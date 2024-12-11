package lk.ac.iit.binov20234126;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        TicketPool ticketPool = null;
        List<Thread> vendorThreads = new ArrayList<>();
        List<Thread> customerThreads = new ArrayList<>();
        Configuration configuration = null;

        System.out.println("Welcome to the Ticket Management System!");
        System.out.println("========================================");

        while (true) {
            try {
                System.out.println("\nMain Menu:");
                System.out.println("1. Load Configuration");
                System.out.println("2. Start System");
                System.out.println("3. Stop System");
                System.out.println("4. Display Status");
                System.out.println("5. Save Configuration");
                System.out.println("6. Exit");
                System.out.print("Enter your choice (1-6): ");
                int choice = scanner.nextInt();

                switch (choice) {
                    case 1: // Load Configuration
                        scanner.nextLine(); // Consume newline
                        System.out.println("Enter configuration file path (or type 'default' for manual input):");
                        String filePath = scanner.nextLine();

                        if (!filePath.equalsIgnoreCase("default")) {
                            configuration = Configuration.loadFromFile(filePath);
                            configuration.validate();
                            System.out.println("Configuration loaded successfully.");
                        } else {
                            // Manual input
                            System.out.print("Enter total tickets: ");
                            int totalTickets = scanner.nextInt();

                            System.out.print("Enter ticket release rate: ");
                            int ticketReleaseRate = scanner.nextInt();

                            System.out.print("Enter customer retrieval rate: ");
                            int customerRetrievalRate = scanner.nextInt();

                            System.out.print("Enter maximum ticket pool capacity: ");
                            int maxCapacity = scanner.nextInt();

                            System.out.println("Enter number of vendors: ");
                            int vendorCount = scanner.nextInt();

                            System.out.println("Enter number of customers: ");
                            int customerCount = scanner.nextInt();

                            configuration = new Configuration(totalTickets, ticketReleaseRate, customerRetrievalRate, maxCapacity, vendorCount, customerCount);
                            configuration.validate();
                            System.out.println("Configuration created successfully.");
                        }

                        // Initialize Ticket Pool
                        ticketPool = new TicketPool(configuration.getMaxTicketCapacity());
                        System.out.println("Ticket Pool initialized with capacity: " + configuration.getMaxTicketCapacity());
                        System.out.println("Ticket Pool initialized with Max Tickets: " + configuration.getTotalTickets());

                        int vendorCount = configuration.getVendorCount();
                        int customerCount = configuration.getCustomerCount();

                        // Prepare Vendor Threads
                        vendorThreads.clear();
                        for (int i = 0; i < vendorCount; i++) { // Example: 2 vendors
                            Thread vendorThread = new Thread(new Vendor(ticketPool, configuration.getTicketReleaseRates(), configuration.getTotalTickets()));
                            vendorThreads.add(vendorThread);
                        }

                        // Prepare Customer Threads
                        customerThreads.clear();
                        for (int i = 0; i < customerCount; i++) { // Example: 3 customers
                            Thread customerThread = new Thread(new Customer(ticketPool, configuration.getCustomerRetrievalRate()));
                            customerThreads.add(customerThread);
                        }
                        break;

                    case 2: // Start System
                        if (configuration == null || ticketPool == null) {
                            System.out.println("Please load a configuration first (Option 1).");
                        } else {
                            // Start Vendor Threads
                            for (Thread vendorThread : vendorThreads) {
                                if (!vendorThread.isAlive()) {
                                    vendorThread.start();
                                }
                            }

                            // Start Customer Threads
                            for (Thread customerThread : customerThreads) {
                                if (!customerThread.isAlive()) {
                                    customerThread.start();
                                }
                            }
                            System.out.println("System started.");
                        }
                        break;

                    case 3: // Stop System
                        if (ticketPool == null) {
                            System.out.println("System is not running.");
                        } else {
                            for (Thread vendorThread : vendorThreads) {
                                vendorThread.interrupt();
                            }
                            for (Thread customerThread : customerThreads) {
                                customerThread.interrupt();
                            }
                            System.out.println("System stopped.");
                        }
                        break;

                    case 4: // Display Status
                        if (ticketPool != null) {
                            ticketPool.displayStatus();
                        } else {
                            System.out.println("Ticket pool is not initialized.");
                        }
                        break;

                    case 5: // Save Configuration
                        if (configuration == null) {
                            System.out.println("No configuration to save. Please load a configuration first.");
                        } else {
                            scanner.nextLine(); // Consume newline
                            System.out.println("Enter file path to save configuration:");
                            String savePath = scanner.nextLine();
                            try {
                                configuration.saveToFile(savePath);
                                System.out.println("Configuration saved successfully to: " + savePath);
                            } catch (Exception e) {
                                System.out.println("Failed to save configuration: " + e.getMessage());
                            }
                        }
                        break;

                    case 6: // Exit
                        System.out.println("Exiting system...");
                        if (ticketPool != null) {
                            for (Thread vendorThread : vendorThreads) {
                                vendorThread.interrupt();
                            }
                            for (Thread customerThread : customerThreads) {
                                customerThread.interrupt();
                            }
                        }
                        System.out.println("Thank you for using the Ticket Management System!");
                        System.exit(0);
                        break;

                    default:
                        System.out.println("Invalid choice. Please select an option between 1 and 6.");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
                scanner.nextLine(); // Consume invalid input
            }
        }
    }
}
