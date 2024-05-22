package unitTest;

import com.bta.web.JWTManager;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * This class contains tests for the JWT token.
 */
public class JWTokenTest {

    //A string containing the username of the admin account
    public String username = "admin";


    /**
     * Tests if a generated token is deemed valid by the server.
     */
    @Test
    public void validTokenTest() {
        String jwt = JWTManager.createJWT("id", username, "login", 200000);
        assertTrue(JWTManager.isJWTValid(jwt), "Valid token was declared invalid!");
    }


    /**
     * Tests if the an expired token is deemed invalid.
     */
    @Test
    public void invalidTokenTest() {
        String jwt = JWTManager.createJWT("id", username, "login", 0);
        assertFalse(JWTManager.isJWTValid(jwt), "Invalid token was declared valid!");
    }

}
