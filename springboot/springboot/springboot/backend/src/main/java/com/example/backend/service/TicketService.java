package com.example.backend.service;

import com.example.backend.model.TicketPool;
import org.springframework.stereotype.Service;

@Service
public class TicketService {
    private TicketPool ticketPool;

    public void initializeTicketPool(int capacity) {
        this.ticketPool = new TicketPool(capacity);
    }
    public int getAvailableTickets() {
        return ticketPool != null ? ticketPool.getAvailableTickets() : 0;
    }

    public void startTicketOperations(int releaseRate, int buyRate, int releaseAmount, int buyAmount) {
        if (ticketPool == null) {
            throw new IllegalStateException("Ticket pool is not initialized.");
        }

        // Vendor thread for releasing tickets
        Thread vendorThread = new Thread(() -> {
            try {
                while (true) {
                    boolean released = ticketPool.releaseTickets(releaseAmount);
                    if (!released) break;
                    Thread.sleep(releaseRate);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        // Customer threads for buying tickets
        Thread customerAThread = new Thread(() -> {
            try {
                while (!ticketPool.areTicketsSoldOut()) {
                    ticketPool.buyTickets(buyAmount, "Customer A");
                    Thread.sleep(buyRate);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        Thread customerBThread = new Thread(() -> {
            try {
                while (!ticketPool.areTicketsSoldOut()) {
                    ticketPool.buyTickets(buyAmount, "Customer B");
                    Thread.sleep(buyRate);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        // Start the threads
        vendorThread.start();
        customerAThread.start();
        customerBThread.start();
    }
}
