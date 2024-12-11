package com.example.backend.controller;

import com.example.backend.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
@CrossOrigin(origins = "http://localhost:3000")

@RestController
@RequestMapping("/api/tickets")
public class TicketController {

    @Autowired
    private TicketService ticketService;

    @PostMapping("/initialize")
    public String initializeTicketPool(@RequestParam int capacity) {
        ticketService.initializeTicketPool(capacity);
        return "Ticket pool initialized with capacity: " + capacity;
    }

    @GetMapping("/status")
    public int getTicketStatus() {
        return ticketService.getAvailableTickets();
    }


    @PostMapping("/start")
    public String startOperations(
            @RequestParam int releaseRate,
            @RequestParam int buyRate,
            @RequestParam int releaseAmount,
            @RequestParam int buyAmount) {
        ticketService.startTicketOperations(releaseRate, buyRate, releaseAmount, buyAmount);
        return "Ticket operations started!";
    }
}
