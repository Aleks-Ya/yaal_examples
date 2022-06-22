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

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Asymmetric encryption by RSA.
 */
class RsaEncryptTest {

    @Test
    void rsaEncryptDecrypt() throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException,
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

        assertThat(originalStr).isEqualTo(inputStr);
    }

    /**
     * Max length of RSA encrypted data is 117 bytes.
     */
    @Test
    void rsaLongText() throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException {

        var keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(1024);
        var keyPair = keyPairGenerator.generateKeyPair();

        var longInputStr = "abcdefghijklmnopqrstuvwxyz abcdefghijklmnopqrstuvwxyz abcdefghijklmnopqrstuvwxyz " +
                "abcdefghijklmnopqrstuvwxyz abcdefghijklmnopqrstuvwxyz";

        var inputBytes = longInputStr.getBytes();
        System.out.println("Data size: " + inputBytes.length);

        var cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, keyPair.getPrivate());

        var e = assertThatThrownBy(() -> cipher.doFinal(inputBytes))
                .isInstanceOf(IllegalBlockSizeException.class)
                .hasMessage("Data must not be longer than 117 bytes");
    }

}
