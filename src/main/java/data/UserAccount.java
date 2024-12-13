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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserAccount that = (UserAccount) o;
        return username.equals(that.username);
    }

    @Override
    public int hashCode() {
        return username.hashCode();
    }

    @Override
    public String toString() {
        return "UserAccount{username='" + username + "'}";
    }
}