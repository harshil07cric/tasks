package com.task11.handler;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.task11.model.SignUpRequest;
import com.task11.util.CognitoUtil;
import com.task11.handler.ApiGatewayResponse;

import java.util.Map;

public class SignUpHandler implements RequestHandler<Map<String, Object>, ApiGatewayResponse> {

    @Override
    public ApiGatewayResponse handleRequest(Map<String, Object> event, Context context) {
        SignUpRequest request = CognitoUtil.parseRequest(event, SignUpRequest.class);
        CognitoUtil.signUp(request);

        return ApiGatewayResponse.builder()
                .setStatusCode(201)
                .setObjectBody(Map.of("message", "User created successfully"))
                .build();
    }
}
