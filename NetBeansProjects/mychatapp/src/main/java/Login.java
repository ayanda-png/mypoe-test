import java.util.Scanner;

class Login {

    private String Username;
    private String Password;
    private String Phone;

    // Username validation
    public boolean checkUserName(String username) {
        return username.contains("_") && username.length() <= 5;
    }

    // Password validation ()
    public boolean checkPassword(String password) {

        if (password.length() < 8) return false;

        boolean hasUpper = false;
        boolean hasNumber = false;
        boolean hasSpecial = false;

        for (int i = 0; i < password.length(); i++) {
            char ch = password.charAt(i);

            if (Character.isUpperCase(ch)) {
                hasUpper = true;
            } else if (Character.isDigit(ch)) {
                hasNumber = true;
            } else if (!Character.isLetterOrDigit(ch)) {
                hasSpecial = true;
            }
        }

        return hasUpper && hasNumber && hasSpecial;
    }

    // Cell phone validation ()
    public boolean checkPhone(String phone) {

        if (!phone.startsWith("+27")) return false;
        if (phone.length() != 12) return false;

        for (int i = 3; i < phone.length(); i++) {
            if (!Character.isDigit(phone.charAt(i))) {
                return false;
            }
        }

        return true;
    }

    // Register method
    public void register(Scanner key) {

        System.out.println("== REGISTER ==");

        System.out.print("Enter username: ");
        String username = key.nextLine();

        if (checkUserName(username)) {
            System.out.println("Username successfully entered");
            Username = username;
        } else {
            System.out.println("Username is  incorrect.");
            return; // stop if invalid
        }

        System.out.print("Enter password: ");
        String password = key.nextLine();

        if (checkPassword(password)) {
            System.out.println("Password successfully entered.");
            Password = password;
        } else {
            System.out.println("Password is incorrect");
            return;
        }

        System.out.print("Enter cell phone (+27...): ");
        String phone = key.nextLine();

        if (checkPhone(phone)) {
            System.out.println("Cell phone number successfully added.");
            Phone = phone;
        } else {
            System.out.println("Cell phone number incorrectly entered.");
            return;
        }

        System.out.println("Registration successful!\n");
    }

    // Login method
    public void login(Scanner key) {

        System.out.println("=== LOGIN ===");

        System.out.print("Enter username: ");
        String username = key.nextLine();

        System.out.print("Enter password: ");
        String password = key.nextLine();

        if (username.equals(Username) && password.equals(Password)) {
            System.out.println("Welcome back! Login successful.");
        } else {
            System.out.println("Username or password incorrect, please try again.");
        }
    }
}

public class Main {
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

            choice = key.nextInt();
            key.nextLine(); 


            switch (choice) {
                case 1:
                    user.register(key);
                    break;

                case 2:
                    user.login(key);
                    break;

                case 3:
                    System.out.println("Goodbye!");
                    break;

                default:
                    System.out.println("Invalid choice.");
            }

        } while (choice != 3);

        key.close();
    }
}


    

    
    
    

        
        

       

        

        

        
   
 



