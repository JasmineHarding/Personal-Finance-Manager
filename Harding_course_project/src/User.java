import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class User {
    private String username;
    private String hashedPassword;
    private UserProfile userProfile;

    // Constructor
    public User(String username, String password) {
        this.username = username;
        this.hashedPassword = hashPassword(password);
        this.userProfile = new UserProfile(); // UserProfile needs to be defined elsewhere
    }

    // Getter for username
    public String getUsername() {
        return username;
    }

    // Setter for username - if not used, consider removing this method
    public void setUsername(String username) {
        this.username = username;
    }

    // Getter for UserProfile
    public UserProfile getUserProfile() {
        return userProfile;
    }

    // Setter for UserProfile
    public void setUserProfile(UserProfile userProfile) {
        this.userProfile = userProfile;
    }

    // Hash the password with SHA-256
    private String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashedBytes = md.digest(password.getBytes(StandardCharsets.UTF_8));
            return bytesToHex(hashedBytes);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Hashing algorithm not available.", e);
        }
    }

    // Convert byte array to hex string
    private String bytesToHex(byte[] bytes) {
        StringBuilder hexString = new StringBuilder(2 * bytes.length);
        for (byte b : bytes) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }

    // Authenticate user by comparing hashed passwords
    public boolean authenticate(String enteredPassword) {
        return this.hashedPassword.equals(hashPassword(enteredPassword));
    }
}
