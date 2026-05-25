
import java.util.*;


public class Registration {

    private String userName;
    private String password;
    private String phoneNumber;

    // Username validation
    public boolean checkUsername(String userName) {
        return userName.contains("_") && userName.length() <= 5;
    }

    // Password validation
    public boolean checkPassword(String password) {

        if (password.length() < 8)
            return false;

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

    
    public static void main(String[] args) {

        Scanner key = new Scanner(System.in);
        Registration reg = new Registration();

        System.out.print("Enter username: ");
        String username = key.nextLine();

        if (reg.checkUsername(username)) {
            System.out.println("Valid username");
        } else {
            System.out.println("Invalid username");
        }

        System.out.print("Enter password: ");
        String password = key.nextLine();

        if (reg.checkPassword(password)) {
            System.out.println("Valid password");
        } else {
            System.out.println("Invalid password");
        }

        key.close();
    }
}



