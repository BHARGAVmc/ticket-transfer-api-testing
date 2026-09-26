package com.tickettransfer.api.user;

import com.tickettransfer.api.config.ApiConfig;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import com.tickettransfer.api.utils.AuthUtil;

public class ProfileApiTest {

    

    @Test
    void getProfileWithValidJwt() {

        String token = AuthUtil.getJwtToken();

        Response response =
                RestAssured
                        .given()
                        .baseUri(ApiConfig.BASE_URL)
                        .header("Authorization", "Bearer " + token)
                        .when()
                        .get("/api/user/profile");

        System.out.println("Valid JWT Status: " + response.statusCode());
        System.out.println("Profile API returned a valid response.");

        assertEquals(200, response.statusCode());

        assertNotNull(response.asString());
        assertFalse(response.asString().isBlank());

        assertTrue(
                response.asString().contains("@"),
                "Profile response should contain the user's email"
        );
    }

    @Test
    void getProfileWithoutJwt() {

        Response response =
                RestAssured
                        .given()
                        .baseUri(ApiConfig.BASE_URL)
                        .when()
                        .get("/api/user/profile");

        System.out.println("No JWT Status: " + response.statusCode());

        assertEquals(403, response.statusCode());
    }

    @Test
    void getProfileWithInvalidJwt() {

        Response response =
                RestAssured
                        .given()
                        .baseUri(ApiConfig.BASE_URL)
                        .header("Authorization", "Bearer invalid.jwt.token")
                        .when()
                        .get("/api/user/profile");

        System.out.println("Invalid JWT Status: " + response.statusCode());

        assertEquals(403, response.statusCode());
    }
}