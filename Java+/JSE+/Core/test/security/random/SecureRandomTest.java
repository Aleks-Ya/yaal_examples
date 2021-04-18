package security.random;

import org.junit.jupiter.api.Test;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class SecureRandomTest {

    /**
     * Generate random numbers by SecureRandom.
     */
    @Test
    public void test() throws NoSuchAlgorithmException {
        var random = SecureRandom.getInstance("SHA1PRNG");
        System.out.println(random.nextInt());
    }
}
