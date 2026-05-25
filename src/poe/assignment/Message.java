package poe.assignment;

public class Message {

    private String recipientCell;
    private String messageText;
    private static int totalMessagesSent = 0;
    private long messageID;
    private String hash;

    // Constructor
    public Message(String recipientCell, String messageText) {
        this.recipientCell = recipientCell;
        this.messageText = messageText;

        // Automatically create message hash
        createMessageHash(totalMessagesSent, messageText);
    }

    // Check if message is less than 250 characters
    public boolean checkMessageID() {
        return messageText.length() <= 250;
    }

    // Create message hash
    public String createMessageHash(int messageNumber, String message) {

        // Generate random 10-digit message ID
        messageID = (long) (Math.random() * 9000000000L) + 1000000000L;

        // Get first two digits
        String firstTwo = String.valueOf(messageID).substring(0, 2);

        // Split message into words
        String[] words = message.trim().split("\\s+");

        // First and last word
        String firstWord = words[0];
        String lastWord = words[words.length - 1];

        // Create hash
        hash = firstTwo + ":" + messageNumber + ":"
                + firstWord + lastWord;

        // Convert to uppercase
        hash = hash.toUpperCase();

        return hash;
    }

    // Check recipient cell number format
    public String checkRecipientCell() {

        if (!recipientCell.startsWith("+")
                || recipientCell.length() > 15) {

            return "Cell phone number is incorrectly formatted";
        }

        // Check digits after +
        for (int i = 1; i < recipientCell.length(); i++) {

            if (!Character.isDigit(recipientCell.charAt(i))) {

                return "Cell phone number is incorrectly formatted";
            }
        }

        return "Cell phone number is correctly formatted";
    }

    // Process message option
    public String sentMessage(int option) {

        switch (option) {

            case 1:
                totalMessagesSent++;
                return "Message sent successfully";

            case 2:
                return "Message disregarded";

            case 3:
                return "Message stored for later";

            default:
                return "Invalid option";
        }
    }

    // Print message details
    public String printMessages() {

        return "To: " + recipientCell
                + "\nMessage: " + messageText
                + "\nMessageID: " + messageID
                + "\nMessage hash: " + hash;
    }

    // Return total messages sent
    public static int returnTotalMessages() {
        return totalMessagesSent;
    }
}