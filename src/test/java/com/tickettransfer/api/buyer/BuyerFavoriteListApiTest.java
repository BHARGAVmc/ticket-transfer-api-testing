package com.tickettransfer.api.buyer;

import com.tickettransfer.api.config.ApiConfig;
import com.tickettransfer.api.utils.AuthUtil;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class BuyerFavoriteListApiTest {

    @Test
    void getSavedTickets() {

        String token = AuthUtil.getJwtToken();

        Response response =
                RestAssured.given()
                        .baseUri(ApiConfig.BASE_URL)
                        .header("Authorization", "Bearer " + token)
                        .when()
                        .get("/api/buyer/favorites");

        System.out.println("Status Code: " + response.statusCode());
        System.out.println(
                "Favorites Response: " +
                response.asPrettyString()
        );

        assertEquals(200, response.statusCode());

        List<?> favorites =
                response.jsonPath().getList("$");

        assertNotNull(favorites);

        if (!favorites.isEmpty()) {

            assertNotNull(
                    response.jsonPath().getString("[0].ticketId")
            );

            assertNotNull(
                    response.jsonPath().getString("[0].ticketType")
            );
        }
    }
}