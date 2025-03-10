package com.task11.util;

import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProvider;
import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProviderClientBuilder;
import com.amazonaws.services.cognitoidp.model.*;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;

public class CognitoUtil {
    private static final String CLIENT_ID = System.getenv("CLIENT_ID");

    public static String signIn(SignInRequest request) {
        AWSCognitoIdentityProvider cognitoClient = AWSCognitoIdentityProviderClientBuilder.defaultClient();

        InitiateAuthRequest authRequest = new InitiateAuthRequest()
                .withClientId(CLIENT_ID)
                .withAuthFlow(AuthFlowType.USER_PASSWORD_AUTH)
                .withAuthParameters(Map.of("USERNAME", request.getUsername(), "PASSWORD", request.getPassword()));

        InitiateAuthResult result = cognitoClient.initiateAuth(authRequest);
        return result.getAuthenticationResult().getIdToken();
    }

    public static void signUp(SignUpRequest request) {
        AWSCognitoIdentityProvider cognitoClient = AWSCognitoIdentityProviderClientBuilder.defaultClient();

        SignUpRequest signUpRequest = new SignUpRequest()
                .withClientId(CLIENT_ID)
                .withUsername(request.getUsername())
                .withPassword(request.getPassword());

        cognitoClient.signUp(signUpRequest);
    }

    public static <T> T parseRequest(Map<String, Object> event, Class<T> clazz) {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.convertValue(event.get("body"), clazz);
    }
}
