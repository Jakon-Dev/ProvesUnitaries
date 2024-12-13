package data;

final public class UserAccount {
    private final String username;

    public UserAccount(String username) {
        if (username == null || username.isEmpty() || !username.matches("[a-zA-Z0-9._-]{3,20}")) {
            throw new IllegalArgumentException("Invalid UserAccount format");
        }
        this.username = username;
    }

    public String getUsername() { return username; }
    
    // UNFINISHED
}