package com.tickettransfer.api.seller;

import com.tickettransfer.api.config.ApiConfig;
import com.tickettransfer.api.utils.AuthUtil;
import com.tickettransfer.api.utils.TestData;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

public class SellerTicketApiTest {

    @Test
    void uploadBusTicket() {

        String token = AuthUtil.getJwtToken();

        String uniqueId = String.valueOf(System.currentTimeMillis());

        File ticketFile =
                new File("F:\\Mr_Mc\\ticket-transfer-api-testing\\ticket_demo.pdf");

        assertTrue(
                ticketFile.exists(),
                "Ticket PDF file was not found"
        );

        Response response =
                RestAssured
                        .given()
                        .baseUri(ApiConfig.BASE_URL)
                        .header("Authorization", "Bearer " + token)

                        .multiPart("providerName", "APSRTC")
                        .multiPart("originalPrice", "650")
                        .multiPart("pnrNumber", "AUTO" + uniqueId)
                        .multiPart("operatorName", "APSRTC")
                        .multiPart("busNumber", "BUS" + uniqueId)
                        .multiPart("source", "Tirupati")
                        .multiPart("destination", "Hyderabad")
                        .multiPart("departureDate", "2026-10-20")
                        .multiPart("departureTime", "20:30")
                        .multiPart("arrivalDate", "2026-10-21")
                        .multiPart("arrivalTime", "05:30")
                        .multiPart("boardingPoint", "Tirupati Bus Stand")
                        .multiPart("droppingPoint", "MGBS")
                        .multiPart("seatNumber", "AUTO-U1")
                        .multiPart("passengerName", "Automation Test User")
                        .multiPart(
                                "additionalDetails",
                                "{\"testAutomation\":true}"
                        )
                        .multiPart(
                                "ticketFile",
                                ticketFile,
                                "application/pdf"
                        )

                        .when()
                        .post("/api/seller/tickets/bus");

        System.out.println("Status Code: " + response.statusCode());
        System.out.println("Upload Response: " + response.asPrettyString());

        assertEquals(200, response.statusCode());

        assertNotNull(response.jsonPath().get("ticketId"));

        assertNotNull(response.jsonPath().get("message"));

        assertTrue(
                response.jsonPath().getLong("ticketId") > 0,
                "Created ticket ID should be greater than zero"
        );
        Long createdTicketId = response.jsonPath().getLong("ticketId");
        TestData.setTicketId(createdTicketId);
    }
}