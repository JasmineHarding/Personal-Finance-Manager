import java.util.Scanner;
import java.util.UUID; // Import UUID class

public class App {
    private static Scanner scanner = new Scanner(System.in);
    private static User currentUser;

    public static void main(String[] args) {
        SQLiteDB.createTables(); // Initialize the database and tables
        boolean running = true;

        while (running) {
            System.out.println("\nWelcome to Personal Finance Manager");
            System.out.println("1. Login");
            System.out.println("2. Register");
            System.out.println("3. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline left-over

            switch (choice) {
                case 1:
                    login();
                    break;
                case 2:
                    register();
                    break;
                case 3:
                    System.out.println("Exiting...");
                    running = false;
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
        scanner.close(); // Close the scanner when the program ends
    }

    private static void login() {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        User user = SQLiteDB.getUser(username);
        if (user != null && user.authenticate(password)) {
            currentUser = user;
            System.out.println("Login successful for user: " + user.getUsername() + "!");
            userMenu();
        } else {
            System.out.println("Login failed. Check your username and password.");
        }
    }

    private static void register() {
        System.out.print("Enter a username: ");
        String username = scanner.nextLine();
        System.out.print("Enter a password: ");
        String password = scanner.nextLine();
        System.out.print("Enter your email: ");
        String email = scanner.nextLine();

        SQLiteDB.addUser(username, password, email);
        System.out.println("Registration successful. Please log in.");
    }

    private static void userMenu() {
        boolean loggedIn = true;
        while (loggedIn) {
            System.out.println("\nUser Menu:");
            System.out.println("1. Deposit");
            System.out.println("2. Withdraw");
            System.out.println("3. Check Balance");
            System.out.println("4. Create Account");
            System.out.println("5. Logout");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    handleDeposit();
                    break;
                case 2:
                    handleWithdraw();
                    break;
                case 3:
                    handleCheckBalance();
                    break;
                case 4:
                    createAccount();
                    break;
                case 5:
                    System.out.println("Logging out...");
                    loggedIn = false;
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private static void createAccount() {
        System.out.println("Creating a new account.");
        System.out.print("Enter an initial balance for the account: ");
        double balance = scanner.nextDouble();
        scanner.nextLine(); // Consume newline

        // Generate a unique account ID using UUID
        String accountId = generateAccountId();

        // Create a new checking account (you may allow users to choose the type)
        Account newAccount = new CheckingAccount(accountId, balance);

        // Add the account to the user's profile
        currentUser.getUserProfile().addAccount(newAccount);

        // Save the new account to the database
        SQLiteDB.addAccountToUser(currentUser.getUsername(), newAccount);

        System.out.println("Account created successfully with ID: " + accountId);
    }

    private static void handleDeposit() {
        if (noAccountsCheck()) return;

        System.out.print("Enter deposit amount: ");
        double amount = scanner.nextDouble();
        scanner.nextLine(); // Consume newline
        Account account = currentUser.getUserProfile().getAccounts().get(0); // This assumes the user has at least one account.
        account.deposit(amount);
        System.out.println("Deposit successful. New balance: " + account.getBalance());
    }

    private static void handleWithdraw() {
        if (noAccountsCheck()) return;

        System.out.print("Enter withdrawal amount: ");
        double amount = scanner.nextDouble();
        scanner.nextLine(); // Consume newline
        try {
            Account account = currentUser.getUserProfile().getAccounts().get(0); // This assumes the user has at least one account.
            account.withdraw(amount);
            System.out.println("Withdrawal successful. New balance: " + account.getBalance());
        } catch (Exception e) {
            System.out.println("Error during withdrawal: " + e.getMessage());
        }
    }

    private static void handleCheckBalance() {
        if (noAccountsCheck()) return;

        Account account = currentUser.getUserProfile().getAccounts().get(0); // This assumes the user has at least one account.
        System.out.println("Current balance: " + account.getBalance());
    }

    private static boolean noAccountsCheck() {
        if (currentUser.getUserProfile().getAccounts().isEmpty()) {
            System.out.println("No accounts found. Would you like to create one? (yes/no)");
            String response = scanner.nextLine();
            if (response.equalsIgnoreCase("yes")) {
                createAccount();
            }
            return true;
        }
        return false;
    }

    // This method generates a unique account ID using UUID
    private static String generateAccountId() {
        return UUID.randomUUID().toString();
    }
}