package com.tickettransfer.api.seller;

import com.tickettransfer.api.config.ApiConfig;
import com.tickettransfer.api.utils.AuthUtil;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SellerTicketListApiTest {

    @Test
    void getMyTickets() {

        String token = AuthUtil.getJwtToken();

        Response response =
                RestAssured
                        .given()
                        .baseUri(ApiConfig.BASE_URL)
                        .header("Authorization", "Bearer " + token)
                        .when()
                        .get("/api/seller/tickets");

        System.out.println("Status Code: " + response.statusCode());
        System.out.println("My Tickets Response: " + response.asPrettyString());

        assertEquals(200, response.statusCode());

        assertNotNull(response.jsonPath().getList("$"));

        assertTrue(
                response.jsonPath().getList("$").size() > 0,
                "Seller should have at least one ticket"
        );

        assertTrue(
                response.asString().contains("\"ticketId\""),
                "Response should contain ticketId"
        );

        assertTrue(
                response.asString().contains("\"ticketType\""),
                "Response should contain ticketType"
        );

        assertTrue(
                response.asString().contains("\"ticketStatus\""),
                "Response should contain ticketStatus"
        );
    }
}