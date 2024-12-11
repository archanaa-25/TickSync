public class Customer implements Runnable {
    private final int customerRetrievalRate;
    private final TicketPool ticketPool;

    public Customer(int customerRetrievalRate, TicketPool ticketPool) {
        this.customerRetrievalRate = customerRetrievalRate;
        this.ticketPool = ticketPool;
    }

    @Override
    public void run() {
        try {
            while (true) {
                synchronized (ticketPool) {
                    while (ticketPool.isEmpty() || ticketPool.getTotalTicketsSold() >= ticketPool.getTotalTicketsReleased()) {
                        if (Thread.currentThread().isInterrupted()) {
                            return; // Gracefully exit if interrupted
                        }
                        ticketPool.wait(); // Wait for tickets to be available
                    }

                    Ticket ticket = ticketPool.removeTickets();
                    if (ticket != null) {
                        // Log ticket retrieval by customer
                        Main.logger.info(Thread.currentThread().getName() + " bought ticket: " + ticket);
                    }

                    // Simulate delay for customer retrieval rate
                    Thread.sleep(customerRetrievalRate);
                }
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
