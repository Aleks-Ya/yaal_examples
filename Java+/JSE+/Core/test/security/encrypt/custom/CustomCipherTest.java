package security.encrypt.custom;

import org.junit.jupiter.api.Test;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.CipherSpi;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import java.security.AlgorithmParameters;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Symmetric encryption by AES.
 * Electronic CopyBook Encryption (ECB)
 */
class CustomCipherTest {

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

        assertThat(originalStr).isEqualTo(inputStr);
    }

    private static class PlusOneCipher extends CipherSpi {

        @Override
        protected void engineSetMode(String s) {
        }

        @Override
        protected void engineSetPadding(String s) {
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
        protected void engineInit(int i, Key key, SecureRandom secureRandom) {

        }

        @Override
        protected void engineInit(int i, Key key, AlgorithmParameterSpec algorithmParameterSpec, SecureRandom secureRandom) {

        }

        @Override
        protected void engineInit(int i, Key key, AlgorithmParameters algorithmParameters, SecureRandom secureRandom) {

        }

        @Override
        protected byte[] engineUpdate(byte[] bytes, int i, int i1) {
            return new byte[0];
        }

        @Override
        protected int engineUpdate(byte[] bytes, int i, int i1, byte[] bytes1, int i2) {
            return 0;
        }

        @Override
        protected byte[] engineDoFinal(byte[] bytes, int i, int i1) {
            return new byte[0];
        }

        @Override
        protected int engineDoFinal(byte[] bytes, int i, int i1, byte[] bytes1, int i2) {
            return 0;
        }
    }

}
