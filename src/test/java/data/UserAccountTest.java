package data;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class UserAccountTest {

    @Test
    void testValidUserAccount() {
        UserAccount user = new UserAccount("validUser_123");
        assertEquals("validUser_123", user.getUsername());
    }

    @Test
    void testInvalidUserAccount_NullUsername() {
        assertThrows(IllegalArgumentException.class, () -> new UserAccount(null),
                "Expected exception for null username");
    }

    @Test
    void testInvalidUserAccount_EmptyUsername() {
        assertThrows(IllegalArgumentException.class, () -> new UserAccount(""),
                "Expected exception for empty username");
    }

    @Test
    void testInvalidUserAccount_InvalidFormat() {
        assertThrows(IllegalArgumentException.class, () -> new UserAccount("us"),
                "Expected exception for username shorter than 3 characters");

        assertThrows(IllegalArgumentException.class, () -> new UserAccount("user_with_invalid_format@"),
                "Expected exception for username with invalid characters");
    }

    @Test
    void testEqualsAndHashCode() {
        UserAccount user1 = new UserAccount("testUser");
        UserAccount user2 = new UserAccount("testUser");
        UserAccount user3 = new UserAccount("differentUser");

        assertEquals(user1, user2, "Expected user1 and user2 to be equal");
        assertNotEquals(user1, user3, "Expected user1 and user3 to be different");
        assertEquals(user1.hashCode(), user2.hashCode(), "Expected equal hashCodes for user1 and user2");
        assertNotEquals(user1.hashCode(), user3.hashCode(), "Expected different hashCodes for user1 and user3");
    }

    @Test
    void testToString() {
        UserAccount user = new UserAccount("userTest");
        String toStringOutput = user.toString();
        assertTrue(toStringOutput.contains("userTest"), "Expected toString to contain the username");
        assertTrue(toStringOutput.contains("UserAccount"), "Expected toString to contain class name");
    }
}
