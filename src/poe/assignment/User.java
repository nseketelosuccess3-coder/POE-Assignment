package poe.assignment;

import java.util.Scanner;

public class User {

    private String firstName;
    private String lastName;
    private String userName;
    private String password;
    private String cellPhoneNumber;

    public User(String firstName, String lastName, String userName,
                String password, String cellPhoneNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.password = password;
        this.cellPhoneNumber = cellPhoneNumber;
    }

    public boolean checkUserName() {
        return userName.contains("_") && userName.length() <= 5;
    }

    public boolean checkPasswordComplexity() {
        if (password.length() < 8) return false;

        boolean hasCapital = false;
        boolean hasNumber = false;
        boolean hasSpecial = false;

        for (char c : password.toCharArray()) {
            if (Character.isUpperCase(c)) {
                hasCapital = true;
            } else if (Character.isDigit(c)) {
                hasNumber = true;
            } else if (!Character.isLetterOrDigit(c)) {
                hasSpecial = true;
            }
        }

        return hasCapital && hasNumber && hasSpecial;
    }

    public boolean checkCellPhoneNumber() {
        if (!cellPhoneNumber.startsWith("+27") || cellPhoneNumber.length() != 12) {
            return false;
        }

        String digits = cellPhoneNumber.substring(3);

        for (char c : digits.toCharArray()) {
            if (!Character.isDigit(c)) {
                return false;
            }
        }

        return true;
    }

    public boolean loginUser(String enteredUsername, String enteredPassword) {
        return this.userName.equals(enteredUsername)
                && this.password.equals(enteredPassword);
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getUserName() {
        return userName;
    }

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        System.out.println("==== REGISTRATION ====");

        System.out.print("Enter first name: ");
        String firstName = scanner.nextLine();

        System.out.print("Enter last name: ");
        String lastName = scanner.nextLine();

        System.out.println();

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

        System.out.println();

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

        User user = new User(firstName, lastName, userName, password, cellPhoneNumber);

        System.out.println("\nRegistration successful!");

        System.out.println("\n======== LOGIN ========");

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

        boolean messaging = true;

        while (messaging) {

            messagingMenu(scanner);

            System.out.print("\nDo you want to continue messaging? (yes/no): ");
            String continueChoice = scanner.nextLine();

            if (!continueChoice.equalsIgnoreCase("yes")) {
                messaging = false;
            }
        }

        System.out.println("\n======== PROGRAM END ========");
        System.out.println("Total messages sent during session: " + Message.returnTotalMessages());
        System.out.println("Thank you for using the system!");

        scanner.close();
    }

    public static void messagingMenu(Scanner scanner) {

        System.out.println("\n======== MESSAGING MENU ========");
        System.out.println("Choose option:");
        System.out.println("1) Send Messages");
        System.out.println("2) Show recently sent messages");
        System.out.println("3) View message statistics");
        System.out.print("Enter your choice (1-3): ");

        int choice;

        try {
            choice = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Please enter a valid number (1-3)");
            return;
        }

        switch (choice) {

            case 1:
                sendMessages(scanner);
                break;

            case 2:
                System.out.println("Feature coming soon! No messages stored yet.");
                break;

            case 3:
                System.out.println("Total messages sent: " + Message.returnTotalMessages());
                break;

            default:
                System.out.println("Invalid choice. Please enter 1, 2, or 3.");
        }
    }

    public static void sendMessages(Scanner scanner) {

        System.out.println("\n======== SEND MESSAGES ========");

        System.out.print("How many messages do you want to send? ");

        int total;

        try {
            total = Integer.parseInt(scanner.nextLine());

            if (total <= 0) {
                System.out.println("Please enter a positive number.");
                return;
            }

        } catch (NumberFormatException e) {
            System.out.println("Please enter a valid number.");
            return;
        }

        int sentCount = 0;

        for (int i = 0; i < total; i++) {

            System.out.println("\n--- Message " + (i + 1) + " of " + total + " ---");

            String phoneNum;

            while (true) {

                System.out.print("Enter recipient number e.g. +27123456789: ");
                phoneNum = scanner.nextLine();

                Message msg = new Message(phoneNum, "");

                String result = msg.checkRecipientCell();

                if (result.equals("Cell phone number is correctly formatted")) {
                    break;
                } else {
                    System.out.println(result);
                    System.out.println("Please try again.");
                }
            }

            String txt;

            while (true) {

                System.out.print("Enter your message (max 250 chars): ");
                txt = scanner.nextLine();

                if (txt.length() <= 250) {
                    break;
                } else {
                    System.out.println("Please enter a message of less than 250.");
                }
            }

            Message msg = new Message(phoneNum, txt);

            if (!msg.checkMessageLength()) {
                System.out.println("Message is too long! Maximum 250 characters allowed.");
                i--;
                continue;
            }

            System.out.println("\nChoose what to do:");
            System.out.println("1) Send Message");
            System.out.println("2) Disregard Message");
            System.out.println("3) Store Message");
            System.out.print("Enter your choice (1-3): ");

            int opt;

            try {
                opt = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid choice. Please enter 1, 2, or 3.");
                continue;
            }

            String sendResult = msg.SentMessage(opt);

            System.out.println("\nResult: " + sendResult);

            if (opt == 1) {
                sentCount++;

                System.out.println("\nMessage Details:");
                System.out.println(msg.printMessages());
            }
        }

        System.out.println("\n======== SUMMARY ========");
        System.out.println("Total messages sent in this session: " + sentCount);
        System.out.println("Total messages sent overall: " + Message.returnTotalMessages());
    }
}