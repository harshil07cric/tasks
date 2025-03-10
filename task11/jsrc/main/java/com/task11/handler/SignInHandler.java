package com.task11.handler;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.task11.model.SignInRequest;
import com.task11.util.CognitoUtil;

import java.util.Map;

public class SignInHandler implements RequestHandler<Map<String, Object>, ApiGatewayResponse> {

    @Override
    public ApiGatewayResponse handleRequest(Map<String, Object> event, Context context) {
        SignInRequest request = CognitoUtil.parseRequest(event, SignInRequest.class);
        String authToken = CognitoUtil.signIn(request);

        return ApiGatewayResponse.builder()
                .setStatusCode(200)
                .setObjectBody(Map.of("token", authToken))
                .build();
    }
}
