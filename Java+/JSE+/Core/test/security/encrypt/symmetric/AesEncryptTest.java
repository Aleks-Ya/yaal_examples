package security.encrypt.symmetric;

import org.junit.jupiter.api.Test;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

/**
 * Symmetric encryption by AES.
 * Electronic CopyBook Encryption (ECB)
 */
class AesEncryptTest {

    @Test
    void aesEncryptDecrypt() throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException,
            BadPaddingException, IllegalBlockSizeException {
        var keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(192);
        Key key = keyGenerator.generateKey();
        var keyEncoded = key.getEncoded();
        System.out.println(Arrays.toString(keyEncoded));

        var inputStr = "my data";
        var inputBytes = inputStr.getBytes();
        System.out.println(Arrays.toString(inputBytes));

        var cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");

        cipher.init(Cipher.ENCRYPT_MODE, key);
        var encryptedBytes = cipher.doFinal(inputBytes);
        System.out.println(Arrays.toString(encryptedBytes));

        cipher.init(Cipher.DECRYPT_MODE, key);
        var decryptedBytes = cipher.doFinal(encryptedBytes);
        var originalStr = new String(decryptedBytes);

        assertThat(originalStr, equalTo(inputStr));
    }

}
