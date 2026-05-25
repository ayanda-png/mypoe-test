  import java.util.Scanner;

class Registration {  

    private String firstName;
    private String lastName;
    private String username;
    private String password;  

    public void register(Scanner key) {
        System.out.println("\n= REGISTER =");

        System.out.print(" First Name: ");
        firstName = key.nextLine().trim();
        if (firstName.isEmpty()) {
            System.out.println(" First name cannot be empty.");
            return;
        }

        System.out.print(" Last Name: ");
        lastName = key.nextLine().trim();
        if (lastName.isEmpty()) {
            System.out.println(" Last name cannot be empty.");
            return;
        }

        System.out.print(" Username (must contain '_' and be ≤5 chars): ");
        String username = key.nextLine();

        if (checkUserName(username)) {
            this.username = username;
            System.out.println(" Username added.");
        } else {
            System.out.println(" Invalid username. Must contain '_' and be ≤5 characters.");
            return;
        }

        System.out.print(" Password (≥8 chars, 1 uppercase, 1 digit, 1 special): ");
        String password = key.nextLine();
        if (checkPassword(password)) {
            this.password = password;
            System.out.println(" Password added.");
        } else {
            System.out.println(" Invalid password format.");
            return;
        }

        System.out.print(" Phone Number (+27..): ");
        String phone = key.nextLine();
        if (!checkPhoneNumber(phone)) {  // ✅ added missing closing ) 
            System.out.println(" Invalid phone format. Must be +27 followed by 9 digits.");
            return;
        } else {
            System.out.println(" Phone number successfully added.");
        }

        System.out.println("\n Registration successful!");
    }

    public void login(Scanner key) {
        System.out.println("\n= LOGIN =");

        if (username == null || password == null) {
            System.out.println(" No registered user found. Please register first.");
            return;
        }

        System.out.print(" Username: ");
        String inputUser = key.nextLine();

        System.out.print(" Password: ");
        String inputPass = key.nextLine();

        if (inputUser.equals(username) && inputPass.equals(password)) {
            System.out.println("\n Welcome, " + firstName + " " + lastName + "! Login successful.");
        } else {
            System.out.println("\n Invalid username or password.");
        }
    }

    //  validate username
    public boolean checkUserName(String username) {
        if (username == null || username.isEmpty()) return false;
        return username.contains("_") && username.length() <= 5;
    }

    //  validate password
    public boolean checkPassword(String password) {
        if (password == null || password.length() < 8) return false;

        boolean hasUpperCase = false, hasNumber = false, hasSpecialCharacter = false;

        for (int a = 0; a < password.length(); a++) {
            char ch = password.charAt(a);
            if (Character.isUpperCase(ch))           hasUpperCase = true;
            else if (Character.isDigit(ch))          hasNumber = true;
            else if (!Character.isLetterOrDigit(ch)) hasSpecialCharacter = true;
        }
        return hasUpperCase && hasNumber && hasSpecialCharacter;
    }

    //  validate phone
    public boolean checkPhoneNumber(String phone) {
        if (phone == null || phone.isEmpty()) return false;
        if (!phone.startsWith("+27") || phone.length() != 12) return false;

        for (int a = 3; a < phone.length(); a++) {
            if (!Character.isDigit(phone.charAt(a))) return false;
        }
        return true;
    }
}

public class Main {  
    public static void main(String[] args) {
        try (Scanner key = new Scanner(System.in)) {
            Registration user = new Registration();  
            int choice;
            
            do {
                System.out.println("\n=== MENU ===");
                System.out.println("1. Register");
                System.out.println("2. Login");
                System.out.println("3. Exit");
                System.out.print("Choose option: ");
                
                while (!key.hasNextInt()) {
                    System.out.print("Please enter a number (1-3): ");
                    key.next();
                }
                choice = key.nextInt();
                key.nextLine();
                
                switch (choice) {
                    case 1 -> user.register(key);
                    case 2 -> user.login(key);
                    case 3 -> System.out.println("Goodbye!");
                    default -> System.out.println("Invalid choice.");
                }
            } while (choice != 3);
        } 
    }
}
   
