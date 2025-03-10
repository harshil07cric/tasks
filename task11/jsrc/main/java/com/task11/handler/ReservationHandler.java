package com.task11.handler;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.task11.model.Reservation;
import com.task11.util.DynamoDBUtil;

import java.util.List;
import java.util.Map;

public class ReservationHandler implements RequestHandler<Map<String, Object>, ApiGatewayResponse> {

    @Override
    public ApiGatewayResponse handleRequest(Map<String, Object> event, Context context) {
        List<Reservation> reservations = DynamoDBUtil.getAllReservations();
        return ApiGatewayResponse.builder()
                .setStatusCode(200)
                .setObjectBody(reservations)
                .build();
    }
}
