package com.task11.handler;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import java.util.Map;
//import syndicate.sdk.annotations.DependsOn;
//import syndicate.sdk.annotations.EnvironmentVariable;
//import syndicate.sdk.annotations.EnvironmentVariables;
//import syndicate.sdk.enums.ResourceType;
import com.task11.handler.ApiGatewayResponse;

//@DependsOn(resourceType = ResourceType.COGNITO_USER_POOL, name = "${booking_userpool}")
//@EnvironmentVariables(value = {
//		@EnvironmentVariable(key = "REGION", value = "${region}"),
//		@EnvironmentVariable(key = "COGNITO_ID", value = "${pool_name}", valueTransformer = USER_POOL_NAME_TO_USER_POOL_ID),
//		@EnvironmentVariable(key = "CLIENT_ID", value = "${pool_name}", valueTransformer = USER_POOL_NAME_TO_CLIENT_ID)
//})
public class ApiHandler implements RequestHandler<Map<String, Object>, ApiGatewayResponse> {

	@Override
	public ApiGatewayResponse handleRequest(Map<String, Object> event, Context context) {
		String path = (String) event.get("path");
		String httpMethod = (String) event.get("httpMethod");

		if ("/signin".equals(path) && "POST".equals(httpMethod)) {
			return new SignInHandler().handleRequest(event, context);
		} else if ("/signup".equals(path) && "POST".equals(httpMethod)) {
			return new SignUpHandler().handleRequest(event, context);
		} else if (path.startsWith("/tables")) {
			return new TableHandler().handleRequest(event, context);
		} else if (path.startsWith("/reservations")) {
			return new ReservationHandler().handleRequest(event, context);
		}

		return ApiGatewayResponse.builder()
				.setStatusCode(404)
				.setObjectBody("Resource not found")
				.build();
	}
}
