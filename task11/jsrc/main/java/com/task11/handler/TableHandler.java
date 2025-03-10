package com.task11.handler;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.task11.model.Table;
import com.task11.util.DynamoDBUtil;
import com.task11.handler.ApiGatewayResponse;

import java.util.List;
import java.util.Map;

public class TableHandler implements RequestHandler<Map<String, Object>, ApiGatewayResponse> {

    @Override
    public ApiGatewayResponse handleRequest(Map<String, Object> event, Context context) {
        List<Table> tables = DynamoDBUtil.getAllTables();
        return ApiGatewayResponse.builder()
                .setStatusCode(200)
                .setObjectBody(tables)
                .build();
    }
}
