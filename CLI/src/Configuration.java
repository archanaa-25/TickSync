public class Configuration {
//This class is for parameter configuration

    //instance variable of the class. Declared as private to ensure Encapsulation in OOP.
    private int totalTickets;
    private int ticketReleaseRate;
    private int customerRetrievalRate;
    private int maximumTicketCapacity;

    public int getTotalTickets () {
        return totalTickets;
    }

    public void setTotalTickets (int totalTickets) {
        this.totalTickets = totalTickets;
    }

    public int getTicketReleaseRate(){
        return ticketReleaseRate;
    }

    public void setTicketReleaseRate(int ticketReleaseRate){
        this.ticketReleaseRate = ticketReleaseRate;
    }

    public int getCustomerRetrievalRate(){
        return customerRetrievalRate;
    }

    public void setCustomerRetrievalRate(int customerRetrievalRate){
        this.customerRetrievalRate = customerRetrievalRate;
    }

    public int getMaximumTicketCapacity(){
        return maximumTicketCapacity;
    }

    public void setMaximumTicketCapacity (int maximumTicketCapacity){
        this.maximumTicketCapacity = maximumTicketCapacity;
    }


}