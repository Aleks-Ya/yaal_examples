package security.encrypt.symmetric;

import org.junit.Test;

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

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

/**
 * Symmetric encryption by AES (with IV).
 * Cipher Block Chaining (CBC)
 * IV is Initialization Vector
 */
public class AesIvEncryptTest {

    @Test
    public void aesEncryptDecrypt() throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException,
            BadPaddingException, IllegalBlockSizeException, InvalidAlgorithmParameterException {


        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(192);
        Key key = keyGenerator.generateKey();
        byte[] keyEncoded = key.getEncoded();
        System.out.println(Arrays.toString(keyEncoded));

        String inputStr = "my data";
        byte[] inputBytes = inputStr.getBytes();
        System.out.println(Arrays.toString(inputBytes));

        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");

        SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
        byte[] randomBytes = new byte[16];
        random.nextBytes(randomBytes);
        System.out.println(Arrays.toString(randomBytes));
        IvParameterSpec ivParameterSpec = new IvParameterSpec(randomBytes);

        cipher.init(Cipher.ENCRYPT_MODE, key, ivParameterSpec);
        byte[] encryptedBytes = cipher.doFinal(inputBytes);
        System.out.println(Arrays.toString(encryptedBytes));

        cipher.init(Cipher.DECRYPT_MODE, key, ivParameterSpec);
        byte[] decryptedBytes = cipher.doFinal(encryptedBytes);
        String originalStr = new String(decryptedBytes);

        assertThat(originalStr, equalTo(inputStr));
    }

}
