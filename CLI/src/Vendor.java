public class Vendor implements Runnable {
    private final int ticketReleaseRate;
    private final int totalTickets;
    private final TicketPool ticketPool;

    public Vendor(int ticketReleaseRate, int totalTickets, TicketPool ticketPool) {
        this.ticketReleaseRate = ticketReleaseRate;
        this.totalTickets = totalTickets;
        this.ticketPool = ticketPool;
    }

    @Override
    public void run() {
        for (int i = 1; i <= totalTickets; i++) {
            synchronized (ticketPool) {
                while (ticketPool.getQueueSize() >= ticketPool.getMaxCapacity()) {
                    try {
                        ticketPool.wait();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }

                ticketPool.addTickets(); // Vendor adds ticket with unique ticketID

                ticketPool.notifyAll(); // Notify customers that tickets are available

                // Log ticket addition
                Main.logger.info((Thread.currentThread().getName()) + " added a ticket." );

                try {
                    Thread.sleep(ticketReleaseRate); // Simulate delay
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }
}
