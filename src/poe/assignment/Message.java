package poe.assignment;

public class Message {
    private String recipientCell;
    private String messageText;
    private static int totalMessagesSent = 0;
    
    // Constructor
    public Message(String recipientCell, String messageText) {
        this.recipientCell = recipientCell;
        this.messageText = messageText;
    }
    
    // Check if message is less than 250 characters
    public boolean checkMessageID() {
        return messageText.length() <= 250;
    }
    public String createMessageHash(String messageID, int messageNumber, String message) {
    
    // First two numbers of message ID
    String firstTwo = messageID.substring(0, 2);

    // Split message into words
    String[] words = message.trim().split("\\s+");

    // First and last words
    String firstWord = words[0];
    String lastWord = words[words.length - 1];

    // Create hash
    String hash = firstTwo + ":" + messageNumber + ":" 
            + firstWord + lastWord;

    // Return in uppercase
    return hash.toUpperCase();
}
    
    // Check recipient cell number format
    public String checkRecipientCell() {
        if (recipientCell.length() <=15 && !recipientCell.startsWith("+")) {
            return "Cell phone number is incorrectly formatted";
        }
        
        // Check if all characters after +27 are digits
        for (int i = 3; i < recipientCell.length(); i++) {
            if (!Character.isDigit(recipientCell.charAt(i))) {
                return "Cell phone number is incorrectly formatted";
            }
        }
        
        return "Cell phone number is correctly formatted";
    }
    
    // Process message based on user choice
    public String SentMessage(int option) {
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
        return "To: " + recipientCell + "\nMessage: " + messageText;
    }
    
    // Return total messages sent
    public static int returnTotalMessages() {
        return totalMessagesSent;
    }
}
