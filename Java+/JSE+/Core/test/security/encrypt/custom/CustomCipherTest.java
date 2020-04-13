package security.encrypt.custom;

import org.junit.Test;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.CipherSpi;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.ShortBufferException;
import java.security.AlgorithmParameters;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Arrays;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

/**
 * Symmetric encryption by AES.
 * Electronic CopyBook Encryption (ECB)
 */
public class CustomCipherTest {

    @Test
    public void aesEncryptDecrypt() throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException,
            BadPaddingException, IllegalBlockSizeException {
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(192);
        Key key = keyGenerator.generateKey();
        byte[] keyEncoded = key.getEncoded();
        System.out.println(Arrays.toString(keyEncoded));

        String inputStr = "my data";
        byte[] inputBytes = inputStr.getBytes();
        System.out.println(Arrays.toString(inputBytes));

        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");

        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] encryptedBytes = cipher.doFinal(inputBytes);
        System.out.println(Arrays.toString(encryptedBytes));

        cipher.init(Cipher.DECRYPT_MODE, key);
        byte[] decryptedBytes = cipher.doFinal(encryptedBytes);
        String originalStr = new String(decryptedBytes);

        assertThat(originalStr, equalTo(inputStr));
    }
    
    private static class PlusOneCipher extends CipherSpi {

        @Override
        protected void engineSetMode(String s) throws NoSuchAlgorithmException {
            
        }

        @Override
        protected void engineSetPadding(String s) throws NoSuchPaddingException {

        }

        @Override
        protected int engineGetBlockSize() {
            return 0;
        }

        @Override
        protected int engineGetOutputSize(int i) {
            return 0;
        }

        @Override
        protected byte[] engineGetIV() {
            return new byte[0];
        }

        @Override
        protected AlgorithmParameters engineGetParameters() {
            return null;
        }

        @Override
        protected void engineInit(int i, Key key, SecureRandom secureRandom) throws InvalidKeyException {

        }

        @Override
        protected void engineInit(int i, Key key, AlgorithmParameterSpec algorithmParameterSpec, SecureRandom secureRandom) throws InvalidKeyException, InvalidAlgorithmParameterException {

        }

        @Override
        protected void engineInit(int i, Key key, AlgorithmParameters algorithmParameters, SecureRandom secureRandom) throws InvalidKeyException, InvalidAlgorithmParameterException {

        }

        @Override
        protected byte[] engineUpdate(byte[] bytes, int i, int i1) {
            return new byte[0];
        }

        @Override
        protected int engineUpdate(byte[] bytes, int i, int i1, byte[] bytes1, int i2) throws ShortBufferException {
            return 0;
        }

        @Override
        protected byte[] engineDoFinal(byte[] bytes, int i, int i1) throws IllegalBlockSizeException, BadPaddingException {
            return new byte[0];
        }

        @Override
        protected int engineDoFinal(byte[] bytes, int i, int i1, byte[] bytes1, int i2) throws ShortBufferException, IllegalBlockSizeException, BadPaddingException {
            return 0;
        }
    }

}
