package lk.ac.iit.binov20234126;

public class Customer implements Runnable{

    private TicketPool ticketPool;
    private final int customerRetrievalRate;

    public Customer(TicketPool ticketPool, int customerRetrievalRate) {
        this.ticketPool = ticketPool;
        this.customerRetrievalRate = customerRetrievalRate;
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                ticketPool.removeTickets();
                Thread.sleep(1000/customerRetrievalRate);
            } catch (InterruptedException e) {
//                System.out.println("Customer thread interrupted.");
                Thread.currentThread().interrupt();  // Preserve the interrupted status
            }
        }
    }
}
