package poe.assignment;

import javax.swing.JOptionPane;
import java.util.Scanner;

public class User {

    // Instance variables
    private String firstName;
    private String lastName;
    private String userName;
    private String password;
    private String cellPhoneNumber;

    // Constructor
    public User(String firstName, String lastName, String userName,
                String password, String cellPhoneNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.password = password;
        this.cellPhoneNumber = cellPhoneNumber;
    }

    // METHOD 1: checkUserName
    public boolean checkUserName() {
        return userName.contains("_") && userName.length() <= 5;
    }

    // METHOD 2: checkPasswordComplexity
    public boolean checkPasswordComplexity() {
        if (password.length() < 8) return false;

        boolean hasCapital = false;
        boolean hasNumber = false;
        boolean hasSpecial = false;

        for (char c : password.toCharArray()) {
            if (Character.isUpperCase(c)) hasCapital = true;
            else if (Character.isDigit(c)) hasNumber = true;
            else if (!Character.isLetterOrDigit(c)) hasSpecial = true;
        }

        return hasCapital && hasNumber && hasSpecial;
    }

    // METHOD 3: checkCellPhoneNumber
    public boolean checkCellPhoneNumber() {
        if (!cellPhoneNumber.startsWith("+27") || cellPhoneNumber.length() != 12) {
            return false;
        }

        String digits = cellPhoneNumber.substring(3);
        for (char c : digits.toCharArray()) {
            if (!Character.isDigit(c)) return false;
        }

        return true;
    }

    // METHOD 4: login check
    public boolean loginUser(String enteredUsername, String enteredPassword) {
        return this.userName.equals(enteredUsername) &&
               this.password.equals(enteredPassword);
    }

    // Getters for the user information
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getUserName() { return userName; }

    // MAIN METHOD
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("==== REGISTRATION ====");

        System.out.print("Enter first name: ");
        String firstName = scanner.nextLine();

        System.out.print("Enter last name: ");
        String lastName = scanner.nextLine();
        System.out.println("\n");

        // USERNAME LOOP
        String userName;
        while (true) {
            System.out.print("Enter username: ");
            userName = scanner.nextLine();

            User temp = new User(firstName, lastName, userName, "", "");

            if (temp.checkUserName()) {
                System.out.println("Correct username");
                break;
            } else {
                System.out.println("Wrong username. It must contain '_' and be no more than 5 characters.");
            }
        }

        // PASSWORD LOOP
        String password;
        while (true) {
            System.out.print("Enter password: ");
            password = scanner.nextLine();

            User temp = new User(firstName, lastName, userName, password, "");

            if (temp.checkPasswordComplexity()) {
                System.out.println("Correct password");
                break;
            } else {
                System.out.println("Wrong password. Must be 8+ characters, include a capital letter, a number, and a special character.");
            }
        }
        System.out.println("\n");

        // CELL PHONE LOOP
        String cellPhoneNumber;
        while (true) {
            System.out.print("Enter cell phone number: ");
            cellPhoneNumber = scanner.nextLine();

            User temp = new User(firstName, lastName, userName, password, cellPhoneNumber);

            if (temp.checkCellPhoneNumber()) {
                System.out.println("Correct cell phone number");
                break;
            } else {
                System.out.println("Wrong number used. Must start with +27 and have 9 digits after it.");
            }
        }

        // Create final registered user
        User user = new User(firstName, lastName, userName, password, cellPhoneNumber);

        System.out.println("\nRegistration successful!");

        System.out.println(" LOGIN ");

        while (true) {
            System.out.print("Enter username: ");
            String enteredUsername = scanner.nextLine();

            System.out.print("Enter password: ");
            String enteredPassword = scanner.nextLine();

            if (user.loginUser(enteredUsername, enteredPassword)) {
                System.out.println("Welcome " + firstName + " " + lastName + ", it is nice to see you again.");
                break;
            } else {
                System.out.println("Try again");
            }
        }
        System.out.println("\n");

        // Start the messaging menu
        messagingMenu();
        scanner.close();
    }

    // Messaging menu method
    public static void messagingMenu() {
        boolean running = true;
        
        while (running) {
            String menu = "Choose option:\n1) Send Messages\n2) Show recently sent messages\n3) Quit";
            String input = JOptionPane.showInputDialog(menu);
            
            if (input == null) {
                running = false;
                break;
            }
            
            int choice;
            try {
                choice = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Please enter a valid number (1-3)");
                continue;
            }
            
            switch (choice) {
                case 1:
                    sendMessages();
                    break;
                case 2:
                    JOptionPane.showMessageDialog(null, "Coming Soon.");
                    break;
                case 3:
                    running = false;
                    JOptionPane.showMessageDialog(null, "Goodbye!");
                    break;
                default:
                    JOptionPane.showMessageDialog(null, "Invalid choice. Please enter 1, 2, or 3.");
            }
        }
    }

    // Method to send messages
    public static void sendMessages() {
        String numMessagesStr = JOptionPane.showInputDialog("How many messages do you want to send?");
        if (numMessagesStr == null) return;
        
        int total;
        try {
            total = Integer.parseInt(numMessagesStr);
            if (total <= 0) {
                JOptionPane.showMessageDialog(null, "Please enter a positive number.");
                return;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Please enter a valid number.");
            return;
        }
        
        int sentCount = 0;
        
        for (int i = 0; i < total; i++) {
            String phoneNum = JOptionPane.showInputDialog("Enter recipient number e.g. +27123456789");
            if (phoneNum == null) break;
            
            String txt = JOptionPane.showInputDialog("Enter your message");
            if (txt == null) break;
            
            Message msg = new Message(phoneNum, txt);
            
            // Check message length
            if (!msg.checkMessageLength()) {
                JOptionPane.showMessageDialog(null, "Please enter a message of less than 250 characters.");
                i--; // Try again
                continue;
            }
            
            // Check recipient cell number
            String result = msg.checkRecipientCell();
            JOptionPane.showMessageDialog(null, result);
            
            if (result.startsWith("Cell phone number is incorrectly")) {
                i--; // Try again
                continue;
            }
            
            // Ask what to do with the message
            String options = "Choose:\n1) Send Message\n2) Disregard Message\n3) Store Message";
            String optInput = JOptionPane.showInputDialog(options);
            if (optInput == null) break;
            
            int opt;
            try {
                opt = Integer.parseInt(optInput);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Invalid choice. Please enter 1, 2, or 3.");
                i--;
                continue;
            }
            
            String sendResult = msg.SentMessage(opt);
            JOptionPane.showMessageDialog(null, sendResult);
            
            if (opt == 1) {
                sentCount++;
                JOptionPane.showMessageDialog(null, "Message sent successfully!");
            }
        }
        
        JOptionPane.showMessageDialog(null, "Total messages sent: " + sentCount);
    }
}