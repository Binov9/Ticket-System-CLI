package lk.ac.iit.binov20234126;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.io.*;

public class Configuration {

    private int totalTickets;
    private int ticketReleaseRates;
    private int customerRetrievalRate;
    private int maxTicketCapacity;
    private int vendorCount;
    private int customerCount;

    public Configuration() {
    }

    public Configuration(int totalTickets, int ticketReleaseRates, int customerRetrievalRate, int maxTicketCapacity, int vendorCount, int customerCount) {
        this.totalTickets = totalTickets;
        this.ticketReleaseRates = ticketReleaseRates;
        this.customerRetrievalRate = customerRetrievalRate;
        this.maxTicketCapacity = maxTicketCapacity;
        this.vendorCount = vendorCount;
        this.customerCount = customerCount;
    }

    public int getTotalTickets() {
        return totalTickets;
    }

    public int getTicketReleaseRates() {
        return ticketReleaseRates;
    }

    public int getCustomerRetrievalRate() {
        return customerRetrievalRate;
    }

    public int getMaxTicketCapacity() {
        return maxTicketCapacity;
    }

    public int getVendorCount() {
        return vendorCount;
    }

    public int getCustomerCount() {
        return customerCount;
    }

    public static Configuration loadFromFile(String filePath) throws IOException {

        Gson gson = new Gson();
        try (Reader reader = new FileReader(filePath)){
            return gson.fromJson(reader, Configuration.class);
        }catch (FileNotFoundException e){
            System.out.println("Configuration file not found : "+filePath);
            throw e;
        }catch (JsonSyntaxException e){
            System.out.println("Invalid JSON syntax in configuration file : "+filePath);
            throw e;
        }
    }

    public void saveToFile(String filePath) throws IOException {
        Gson gson = new Gson();
        try (Writer writer = new FileWriter(filePath)) {
            gson.toJson(this, writer);
        }catch (IOException e){
            System.out.println("Error while saving configuration to file: "+filePath);
            throw e;
        }
    }

    public void validate() {
        if (totalTickets <= 0) {
            throw new IllegalArgumentException("Total tickets must be greater than 0.");
        }
        if (ticketReleaseRates <= 0) {
            throw new IllegalArgumentException("Ticket release rate must be greater than 0.");
        }
        if (customerRetrievalRate <= 0) {
            throw new IllegalArgumentException("Customer release rate must be greater than 0.");
        }
        if (maxTicketCapacity <= 0) {
            throw new IllegalArgumentException("Maximum ticket capacity must be greater than 0.");
        }
        if (vendorCount <= 0) {
            throw new IllegalArgumentException("Vendor count must be greater than 0.");
        }
        if (customerCount <= 0) {
            throw new IllegalArgumentException("Customer count must be greater than 0.");
        }
    }

    @Override
    public String toString() {
        return "Configuration{" +
                "totalTickets=" + totalTickets +
                ", ticketReleaseRates=" + ticketReleaseRates +
                ", customerReleaseRates=" + customerRetrievalRate +
                ", maxTicketCapacity=" + maxTicketCapacity +
                '}';
    }
}
