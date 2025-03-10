const AWS = require('aws-sdk');
const axios = require('axios');
const { v4: uuidv4 } = require('uuid');
const AWSXRay = require('aws-xray-sdk');

AWSXRay.captureHTTPsGlobal(require('https'));
AWSXRay.captureAWS(AWS);

const dynamoDB = new AWS.DynamoDB.DocumentClient();
const TABLE_NAME = ${target_table};

exports.handler = async (event) => {
    const segment = AWSXRay.getSegment();
    const subsegment = segment.addNewSubsegment('fetch-weather-data');

    try {
        const weatherApiUrl = 'https://api.open-meteo.com/v1/forecast?latitude=52.52&longitude=13.41&current=temperature_2m,wind_speed_10m&hourly=temperature_2m,relative_humidity_2m,wind_speed_10m';

        const response = await axios.get(weatherApiUrl);
        subsegment.addAnnotation('HTTP Status', response.status);
        subsegment.addMetadata('Weather API Response', response.data);

        const weatherData = {
            id: uuidv4(),
            forecast: {
                latitude: response.data.latitude,
                longitude: response.data.longitude,
                generationtime_ms: response.data.generationtime_ms,
                utc_offset_seconds: response.data.utc_offset_seconds,
                timezone: response.data.timezone,
                timezone_abbreviation: response.data.timezone_abbreviation,
                elevation: response.data.elevation,
                hourly_units: {
                    time: response.data.hourly_units.time,
                    temperature_2m: response.data.hourly_units.temperature_2m
                },
                hourly: {
                    time: response.data.hourly.time,
                    temperature_2m: response.data.hourly.temperature_2m
                }
            }
        };


        const putParams = {
            TableName: TABLE_NAME,
            Item: weatherData
        };

        await dynamoDB.put(putParams).promise();
        subsegment.addMetadata('DynamoDB PutItem', putParams);

        return {
            statusCode: 200,
            body: JSON.stringify({ message: 'Weather data saved successfully!', id: weatherData.id })
        };
    } catch (error) {
        subsegment.addError(error);
        return {
            statusCode: 500,
            body: JSON.stringify({ message: 'Error fetching or storing weather data', error: error.message })
        };
    } finally {
        subsegment.close();
    }
};
