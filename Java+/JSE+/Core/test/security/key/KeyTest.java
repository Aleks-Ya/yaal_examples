package security.key;

import org.junit.Test;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;

import static org.junit.Assert.assertNotNull;

/**
 * Working with a Keys.
 */
public class KeyTest {

    @Test
    public void generateKeyPair() throws NoSuchAlgorithmException {
        KeyPairGenerator keyGenerator = KeyPairGenerator.getInstance("RSA");
        keyGenerator.initialize(1024);
        KeyPair keyPair = keyGenerator.generateKeyPair();
        PublicKey publicKey = keyPair.getPublic();
        PrivateKey privateKey = keyPair.getPrivate();
        assertNotNull(publicKey);
        assertNotNull(privateKey);
    }
}
