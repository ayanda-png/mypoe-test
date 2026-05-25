


/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

import java.util.Scanner;

public class QuickChat {

    public static void main(String[] args) {
        try (Scanner key = new Scanner(System.in)) {
            RegistrationSystem regSystem = new RegistrationSystem(key);
            
            System.out.println("=== QUICKCHAT APPLICATION ===");
            
            // Run registration/login from Part 1
            boolean loggedIn = false;
            while (!loggedIn) {
                regSystem.showMenu();  // This will show Register/Login/Exit from your Part 1
                
                // After menu, check if user logged in successfully
                // We assume after successful login they are in the system
                // (You can improve this check if needed)
                loggedIn = true; // For simplicity - adjust if you want stricter check
            }
            
            System.out.println("\nWelcome to Fire QuickChat.");
            
            // Ask how many messages user wants to send
            System.out.print("How many messages would you like to send? ");
            int numMessages = 0;
            while (!key.hasNextInt() || (numMessages = key.nextInt()) < 1) {
                System.out.print("Please enter a valid number: ");
                key.nextLine();
            }
            key.nextLine(); // consume newline
            
            int messagesSent = 0;
            
            // Main menu loop
            int choice;
            do {
                System.out.println("\n=== QUICKCHAT MENU ===");
                System.out.println("1. Send Messages");
                System.out.println("2. Show recently sent messages");
                System.out.println("3. Quit");
                System.out.print("Choose option: ");
                
                while (!key.hasNextInt()) {
                    System.out.print("Please enter a number (1-3): ");
                    key.next();
                }
                choice = key.nextInt();
                key.nextLine();
                
                switch (choice) {
                    case 1 -> {
                        // Send Messages
                        if (messagesSent >= numMessages) {
                            System.out.println("You have reached your message limit for this session.");
                            break;
                        }
                        
                        System.out.print("Enter recipient cell number (+...): ");
                        String recipient = key.nextLine();
                        
                        System.out.print("Enter your message (max 250 chars): ");
                        String msgText = key.nextLine();
                        
                        if (msgText.length() > 250) {
                            System.out.println("Please enter a message of less than 250 characters.");
                            break;
                        }
                        
                        message msg = new message(recipient, msgText);

                        System.out.println(message.checkRecipientCell);/*());*/
                        
                        String result = msg.sentMessage(key);
                        System.out.println(result);
                        
                        if (result.contains("successfully sent")) {
                            msg.printMessages();
                            messagesSent++;
                        }
                    }
                        
                    case 2 -> System.out.println("Coming Soon.");
                        
                    case 3 -> {
                        System.out.println("Total messages sent: " + message.getTotalMessages());
                        System.out.println("Goodbye!");
                    }
                        
                    default -> System.out.println("Invalid option. Please try again.");
                }
            } while (choice != 3);
        }
    }

    private static class RegistrationSystem {

        public RegistrationSystem(Scanner key) {
        }

        private void showMenu() {
            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }
    }
}
