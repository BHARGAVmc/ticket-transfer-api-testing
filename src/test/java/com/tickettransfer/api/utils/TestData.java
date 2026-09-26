package com.tickettransfer.api.utils;

public class TestData {

    private static Long ticketId;

    private TestData() {
        // Utility class
    }

    public static void setTicketId(Long id) {
        ticketId = id;
    }

    public static Long getTicketId() {
        return ticketId;
    }
}