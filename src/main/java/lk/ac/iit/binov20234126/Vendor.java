package lk.ac.iit.binov20234126;

public class Vendor implements Runnable {

    private TicketPool ticketPool;
    private final int ticketReleaseRate;
    private int ticketCounter = 1;
    private int totalTickets;

    public Vendor(TicketPool ticketPool, int ticketReleaseRate, int totalTickets) {
        this.ticketPool = ticketPool;
        this.ticketReleaseRate = ticketReleaseRate;
        this.totalTickets = totalTickets;
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) { // Continue running until the thread is interrupted
            try {
                if(ticketCounter>totalTickets){
                    Thread.currentThread().interrupt();
                }
                else {
                    ticketPool.addTickets(ticketCounter++); // Add a ticket to the pool, incrementing the ticket ID
                    Thread.sleep(1000 / ticketReleaseRate); // Sleep for a calculated interval based on releaseRate
                }
            } catch (InterruptedException e) {
                System.out.println("Vendor thread interrupted."); // Print message if interrupted
                Thread.currentThread().interrupt(); // Restore interrupted status to allow proper handling elsewhere
            }
        }
    }
}
