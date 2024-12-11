package com.example.backend.CLI;

import java.math.BigDecimal;

public class Ticket {
    private final int ticketID;
    private final String eventName = "Event Simple";
    private final BigDecimal ticketPrice = new BigDecimal("1000.0");

    public Ticket(int ticketID) {
        this.ticketID = ticketID;
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "ticketID=" + ticketID +
                ", eventName='" + eventName + '\'' +
                ", ticketPrice=" + ticketPrice +
                '}';
    }
}
