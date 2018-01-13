package security;

import org.junit.Test;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class SecureRandomTest {

    /**
     * Generate random numbers by SecureRandom.
     */
    @Test
    public void test() throws NoSuchAlgorithmException {
        SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
        System.out.println(random.nextInt());
    }
}
