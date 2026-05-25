


import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Login System - Full Test Suite")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class LoginTest {

    private Login login;
    private ConsoleCapture console;

    @BeforeEach
    void setUp() {
        login = new Login();
        console = new ConsoleCapture();
    }

    @AfterEach
    void tearDown() {
        console.stopCapture();
        // Reset login state between tests
        if (login != null) {
            login.reset(); // Requires reset() method in Login.java
        }
    }

    
    // VALIDATION METHOD TESTS (Unit Tests)
    

    @Nested
    @DisplayName(" Username Validation")
    class UsernameValidation {

        @ParameterizedTest
        @ValueSource(strings = {"ab_c", "user_", "abc", "a_b", "__"})
        @DisplayName(" Valid: contains underscore AND length ≤ 5")
        void validUsernames(String username) {
            assertTrue(login.checkUserName(username), 
                "Should accept: " + username + " ");
        }

        @ParameterizedTest
        @ValueSource(strings = {"abcde", "user", "name", "", "a"})
        @DisplayName(" Invalid: missing underscore")
        void invalidUsernames_NoUnderscore(String username) {
            assertFalse(login.checkUserName(username));
        }

        @ParameterizedTest
        @ValueSource(strings = {"abc_def", "user_name", "a_b_c_d", "____"})
        @DisplayName(" Invalid: length > 5")
        void invalidUsernames_TooLong(String username) {
            assertFalse(login.checkUserName(username));
        }
    }

    @Nested
    @DisplayName(" Password Validation")
    class PasswordValidation {

        @ParameterizedTest
        @ValueSource(strings = {"Abc123!@", "Passw0rd#", "A1!aaaaa", "Test@123", "Xy9$zzzz"})
        @DisplayName(" Valid: meets all criteria (≥8, uppercase, digit, special)")
        void validPasswords(String password) {
            assertTrue(login.checkPassword(password));
        }

        @Test
        @DisplayName("Invalid: too short (< 8 chars)")
        void passwordTooShort() {
            assertFalse(login.checkPassword("Ab1!xyz")); // 7 chars
        }

        @Test
        @DisplayName("Invalid: missing uppercase")
        void passwordNoUpperCase() {
            assertFalse(login.checkPassword("abc123!@#"));
        }

        @Test
        @DisplayName("Invalid: missing digit")
        void passwordNoDigit() {
            assertFalse(login.checkPassword("Abcdefg!@"));
        }

        @Test
        @DisplayName(" Invalid: missing special character")
        void passwordNoSpecialChar() {
            assertFalse(login.checkPassword("Abcdefg12"));
        }

        @ParameterizedTest
        @ValueSource(strings = {"", " ", "12345678", "ABCDEFGH", "!!!!!!!!"})
        @DisplayName("Invalid: edge cases")
        void passwordEdgeCases(String password) {
            assertFalse(login.checkPassword(password));
        }
    }

    @Nested
    @DisplayName("Phone Number Validation")
    class PhoneNumberValidation {

        @ParameterizedTest
        @ValueSource(strings = {"+27123456789", "+27821234567", "+27000000000", "+27999999999"})
        @DisplayName(" Valid: +27 prefix + exactly 9 digits")
        void validPhoneNumbers(String phone) {
            assertTrue(login.checkPhoneNumber(phone));
        }

        @ParameterizedTest
        @ValueSource(strings = {
            "0821234567",      // no +27
            "+26123456789",    // wrong country code
            "27123456789",     // missing +
            "+2712345678",     // too short (11 chars)
            "+271234567890"    // too long (13 chars)
        })
        @DisplayName(" Invalid: wrong prefix or length")
        void invalidPhoneNumbers_Format(String phone) {
            assertFalse(login.checkPhoneNumber(phone));
        }

        @ParameterizedTest
        @ValueSource(strings = {
            "+2712345678a",
            "+27abc123456",
            "+2712-3456789",
            "+27 123456789"
        })
        @DisplayName("✗ Invalid: non-digit characters after prefix")
        void invalidPhoneNumbers_NonDigit(String phone) {
            assertFalse(login.checkPhoneNumber(phone));
        }
    }

    
    // REGISTRATION FLOW TESTS (Integration-style)
   

    @Nested
    @DisplayName(" Registration Flow")
    class RegistrationFlow {

        @Test
        @Order(1)
        @DisplayName(" Success: all inputs valid")
        void register_success_allValid() {
            // Arrange
            Scanner mockInput = InputMocker.mockInput("ab_c", "Abc123!@", "+27123456789");
            console.startCapture();

            // Act
            login.register(mockInput);

            // Assert
            String output = console.getOutput();
            assertTrue(output.contains("Username successfully added"));
            assertTrue(output.contains("Password successfully added"));
            assertTrue(output.contains("Cell phone number successfully added"));
            assertTrue(output.contains("Registration successful"));

            // Verify state (requires getters)
            assertEquals("ab_c", login.getUsername());
            assertEquals("Abc123!@", login.getPassword());
            assertEquals("+27123456789", login.getPhoneNumber());

            mockInput.close();
        }

        @Test
        @Order(2)
        @DisplayName("Fail: invalid username (no underscore)")
        void register_fail_invalidUsername() {
            Scanner mockInput = InputMocker.mockInput("invalid", "Abc123!@", "+27123456789");
            console.startCapture();

            login.register(mockInput);

            String output = console.getOutput();
            assertTrue(output.contains("Username is not correct"));
            assertFalse(output.contains("Password successfully added"));
            assertNull(login.getUsername()); // Should not be set

            mockInput.close();
        }

        @Test
        @Order(3)
        @DisplayName(" Fail: invalid username (too long)")
        void register_fail_usernameTooLong() {
            Scanner mockInput = InputMocker.mockInput("abc_def", "Abc123!@", "+27123456789");
            console.startCapture();

            login.register(mockInput);

            String output = console.getOutput();
            assertTrue(output.contains("Username is not correct"));
            assertFalse(output.contains("Registration successful"));

            mockInput.close();
        }

        @Test
        @Order(4)
        @DisplayName(" Fail: invalid password (too short)")
        void register_fail_passwordTooShort() {
            Scanner mockInput = InputMocker.mockInput("ab_c", "Ab1!xyz", "+27123456789");
            console.startCapture();

            login.register(mockInput);

            String output = console.getOutput();
            assertTrue(output.contains("Username successfully added"));
            assertTrue(output.contains("Password is incorrect"));
            assertFalse(output.contains("Cell phone number successfully added"));

            mockInput.close();
        }

        @Test
        @Order(5)
        @DisplayName(" Fail: invalid password (no special char)")
        void register_fail_passwordNoSpecialChar() {
            Scanner mockInput = InputMocker.mockInput("ab_c", "Abcdefg1", "+27123456789");
            console.startCapture();

            login.register(mockInput);

            String output = console.getOutput();
            assertTrue(output.contains("Password is incorrect"));
            assertNull(login.getPassword());

            mockInput.close();
        }
        

        @Test
        @Order(6)
        @DisplayName(" Fail: invalid phone (wrong prefix)")
        void register_fail_phoneWrongPrefix() {
            try (Scanner mockInput = InputMocker.mockInput("ab_c", "Abc123!@", "0821234567")) {
                console.startCapture();
                
                login.register(mockInput);
                
                String output = console.getOutput();
                assertTrue(output.contains("Username successfully added"));
                assertTrue(output.contains("Password successfully added"));
                assertTrue(output.contains("Cell phone number is incorrect"));
                assertFalse(output.contains("Registration successful"));
            }
        }

        @Test
        @Order(7)
        @DisplayName("Fail: invalid phone (non-digit after +27)")
        void register_fail_phoneNonDigit() {
            Scanner mockInput = InputMocker.mockInput("ab_c", "Abc123!@", "+2712345678a");
            console.startCapture();

            login.register(mockInput);

            String output = console.getOutput();
            assertTrue(output.contains("Cell phone number is incorrect"));

            mockInput.close();
        }

        @Test
        @Order(8)
        @DisplayName(" Registration is isolated: previous data doesn't affect new registration")
        void register_testIsolation() {
            // First registration
            Scanner input1 = InputMocker.mockInput("user_1", "Pass1!@#", "+27111111111");
            console.startCapture();
            login.register(input1);
            console.stopCapture();
            input1.close();

            // Reset and try second registration with different data
            login.reset();
            console.startCapture();
            Scanner input2 = InputMocker.mockInput("user_2", "Pass2!@#", "+27222222222");
            login.register(input2);

            String output = console.getOutput();
            assertTrue(output.contains("Registration successful"));
            assertEquals("user_2", login.getUsername());
            assertEquals("+27222222222", login.getPhoneNumber());

            input2.close();
        }
    }

    
    //  LOGIN FLOW TESTS (Integration-style)
    

    @Nested
    @DisplayName(" Login Flow")
    class LoginFlow {

        @BeforeEach
        void setupRegisteredUser() {
            // Pre-register a user for login tests
            login.register(InputMocker.mockInput("test_user", "Secure@123", "+27987654321"));
        }

        @Test
        @Order(1)
        @DisplayName("Success: correct username and password")
        void login_success_correctCredentials() {
            console.startCapture();
            try (Scanner mockInput = InputMocker.mockInput("test_user", "Secure@123")) {
                login.login(mockInput);
                
                String output = console.getOutput();
                assertTrue(output.contains("Welcome back! Login successful"));
                assertFalse(output.contains("incorrect"));
            }
        }

        @Test
        @Order(2)
        @DisplayName("Fail: wrong username")
        void login_fail_wrongUsername() {
            console.startCapture();
            Scanner mockInput = InputMocker.mockInput("wrong_user", "Secure@123");

            login.login(mockInput);

            String output = console.getOutput();
            assertTrue(output.contains("Username or password was incorrect"));
            assertFalse(output.contains("Welcome back"));

            mockInput.close();
        }

        @Test
        @Order(3)
        @DisplayName(" Fail: wrong password")
        void login_fail_wrongPassword() {
            console.startCapture();
            Scanner mockInput = InputMocker.mockInput("test_user", "WrongPass1!");

            login.login(mockInput);

            String output = console.getOutput();
            assertTrue(output.contains("Username or password was incorrect"));

            mockInput.close();
        }

        @Test
        @Order(4)
        @DisplayName(" Fail: both username and password wrong")
        void login_fail_bothWrong() {
            console.startCapture();
            Scanner mockInput = InputMocker.mockInput("fake_user", "FakePass1!");

            login.login(mockInput);

            String output = console.getOutput();
            assertTrue(output.contains("Username or password was incorrect"));

            mockInput.close();
        }

        @Test
        @Order(5)
        @DisplayName(" Fail: login before registration (empty credentials)")
        void login_fail_noUserRegistered() {
            // Create fresh login instance with no registration
            Login freshLogin = new Login();
            console.startCapture();
            Scanner mockInput = InputMocker.mockInput("any_user", "any_pass");

            freshLogin.login(mockInput);

            String output = console.getOutput();
            assertTrue(output.contains("Username or password was incorrect"));

            mockInput.close();
        }

        @Test
        @Order(6)
        @DisplayName("✓ Case-sensitive: username/password matching is exact")
        void login_caseSensitive() {
            // Register with lowercase username
            login.reset();
            login.register(InputMocker.mockInput("abc_1", "Pass@123", "+27100000000"));

            console.startCapture();
            // Try login with uppercase variation
            Scanner mockInput = InputMocker.mockInput("ABC_1", "Pass@123");
            login.login(mockInput);

            String output = console.getOutput();
            assertTrue(output.contains("incorrect"), "Login should be case-sensitive");

            mockInput.close();
        }
    }

 
    //  END-TO-END: Register THEN 

    @Test
    @Order(99)
    @DisplayName(" E2E: Register new user → Login with same credentials")
    void e2e_registerThenLogin() {
        // Step 1: Register
        console.startCapture();
        Scanner registerInput = InputMocker.mockInput("e2e_usr", "E2E@Pass9", "+27555123456");
        login.register(registerInput);
        
        String registerOutput = console.getOutput();
        assertTrue(registerOutput.contains("Registration successful"));
        registerInput.close();

        // Step 2: Login with same credentials
        console.reset();
        console.startCapture();
        Scanner loginInput = InputMocker.mockInput("e2e_usr", "E2E@Pass9");
        login.login(loginInput);

        String loginOutput = console.getOutput();
        assertTrue(loginOutput.contains("Welcome back! Login successful"));
        loginInput.close();
    }

    @Test
    @Order(100)
    @DisplayName("E2E: Register → Try login with wrong password")
    void e2e_registerThenLoginWrongPassword() {
        // Register
        console.startCapture();
        login.register(InputMocker.mockInput("e2e_test", "Correct@1", "+27444555666"));
        console.stopCapture();

        // Try login with wrong password
        console.startCapture();
        Scanner loginInput = InputMocker.mockInput("e2e_test", "Wrong@Pass");
        login.login(loginInput);

        String output = console.getOutput();
        assertTrue(output.contains("Username or password was incorrect"));
        loginInput.close();
        
    }
}
    
