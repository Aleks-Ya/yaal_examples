package security.encrypt.asymmetric;

import org.junit.jupiter.api.Test;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidKeyException;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThrows;

/**
 * Asymmetric encryption by RSA.
 */
public class RsaEncryptTest {

    @Test
    public void rsaEncryptDecrypt() throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException,
            BadPaddingException, IllegalBlockSizeException {
        var keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(1024);
        var keyPair = keyPairGenerator.generateKeyPair();
        var privateKey = keyPair.getPrivate();
        var publicKey = keyPair.getPublic();
        System.out.println(Arrays.toString(privateKey.getEncoded()));
        System.out.println(Arrays.toString(publicKey.getEncoded()));

        var inputStr = "my data";
        var inputBytes = inputStr.getBytes();
        System.out.println(Arrays.toString(inputBytes));

        var cipher = Cipher.getInstance("RSA");

        cipher.init(Cipher.ENCRYPT_MODE, privateKey);
        var encryptedBytes = cipher.doFinal(inputBytes);
        System.out.println(Arrays.toString(encryptedBytes));

        cipher.init(Cipher.DECRYPT_MODE, publicKey);
        var decryptedBytes = cipher.doFinal(encryptedBytes);
        var originalStr = new String(decryptedBytes);

        assertThat(originalStr, equalTo(inputStr));
    }

    /**
     * Max length of RSA encrypted data is 117 bytes.
     */
    @Test
    public void rsaLongText() throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException {

        var keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(1024);
        var keyPair = keyPairGenerator.generateKeyPair();

        var longInputStr = "abcdefghijklmnopqrstuvwxyz abcdefghijklmnopqrstuvwxyz abcdefghijklmnopqrstuvwxyz " +
                "abcdefghijklmnopqrstuvwxyz abcdefghijklmnopqrstuvwxyz";

        var inputBytes = longInputStr.getBytes();
        System.out.println("Data size: " + inputBytes.length);

        var cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, keyPair.getPrivate());

        var e = assertThrows(IllegalBlockSizeException.class, () -> cipher.doFinal(inputBytes));
        assertThat(e.getMessage(), equalTo("Data must not be longer than 117 bytes"));
    }

}
