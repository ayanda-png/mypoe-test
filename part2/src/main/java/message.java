

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.io.FileWriter;
import java.io.IOException;

public class message {

    static boolean checkRecipientCell;


    private final String messageID;
    private final int messageNumber;
    private final String recipient;
    private final String message;
    private final String messageHash;
    
    private static int totalMessages = 0;
    private static final ArrayList<message> sentMessages = new ArrayList<>();

    // Constructor
    public message(String recipient, String message) {
        this.recipient = recipient;
        this.message = message;
        this.messageID = generateMessageID();
        totalMessages++;
        this.messageNumber = totalMessages;
        this.messageHash = createMessageHash();
    }

    private String generateMessageID() {
        Random rand = new Random();
        StringBuilder id = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            id.append(rand.nextInt(10));
        }
        return id.toString();
    }

    public boolean checkMessageID() {
        return messageID.length() <= 10;
    }

    public String checkRecipientCell() {
        if (recipient == null || recipient.length() > 10 || !recipient.startsWith("+")) {
            return "Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.";
        }
        return "Cell phone number successfully captured.";
    }

    public String createMessageHash() {
        String firstTwo = messageID.substring(0, 2);
        String msgNumStr = String.valueOf(messageNumber);
        String[] words = message.trim().split("\\s+");
        String firstWord = words.length > 0 ? words[0].toUpperCase() : "EMPTY";
        String lastWord = words.length > 0 ? words[words.length - 1].toUpperCase() : "EMPTY";
        return (firstTwo + ":" + msgNumStr + firstWord + lastWord).toUpperCase();
    }

    // Store message as JSON
    public String storeMessage() {
        try (FileWriter file = new FileWriter("stored_messages.json", true)) { 
            String json = String.format("""
                                        {
                                          "messageID": "%s",
                                          "messageNumber": %d,
                                          "recipient": "%s",
                                          "message": "%s",
                                          "messageHash": "%s"
                                        },
                                        """,
                messageID, messageNumber, recipient, 
                message.replace("\"", "\\\""), messageHash
            );
            file.write(json);
            return "Message successfully stored.";
        } catch (IOException e) {
            return "Error storing message: " + e.getMessage();
        }
    }

    public String sentMessage(Scanner sc) {
        System.out.println("\nChoose an option:");
        System.out.println("1. Send Message");
        System.out.println("2. Ignored Message");
        System.out.println("3. Store Message to send later");

        int choice = 0;
        while (choice < 1 || choice > 3) {
            while (!sc.hasNextInt()) {
                System.out.print("Please enter a number (1-3): ");
                sc.next();
            }
            choice = sc.nextInt();
            sc.nextLine();
        }

        switch (choice) {
            case 1 -> {
                sentMessages.add(this);
                return "Message successfully sent.";
            }
            case 2 -> {
                return "Press 0 to delete the message.";
            }
            case 3 -> {
                return storeMessage();
            }
            default -> {
                return "Invalid choice.";
            }
        }
    }

    public void printMessages() {
        System.out.println("\n=== MESSAGE DETAILS ===");
        System.out.println("Message ID     : " + messageID);
        System.out.println("Message Hash   : " + messageHash);
        System.out.println("Recipient      : " + recipient);
        System.out.println("Message        : " + message);
        System.out.println("=========================");
    }

    public static int getTotalMessages() {
        return totalMessages;
    }

    public static void printAllSentMessages() {
        if (sentMessages.isEmpty()) {
            System.out.println("No messages sent yet.");
            return;
        }
        for (message m : sentMessages) {
            m.printMessages();
        }
    }
}

    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    

