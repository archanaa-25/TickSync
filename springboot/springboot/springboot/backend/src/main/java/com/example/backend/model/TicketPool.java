package com.example.backend.model;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class TicketPool {
    private int capacity;
    private int availableTickets;
    private final Lock lock = new ReentrantLock();
    private boolean ticketsSoldOut = false; // Flag to indicate when tickets are sold out

    public TicketPool(int capacity) {
        this.capacity = capacity;
        this.availableTickets = 0;
    }

    public boolean releaseTickets(int amount) {
        lock.lock();
        try {
            if (availableTickets + amount > capacity) {
                System.out.println("Cannot release tickets. Exceeds capacity.");
                return false;
            }
            availableTickets += amount;
            ticketsSoldOut = false; // Reset the flag when new tickets are released
            System.out.println("Tickets released: " + amount + ", Total available: " + availableTickets);
            return true;
        } finally {
            lock.unlock();
        }
    }

    public boolean buyTickets(int amount, String customerName) {
        lock.lock();
        try {
            if (ticketsSoldOut) {
                return false; // Stop processing if tickets are sold out
            }
            if (availableTickets >= amount) {
                availableTickets -= amount;
                System.out.println(customerName + " bought " + amount + " tickets. Remaining: " + availableTickets);
                if (availableTickets == 0) {
                    ticketsSoldOut = true; // Set the flag when tickets are sold out
                }
                return true;
            } else {
                System.out.println(customerName + " tried to buy " + amount + " tickets, but only "
                        + availableTickets + " are available.");
                return false;
            }
        } finally {
            lock.unlock();
        }
    }

    public boolean areTicketsSoldOut() {
        return ticketsSoldOut;
    }

    public int getAvailableTickets() {
    return availableTickets;
    }
}
