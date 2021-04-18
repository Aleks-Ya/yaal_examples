package security.encrypt.symmetric;

import org.junit.jupiter.api.Test;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

/**
 * Symmetric encryption by AES (with IV).
 * Cipher Block Chaining (CBC)
 * IV is Initialization Vector
 */
public class AesIvEncryptTest {

    @Test
    public void aesEncryptDecrypt() throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException,
            BadPaddingException, IllegalBlockSizeException, InvalidAlgorithmParameterException {


        var keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(192);
        Key key = keyGenerator.generateKey();
        var keyEncoded = key.getEncoded();
        System.out.println(Arrays.toString(keyEncoded));

        var inputStr = "my data";
        var inputBytes = inputStr.getBytes();
        System.out.println(Arrays.toString(inputBytes));

        var cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");

        var random = SecureRandom.getInstance("SHA1PRNG");
        var randomBytes = new byte[16];
        random.nextBytes(randomBytes);
        System.out.println(Arrays.toString(randomBytes));
        var ivParameterSpec = new IvParameterSpec(randomBytes);

        cipher.init(Cipher.ENCRYPT_MODE, key, ivParameterSpec);
        var encryptedBytes = cipher.doFinal(inputBytes);
        System.out.println(Arrays.toString(encryptedBytes));

        cipher.init(Cipher.DECRYPT_MODE, key, ivParameterSpec);
        var decryptedBytes = cipher.doFinal(encryptedBytes);
        var originalStr = new String(decryptedBytes);

        assertThat(originalStr, equalTo(inputStr));
    }

}
