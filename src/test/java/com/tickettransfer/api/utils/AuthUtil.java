package com.tickettransfer.api.utils;

import com.tickettransfer.api.config.ApiConfig;
import io.restassured.RestAssured;
import io.restassured.response.Response;

public class AuthUtil {

    private AuthUtil() {
        // Utility class
    }

    public static String getJwtToken() {

        String requestBody = """
                {
                    "email": "mrboby35111@gmail.com",
                    "password": "123456"
                }
                """;

        Response response =
                RestAssured
                        .given()
                        .baseUri(ApiConfig.BASE_URL)
                        .contentType("application/json")
                        .body(requestBody)
                        .when()
                        .post("/api/auth/login");

        if (response.statusCode() != 200) {
            throw new RuntimeException(
                    "Login failed. Status: "
                            + response.statusCode()
                            + ", Response: "
                            + response.asString()
            );
        }

        String token = response.jsonPath().getString("token");

        if (token == null || token.isBlank()) {
            throw new RuntimeException("Login succeeded but JWT token is empty.");
        }

        return token;
    }
}