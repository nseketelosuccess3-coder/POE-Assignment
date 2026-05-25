/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package poe.assignment;

/**
 *
 * @author HP
 */
import java.util.Random;

public class Message {
    private String messageId;
    private String phoneNumb;
    private String messageText;
    private static int totalSent = 0;

    public Message(String phoneNumb, String messageText) {
        this.phoneNumb = phoneNumb;
        this.messageText = messageText;
        this.messageId = makeID();
    }

    private String makeID() {
        Random r = new Random();
        String id = "";
        for (int i = 0; i < 10; i = i + 1) {
            int digit = r.nextInt(10);
            id = id + digit;
        }
        return id;
    }

    public boolean checkMessageID() {
        return messageId.length() == 10;
    }

    public boolean checkMessageLength() {
        return messageText.length() <= 250;
    }

    public String checkRecipientCell() {
        if (phoneNumb.length() <= 12 && phoneNumb.startsWith("+27")) {
            return "Cell phone number is correctly formatted.";
        } else {
            return "Cell phone number is incorrectly formatted or does not contain an international code.";
        }
    }

    public String createMessageHash() {
        String[] words = messageText.split(" ");
        String first = words[0];
        String last = words[words.length - 1];
        String idStart = messageId.substring(0, 2);
        String hash = idStart + ":" + totalSent + ":" + first.toUpperCase() + last.toUpperCase();
        return hash;
    }

    public String SentMessage(int option) {
        if (option == 1) {
            totalSent = totalSent + 1;
            return "Message successfully sent.";
        } else if (option == 2) {
            return "Message disregarded.";
        } else if (option == 3) {
            return "Message successfully stored.";
        } else {
            return "Invalid option.";
        }
    }

    public String printMessages() {
        return "MessageID: " + messageId + "\n" +
               "Message Hash: " + createMessageHash() + "\n" +
               "Recipient: " + phoneNumb + "\n" +
               "Message: " + messageText;
    }

    public static int returnTotalMessages() {
        return totalSent;
    }
}
