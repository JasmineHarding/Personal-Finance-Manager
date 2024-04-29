import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SQLiteDB {
    private static final String DB_URL = "jdbc:sqlite:pfm_database.db"; // Database URL

    public static Connection connect() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(DB_URL);
        } catch (SQLException e) {
            System.out.println("Connection failed: " + e.getMessage());
        }
        return conn;
    }

    public static void createTables() {
        try (Connection conn = connect();
             Statement stmt = conn.createStatement()) {
            // SQL statement for creating a new Users Table
            String sqlUsers = "CREATE TABLE IF NOT EXISTS Users (" +
                              "username TEXT PRIMARY KEY," +
                              "password TEXT NOT NULL," +
                              "email TEXT NOT NULL);";
            stmt.execute(sqlUsers);

            // SQL statement for creating a new Accounts Table
            String sqlAccounts = "CREATE TABLE IF NOT EXISTS Accounts (" +
                                 "accountId TEXT PRIMARY KEY," +
                                 "balance REAL NOT NULL);";
            stmt.execute(sqlAccounts);

            // SQL statement for creating a new UserAccounts Table to manage user-account relationships
            String sqlUserAccounts = "CREATE TABLE IF NOT EXISTS UserAccounts (" +
                                     "username TEXT NOT NULL," +
                                     "accountId TEXT NOT NULL," +
                                     "FOREIGN KEY (username) REFERENCES Users(username)," +
                                     "FOREIGN KEY (accountId) REFERENCES Accounts(accountId)," +
                                     "PRIMARY KEY (username, accountId));";
            stmt.execute(sqlUserAccounts);
        } catch (SQLException e) {
            System.out.println("Error creating tables: " + e.getMessage());
        }
    }

    public static User getUser(String username) {
        String sql = "SELECT * FROM Users WHERE username = ?";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                String user = rs.getString("username");
                String pass = rs.getString("password");
                return new User(user, pass);
            }
        } catch (SQLException e) {
            System.out.println("Error getting user: " + e.getMessage());
        }
        return null;
    }

    public static void addUser(String username, String password, String email) {
        String sql = "INSERT INTO Users (username, password, email) VALUES (?, ?, ?)";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            pstmt.setString(2, password); // Consider encrypting this in production
            pstmt.setString(3, email);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error adding user: " + e.getMessage());
        }
    }

    public static void addAccountToUser(String username, Account account) {
        String sql = "INSERT INTO Accounts (accountId, balance) VALUES (?, ?)";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, account.getAccountID());
            pstmt.setDouble(2, account.getBalance());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error adding account: " + e.getMessage());
        }

        sql = "INSERT INTO UserAccounts (username, accountId) VALUES (?, ?)";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            pstmt.setString(2, account.getAccountID());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error adding user-account association: " + e.getMessage());
        }
    }
}