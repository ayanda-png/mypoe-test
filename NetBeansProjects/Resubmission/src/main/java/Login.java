import java.util.Scanner;
import javax.lang.model.SourceVersion;

class Login {

    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private String phoneNumber;

    // Parameterized constructor
    public Login(String firstName, String lastName, String username, String password, String phoneNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.phoneNumber = phoneNumber;
    }

    // Default constructor
    public Login() {
    }

    // Getters and Setters
    public String getFirstName()                   { return firstName; }
    public void setFirstName(String firstName)     { this.firstName = firstName; }
    public String getLastName()                    { return lastName; }
    public void setLastName(String lastName)       { this.lastName = lastName; }
    public String getUsername()                    { return username; }
    public void setUsername(String username)       { this.username = username; }
    public String getPassword()                    { return password; }
    public void setPassword(String password)       { this.password = password; }
    public String getPhoneNumber()                 { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    // Validate username: must contain '_' and be 5 chars or less
    public boolean checkUserName(String username) {
        if (username == null || username.isEmpty()) return false;
        return username.contains("_") && username.length() <= 5;
    }

    // Validate password: min 8 chars, 1 uppercase, 1 digit, 1 special char
    public boolean checkPassword(String password) {
        if (password == null || password.length() < 8) return false;

        boolean hasUpperCase = false;
        boolean hasNumber = false;
        boolean hasSpecialCharacter = false;

        for (int a = 0; a < password.length(); a++) {
            char ch = password.charAt(a);
            if (Character.isUpperCase(ch))           hasUpperCase = true;
            else if (Character.isDigit(ch))          hasNumber = true;
            else if (!Character.isLetterOrDigit(ch)) hasSpecialCharacter = true;
        }

        return hasUpperCase && hasNumber && hasSpecialCharacter;
    }

    // Validate phone: must start with +27 and be exactly 12 chars
    public boolean checkPhoneNumber(String phone) {
        if (phone == null) return false;
        if (!phone.startsWith("+27")) return false;
        if (phone.length() != 12) return false;

        for (int a = 3; a < phone.length(); a++) {
            if (!Character.isDigit(phone.charAt(a))) return false;
        }
        return true;
    }

    // Register method
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

        System.out.print(" Username (must contain '_' and be 5 chars or less): ");
        String inputUsername = key.nextLine();
        if (checkUserName(inputUsername)) {
            this.username = inputUsername;
            System.out.println(" Username added.");
        } else {
            System.out.println(" Invalid username.");
            return;
        }

        System.out.print(" Password (8+ chars, 1 uppercase, 1 digit, 1 special): ");
        String inputPassword = key.nextLine();
        if (checkPassword(inputPassword)) {
            this.password = inputPassword;
            System.out.println(" Password added.");
        } else {
            System.out.println(" Invalid password.");
            return;
        }

        System.out.print(" Phone Number (+27XXXXXXXXX): ");
        String phone = key.nextLine();
        if (checkPhoneNumber(phone)) {
            this.phoneNumber = phone;
            System.out.println(" Phone number added.");
        } else {
            System.out.println(" Invalid phone number.");
            return;
        }

        System.out.println("\n Registration successful!");
    }

    // Login method
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

    // Reset method
    public void reset() {
        firstName   = null;
        lastName    = null;
        username    = null;
        password    = null;
        phoneNumber = null;
        System.out.println(" User data has been reset.");
    }

     {
       
    }
}

class Main {
    public static void main(String[] args) {
        Scanner key = new Scanner(System.in);
        Login user = new Login(); 
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
        
        key.close();
    }
}







