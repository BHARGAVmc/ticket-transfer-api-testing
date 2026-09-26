package com.tickettransfer.api.buyer;

import com.tickettransfer.api.config.ApiConfig;
import com.tickettransfer.api.utils.AuthUtil;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BuyerSearchApiTest {

    @Test
    void searchAvailableTickets() {

        String token = AuthUtil.getJwtToken();

        Response response =
                RestAssured.given()
                        .baseUri(ApiConfig.BASE_URL)
                        .header("Authorization", "Bearer " + token)
                        .queryParam("page", 0)
                        .queryParam("size", 10)
                        .when()
                        .get("/api/buyer/search");

        System.out.println("Status Code: " + response.statusCode());
        System.out.println("Search Response: " + response.asPrettyString());

        assertEquals(200, response.statusCode());

        assertNotNull(response.jsonPath().getList("content"));

        assertNotNull(response.jsonPath().get("number"));
        assertNotNull(response.jsonPath().get("size"));
    }

    @Test
    void searchBusTicketsWithFilters() {

        String token = AuthUtil.getJwtToken();

        Response response =
                RestAssured.given()
                        .baseUri(ApiConfig.BASE_URL)
                        .header("Authorization", "Bearer " + token)
                        .queryParam("ticketType", "BUS")
                        .queryParam("source", "Tirupati")
                        .queryParam("destination", "Hyderabad")
                        .queryParam("departureDate", "2026-10-20")
                        .queryParam("page", 0)
                        .queryParam("size", 10)
                        .when()
                        .get("/api/buyer/search");

        System.out.println("Filtered Search Status: " + response.statusCode());
        System.out.println("Filtered Search Response: " + response.asPrettyString());

        assertEquals(200, response.statusCode());

        assertNotNull(response.jsonPath().getList("content"));

        assertNotNull(response.jsonPath().get("number"));
        assertNotNull(response.jsonPath().get("size"));
    }
}