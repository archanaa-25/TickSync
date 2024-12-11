import java.io.IOException;
import java.util.Scanner;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class Main {
    private static Configuration config;
    private Scanner scanner;
    static final Logger logger = Logger.getLogger(Main.class.getName());

    public Main() {
        config = new Configuration();
        scanner = new Scanner(System.in);
        configureLogger();
    }

    private void configureLogger() {
        try {
            // Create a FileHandler that writes log messages to a file
            FileHandler fileHandler = new FileHandler("Ticketing_system.log", true); // Append mode
            fileHandler.setFormatter(new SimpleFormatter()); // Set a simple text formatter
            logger.addHandler(fileHandler);

            // Optional: Prevent logs from appearing on the console
            logger.setUseParentHandlers(false);

        } catch (IOException e) {
            System.err.println("Failed to configure logger: " + e.getMessage());
        }
    }

    private int promptForPositiveInt(String message) {
        int value;
        while (true) {
            System.out.print(message);
            try {
                value = Integer.parseInt(scanner.nextLine());
                if (value > 0) {
                    break;
                }
                System.out.println("Please enter a positive integer.");
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid integer.");
            }
        }
        return value;
    }

    // Log configuration data
    private void logConfiguration() {
        logger.info("Total Tickets: " + config.getTotalTickets());
        logger.info("Ticket Release Rate: " + config.getTicketReleaseRate());
        logger.info("Customer Retrieval Rate: " + config.getCustomerRetrievalRate());
        logger.info("Max Ticket Capacity: " + config.getMaximumTicketCapacity());
    }

    public void configureSystem() {
        System.out.println("Welcome to the Real-Time Event Ticketing System Configuration!");

        config.setTotalTickets(promptForPositiveInt("Enter the total number of tickets: "));
        config.setTicketReleaseRate(promptForPositiveInt("Enter the ticket release rate (in milliseconds): "));
        config.setCustomerRetrievalRate(promptForPositiveInt("Enter the customer retrieval rate (in milliseconds): "));
        config.setMaximumTicketCapacity(promptForPositiveInt("Enter the maximum ticket capacity: "));

        System.out.println("Configuration complete!");
        logConfiguration(); // Log configuration data
    }

    public static void main(String[] args) {
        Main cli = new Main();
        cli.configureSystem();

        int totalTickets = config.getTotalTickets();
        int ticketReleaseRate = config.getTicketReleaseRate();
        int customerRetrievalRate = config.getCustomerRetrievalRate();
        int maxCapacity = config.getMaximumTicketCapacity();

        TicketPool ticketPool = new TicketPool(maxCapacity);

        // Vendor threads
        Vendor vendor1 = new Vendor(ticketReleaseRate, totalTickets / 2, ticketPool); // Vendor 1 releases half the tickets
        Thread vendorThread1 = new Thread(vendor1, "Vendor 1");

        Vendor vendor2 = new Vendor(ticketReleaseRate, totalTickets / 2 + totalTickets%2, ticketPool); // Vendor 2 releases the remaining tickets
        Thread vendorThread2 = new Thread(vendor2, "Vendor 2");

        // Customer threads
        Customer customer1 = new Customer(customerRetrievalRate, ticketPool);
        Thread customerThread1 = new Thread(customer1, "Customer 1");

        Customer customer2 = new Customer(customerRetrievalRate, ticketPool);
        Thread customerThread2 = new Thread(customer2, "Customer 2");

        // Start vendor and customer threads
        vendorThread1.start();
        vendorThread2.start();
        customerThread1.start();
        customerThread2.start();

        // Wait for threads to complete
        try {
            vendorThread1.join();
            vendorThread2.join();
            customerThread1.join();
            customerThread2.join();
        } catch (InterruptedException e) {
            System.err.println("Thread interrupted: " + e.getMessage());
        }

        // Log final data
        logger.info("Total Tickets Sold: " + ticketPool.getTicketsSoldCount());
        logger.info("Total Tickets Released: " + ticketPool.getTicketsReleasedCount());
        logger.info("Final Queue Size: " + ticketPool.getQueueSize());

        System.out.println("Simulation finished.");
    }
}