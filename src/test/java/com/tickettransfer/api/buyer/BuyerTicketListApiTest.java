package com.tickettransfer.api.buyer;

import com.tickettransfer.api.config.ApiConfig;
import com.tickettransfer.api.utils.AuthUtil;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class BuyerTicketListApiTest {

    @Test
    void getAvailableTickets() {

        String token = AuthUtil.getJwtToken();

        Response response =
                RestAssured.given()
                        .baseUri(ApiConfig.BASE_URL)
                        .header("Authorization", "Bearer " + token)
                        .queryParam("page", 0)
                        .queryParam("size", 10)
                        .when()
                        .get("/api/buyer/tickets");

        System.out.println("Status Code: " + response.statusCode());
        System.out.println("Buyer Tickets Response: " + response.asPrettyString());

        assertEquals(200, response.statusCode());

        List<?> tickets = response.jsonPath().getList("content");

        assertNotNull(tickets, "Ticket content should not be null");
        assertTrue(tickets.size() > 0, "Available tickets should not be empty");

        assertNotNull(response.jsonPath().get("number"));
        assertNotNull(response.jsonPath().get("size"));

        assertNotNull(response.jsonPath().getInt("content[0].ticketId"));
        assertNotNull(response.jsonPath().getString("content[0].ticketType"));
        assertNotNull(response.jsonPath().getString("content[0].ticketStatus"));
        assertNotNull(response.jsonPath().getString("content[0].providerName"));
    }
}