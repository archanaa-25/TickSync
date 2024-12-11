package com.example.backend.CLI;

import java.util.LinkedList;
import java.util.Queue;

public class TicketPool {
    private final Queue<Ticket> ticketQueue = new LinkedList<>();
    private final int maximumCapacity;
    private int totalTicketsReleased = 0;
    private int totalTicketsSold = 0;

    public TicketPool(int maximumCapacity) {
        this.maximumCapacity = maximumCapacity;
    }

    // Vendor adds tickets to the pool
    public synchronized void addTickets(Ticket ticket) {
        while (ticketQueue.size() >= maximumCapacity) { // If the pool is full
            try {
                System.out.println("Vendor waiting, TicketPool is full.");
                wait(); // Vendor waits until space is available
            } catch (InterruptedException e) {
                e.printStackTrace();
                Thread.currentThread().interrupt(); // Restore interrupted status
            }
        }

        ticketQueue.add(ticket); // Add ticket to the pool
        totalTicketsReleased++;
        System.out.println("Vendor added the ticket: " + ticket);
        System.out.println("Total tickets released: " + totalTicketsReleased + "Total tickets sold: " + totalTicketsSold +" | Current queue size: " + ticketQueue.size());
        notifyAll(); // Notify waiting customers that a ticket is available
    }

    // Customer removes tickets from the pool
    public synchronized Ticket removeTickets() {
        while (ticketQueue.isEmpty()) { // If the pool is empty
            try {
                System.out.println("Customer waiting, TicketPool is empty.");
                wait(); // Customer waits until a ticket is available
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt(); // Restore interrupted status
            }
        }

        Ticket ticket = ticketQueue.poll(); // Remove a ticket from the pool
        totalTicketsSold++;
        System.out.println("Customer retrieved the ticket: " + ticket);
        System.out.println("Total tickets released: " + totalTicketsReleased + "Total tickets sold: " + totalTicketsSold + " | Current queue size: " + ticketQueue.size());

        notifyAll(); // Notify vendors that space is available
        return ticket;
    }

    // Getter methods for monitoring
    public synchronized int getTicketsReleasedCount() {
        return totalTicketsReleased;
    }

    public synchronized int getTicketsSoldCount() {
        return totalTicketsSold;
    }

    public synchronized int getQueueSize() {
        return ticketQueue.size();
    }

    public synchronized boolean isEmpty() {
        return ticketQueue.isEmpty();
    }

    public synchronized int getMaxCapacity() {
        return maximumCapacity;
    }

    public synchronized int getTotalTicketsReleased() {
        return totalTicketsReleased;
    }

    public synchronized int getTotalTicketsSold() {
        return totalTicketsSold;
    }
}
