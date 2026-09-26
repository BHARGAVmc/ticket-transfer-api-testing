package com.tickettransfer.api.seller;

import com.tickettransfer.api.config.ApiConfig;
import com.tickettransfer.api.utils.AuthUtil;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SellerTicketSearchApiTest {

    @Test
    void searchMyTickets() {

        String token = AuthUtil.getJwtToken();

        Response response =
                RestAssured
                        .given()
                        .baseUri(ApiConfig.BASE_URL)
                        .header("Authorization", "Bearer " + token)
                        .queryParam("keyword", "APSRTC")
                        .when()
                        .get("/api/seller/tickets/search");

        System.out.println("Status Code: " + response.statusCode());
        System.out.println("Search Response: " + response.asPrettyString());

        assertEquals(200, response.statusCode());

        assertNotNull(response.jsonPath().getList("$"));

        response.jsonPath()
                .getList("$")
                .forEach(ticket -> {
                    assertNotNull(ticket);
                });
    }
}