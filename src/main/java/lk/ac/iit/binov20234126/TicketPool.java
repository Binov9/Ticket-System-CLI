package lk.ac.iit.binov20234126;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TicketPool {

    private final List<Integer> tickets = Collections.synchronizedList(new ArrayList<>());
    private final int maxCapacity;

    public TicketPool(int maxCapacity) {
        this.maxCapacity = maxCapacity;
    }

    public int getMaxCapacity() {
        return maxCapacity;
    }

    public synchronized void addTickets(int ticketId) throws InterruptedException {
        while (tickets.size() >= maxCapacity) { // Wait if the pool is full
            System.out.println("TicketPool is Full. Vendor is waiting...");
            wait();
        }
        tickets.add(ticketId);
        System.out.println("Vendor added ticket: " + ticketId);
        notifyAll(); // Notify customers that a new ticket is available
    }

    public synchronized void removeTickets() throws InterruptedException {
        while (tickets.isEmpty()) { // Correct condition: wait if there are no tickets
            System.out.println("TicketPool is Empty. Customer is waiting...");
            wait();
        }
        int ticket = tickets.remove(0);
        System.out.println("Customer purchased ticket: " + ticket);
        notifyAll(); // Notify vendors that space is available
    }

    public synchronized void displayStatus() {
        System.out.println("Current tickets in pool: " + tickets);
        System.out.println("Current pool size: " + tickets.size());
    }
}
