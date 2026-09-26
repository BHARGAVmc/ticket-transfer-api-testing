package com.tickettransfer.api.buyer;

import com.tickettransfer.api.config.ApiConfig;
import com.tickettransfer.api.utils.AuthUtil;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BuyerFavoriteApiTest {

    @Test
    void saveTicketAsFavorite() {

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

        System.out.println("Favorite Ticket ID: " + ticketId);

        String requestBody = """
                {
                    "ticketId": %d
                }
                """.formatted(ticketId);

        Response response =
                RestAssured.given()
                        .baseUri(ApiConfig.BASE_URL)
                        .header("Authorization", "Bearer " + token)
                        .contentType("application/json")
                        .body(requestBody)
                        .when()
                        .post("/api/buyer/favorites");

        System.out.println("Status Code: " + response.statusCode());
        System.out.println(
                "Favorite Response: " +
                response.asPrettyString()
        );

        assertEquals(200, response.statusCode());

        assertNotNull(
                response.jsonPath().getString("message")
        );
    }
}