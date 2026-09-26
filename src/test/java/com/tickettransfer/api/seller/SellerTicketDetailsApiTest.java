package com.tickettransfer.api.seller;

import com.tickettransfer.api.config.ApiConfig;
import com.tickettransfer.api.utils.AuthUtil;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SellerTicketDetailsApiTest {

    @Test
    void getTicketDetails() {

        String token = AuthUtil.getJwtToken();

        // First get seller's tickets and select an existing ticket dynamically
        Response ticketsResponse =
                RestAssured
                        .given()
                        .baseUri(ApiConfig.BASE_URL)
                        .header("Authorization", "Bearer " + token)
                        .when()
                        .get("/api/seller/tickets");

        assertEquals(200, ticketsResponse.statusCode());

        Long ticketId =
                ticketsResponse
                        .jsonPath()
                        .getLong("[0].ticketId");

        assertTrue(ticketId > 0, "A valid ticket ID should be available");

        // Get details using the dynamically retrieved ticket ID
        Response response =
                RestAssured
                        .given()
                        .baseUri(ApiConfig.BASE_URL)
                        .header("Authorization", "Bearer " + token)
                        .when()
                        .get("/api/seller/tickets/" + ticketId);

        System.out.println("Ticket ID: " + ticketId);
        System.out.println("Status Code: " + response.statusCode());
        System.out.println(
                "Ticket Details Response: " + response.asPrettyString()
        );

        assertEquals(200, response.statusCode());

        assertEquals(
                ticketId,
                response.jsonPath().getLong("ticketId")
        );

        assertNotNull(
                response.jsonPath().getString("ticketType")
        );

        assertNotNull(
                response.jsonPath().getString("ticketStatus")
        );

        assertNotNull(
                response.jsonPath().getString("verificationStatus")
        );
    }
}