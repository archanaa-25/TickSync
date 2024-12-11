import java.math.BigDecimal;

public class Ticket {
    private final int ticketID;
    private final String eventName = "Musical Show";
    private final BigDecimal ticketPrice = new BigDecimal("1500.0");

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
