//package com.example.backend.CLI;
//
//import org.springframework.boot.SpringApplication;
//import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.web.bind.annotation.*;
//
//import java.io.IOException;
//import java.util.logging.FileHandler;
//import java.util.logging.Logger;
//import java.util.logging.SimpleFormatter;
//
//@SpringBootApplication
//@RestController
//public class Main {
//    private static Configuration config;
//    static final Logger logger = Logger.getLogger(Main.class.getName());
//
//    public Main() {
//        config = new Configuration();
//        configureLogger();
//    }
//
//    private void configureLogger() {
//        try {
//            // Create a FileHandler that writes log messages to a file
//            FileHandler fileHandler = new FileHandler("Config_simulation.log", true); // Append mode
//            fileHandler.setFormatter(new SimpleFormatter()); // Set a simple text formatter
//            logger.addHandler(fileHandler);
//
//            // Optional: Prevent logs from appearing on the console
//            logger.setUseParentHandlers(false);
//
//        } catch (IOException e) {
//            System.err.println("Failed to configure logger: " + e.getMessage());
//        }
//    }
//
//    // Log configuration data
//    private void logConfiguration() {
//        logger.info("Total Tickets: " + config.getTotalTickets());
//        logger.info("Ticket Release Rate: " + config.getTicketReleaseRate());
//        logger.info("Customer Retrieval Rate: " + config.getCustomerRetrievalRate());
//        logger.info("Max Ticket Capacity: " + config.getMaximumTicketCapacity());
//    }
//
//    @PostMapping("/configure")
//    public String configureSystem(@RequestBody Configuration configRequest) {
//        config.setTotalTickets(configRequest.getTotalTickets());
//        config.setTicketReleaseRate(configRequest.getTicketReleaseRate());
//        config.setCustomerRetrievalRate(configRequest.getCustomerRetrievalRate());
//        config.setMaximumTicketCapacity(configRequest.getMaximumTicketCapacity());
//
//        logConfiguration(); // Log configuration data
//        startSimulation();
//
//        return "Configuration received and simulation started!";
//    }
//
//    private void startSimulation() {
//        int totalTickets = config.getTotalTickets();
//        int ticketReleaseRate = config.getTicketReleaseRate();
//        int customerRetrievalRate = config.getCustomerRetrievalRate();
//        int maxCapacity = config.getMaximumTicketCapacity();
//
//        TicketPool ticketPool = new TicketPool(maxCapacity);
//
//        // Vendor and Customer threads
//        Vendor vendor = new Vendor(ticketReleaseRate, totalTickets, ticketPool);
//        Thread vendorThread = new Thread(vendor, "Vendor");
//
//        Customer customer = new Customer(customerRetrievalRate, ticketPool);
//        Thread customerThread = new Thread(customer, "Customer");
//
//        // Start vendor and customer threads
//        vendorThread.start();
//        customerThread.start();
//
//        // Wait for threads to complete
//        try {
//            vendorThread.join();
//            customerThread.join();
//        } catch (InterruptedException e) {
//            System.err.println("Thread interrupted: " + e.getMessage());
//        }
//
//        // Log final data
//        logger.info("Total Tickets Sold: " + ticketPool.getTicketsSoldCount());
//        logger.info("Total Tickets Released: " + ticketPool.getTicketsReleasedCount());
//        logger.info("Final Queue Size: " + ticketPool.getQueueSize());
//
//        System.out.println("Simulation finished.");
//    }
//
//    public static void main(String[] args) {
//        SpringApplication.run(Main.class, args);
//    }
//}



package com.example.backend.CLI;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
@EnableJpaRepositories(basePackages = "com.example.backend.model") // Adjust package to match your structure
@SpringBootApplication
@RestController
public class Main {
    private static Configurations config;
    static final Logger logger = Logger.getLogger(Main.class.getName());

    public Main() {
        config = new Configurations();
        configureLogger();
    }

    private void configureLogger() {
        try {
            // Create a FileHandler that writes log messages to a file
            FileHandler fileHandler = new FileHandler("Config_simulation.log", true); // Append mode
            fileHandler.setFormatter(new SimpleFormatter()); // Set a simple text formatter
            logger.addHandler(fileHandler);

            // Optional: Prevent logs from appearing on the console
            logger.setUseParentHandlers(false);

        } catch (IOException e) {
            System.err.println("Failed to configure logger: " + e.getMessage());
        }
    }

    // Log configuration data
    private void logConfiguration() {
        logger.info("Total Tickets: " + config.getTotalTickets());
        logger.info("Ticket Release Rate: " + config.getTicketReleaseRate());
        logger.info("Customer Retrieval Rate: " + config.getCustomerRetrievalRate());
        logger.info("Max Ticket Capacity: " + config.getMaximumTicketCapacity());
    }

    @PostMapping("/configure")
    public String configureSystem(@RequestBody Configurations configRequest) {
        config.setTotalTickets(configRequest.getTotalTickets());
        config.setTicketReleaseRate(configRequest.getTicketReleaseRate());
        config.setCustomerRetrievalRate(configRequest.getCustomerRetrievalRate());
        config.setMaximumTicketCapacity(configRequest.getMaximumTicketCapacity());

        logConfiguration(); // Log configuration data
        startSimulation();

        return "Configuration received and simulation started!";
    }

    private void startSimulation() {
        int totalTickets = config.getTotalTickets();
        int ticketReleaseRate = config.getTicketReleaseRate();
        int customerRetrievalRate = config.getCustomerRetrievalRate();
        int maxCapacity = config.getMaximumTicketCapacity();

        TicketPool ticketPool = new TicketPool(maxCapacity);

        // Vendor and Customer threads
        Vendor vendor = new Vendor(ticketReleaseRate, totalTickets, ticketPool);
        Thread vendorThread = new Thread(vendor, "Vendor");

        Customer customer = new Customer(customerRetrievalRate, ticketPool);
        Thread customerThread = new Thread(customer, "Customer");

        // Start vendor and customer threads
        vendorThread.start();
        customerThread.start();

        // Wait for threads to complete
        try {
            vendorThread.join();
            customerThread.join();
        } catch (InterruptedException e) {
            System.err.println("Thread interrupted: " + e.getMessage());
        }

        // Log final data
        logger.info("Total Tickets Sold: " + ticketPool.getTicketsSoldCount());
        logger.info("Total Tickets Released: " + ticketPool.getTicketsReleasedCount());
        logger.info("Final Queue Size: " + ticketPool.getQueueSize());

        System.out.println("Simulation finished.");
    }

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }
}
