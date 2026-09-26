package com.tickettransfer.api.buyer;

import com.tickettransfer.api.config.ApiConfig;
import com.tickettransfer.api.utils.AuthUtil;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BuyerTicketDetailsApiTest {

    @Test
    void getAvailableTicketDetails() {

        String token = AuthUtil.getJwtToken();

        Response listResponse =
                RestAssured.given()
                        .baseUri(ApiConfig.BASE_URL)
                        .header("Authorization", "Bearer " + token)
                        .queryParam("page", 0)
                        .queryParam("size", 10)
                        .when()
                        .get("/api/buyer/tickets");

        assertEquals(200, listResponse.statusCode());

        Integer ticketId =
                listResponse.jsonPath()
                        .getInt("content[0].ticketId");

        assertNotNull(ticketId);
        assertTrue(ticketId > 0);

        System.out.println("Selected Buyer Ticket ID: " + ticketId);

        Response detailsResponse =
                RestAssured.given()
                        .baseUri(ApiConfig.BASE_URL)
                        .header("Authorization", "Bearer " + token)
                        .when()
                        .get("/api/buyer/tickets/" + ticketId);

        System.out.println("Status Code: " + detailsResponse.statusCode());
        System.out.println(
                "Buyer Ticket Details: " +
                detailsResponse.asPrettyString()
        );

        assertEquals(200, detailsResponse.statusCode());

        assertEquals(
                ticketId,
                detailsResponse.jsonPath().getInt("ticketId")
        );

        assertNotNull(
                detailsResponse.jsonPath().getString("ticketType")
        );

        assertNotNull(
                detailsResponse.jsonPath().getString("providerName")
        );

        assertNotNull(
                detailsResponse.jsonPath().get("originalPrice")
        );

        assertNotNull(
                detailsResponse.jsonPath().getString("ticketStatus")
        );

        assertNotNull(
                detailsResponse.jsonPath().getString("verificationStatus")
        );
    }
}