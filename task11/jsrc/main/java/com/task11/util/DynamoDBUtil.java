package com.task11.util;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.model.*;

import com.task11.model.Table;
import com.task11.model.Reservation;

import java.util.ArrayList;
import java.util.List;

public class DynamoDBUtil {
    private static final AmazonDynamoDB dynamoDB = AmazonDynamoDBClientBuilder.defaultClient();

    public static List<Table> getAllTables() {
        ScanRequest scanRequest = new ScanRequest("Tables");
        ScanResult result = dynamoDB.scan(scanRequest);
        List<Table> tables = new ArrayList<>();

        result.getItems().forEach(item -> {
            Table table = new Table();
            table.setId(item.get("id").getS());
            table.setCapacity(Integer.parseInt(item.get("capacity").getN()));
            table.setAvailable(Boolean.parseBoolean(item.get("available").getBOOL().toString()));
            tables.add(table);
        });

        return tables;
    }

    public static List<Reservation> getAllReservations() {
        ScanRequest scanRequest = new ScanRequest("Reservations");
        ScanResult result = dynamoDB.scan(scanRequest);
        List<Reservation> reservations = new ArrayList<>();

        result.getItems().forEach(item -> {
            Reservation reservation = new Reservation();
            reservation.setId(item.get("id").getS());
            reservation.setUserId(item.get("userId").getS());
            reservation.setTableId(item.get("tableId").getS());
            reservation.setReservationTime(item.get("reservationTime").getS());
            reservations.add(reservation);
        });

        return reservations;
    }
}
