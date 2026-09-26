package com.tickettransfer.api.auth;

import com.tickettransfer.api.config.ApiConfig;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class LoginApiTest {

    @Test
    void loginWithValidCredentials() {

        String email = System.getenv("TT_TEST_EMAIL");
        String password = System.getenv("TT_TEST_PASSWORD");

        if (email == null || password == null) {
        throw new IllegalStateException(
                "TT_TEST_EMAIL and TT_TEST_PASSWORD environment variables are required."
        );
        }

        String requestBody = """
                {
                "email": "%s",
                "password": "%s"
                }
                """.formatted(email, password);

        Response response =
                RestAssured.given()
                        .baseUri(ApiConfig.BASE_URL)
                        .contentType("application/json")
                        .body(requestBody)
                        .when()
                        .post("/api/auth/login");

        System.out.println("Status Code: " + response.statusCode());
        System.out.println("Login Message: " +
                response.jsonPath().getString("message"));

        assertEquals(200, response.statusCode());

        assertEquals(
                "Login Successful",
                response.jsonPath().getString("message")
        );

        String token = response.jsonPath().getString("token");

        assertNotNull(token);
        assertFalse(token.isBlank());
    }
}