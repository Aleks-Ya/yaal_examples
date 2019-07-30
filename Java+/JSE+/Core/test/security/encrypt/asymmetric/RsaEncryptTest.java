package security.encrypt.asymmetric;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Arrays;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

/**
 * Asymmetric encryption by RSA.
 */
public class RsaEncryptTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();


    @Test
    public void rsaEncryptDecrypt() throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException,
            BadPaddingException, IllegalBlockSizeException {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(1024);
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        PrivateKey privateKey = keyPair.getPrivate();
        PublicKey publicKey = keyPair.getPublic();
        System.out.println(Arrays.toString(privateKey.getEncoded()));
        System.out.println(Arrays.toString(publicKey.getEncoded()));

        String inputStr = "my data";
        byte[] inputBytes = inputStr.getBytes();
        System.out.println(Arrays.toString(inputBytes));

        Cipher cipher = Cipher.getInstance("RSA");

        cipher.init(Cipher.ENCRYPT_MODE, privateKey);
        byte[] encryptedBytes = cipher.doFinal(inputBytes);
        System.out.println(Arrays.toString(encryptedBytes));

        cipher.init(Cipher.DECRYPT_MODE, publicKey);
        byte[] decryptedBytes = cipher.doFinal(encryptedBytes);
        String originalStr = new String(decryptedBytes);

        assertThat(originalStr, equalTo(inputStr));
    }

    /**
     * Max length of RSA encrypted data is 117 bytes.
     */
    @Test
    public void rsaLongText() throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException,
            BadPaddingException, IllegalBlockSizeException {

        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(1024);
        KeyPair keyPair = keyPairGenerator.generateKeyPair();

        String longInputStr = "abcdefghijklmnopqrstuvwxyz abcdefghijklmnopqrstuvwxyz abcdefghijklmnopqrstuvwxyz " +
                "abcdefghijklmnopqrstuvwxyz abcdefghijklmnopqrstuvwxyz";

        byte[] inputBytes = longInputStr.getBytes();
        System.out.println("Data size: " + inputBytes.length);

        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, keyPair.getPrivate());

        thrown.expect(IllegalBlockSizeException.class);
        thrown.expectMessage("Data must not be longer than 117 bytes");
        cipher.doFinal(inputBytes);
    }

}
