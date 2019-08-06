package security.provider;

import org.junit.Ignore;
import org.junit.Test;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Provider;
import java.util.Arrays;

/**
 * Custom JCA Provider implementation.
 */
@Ignore("not finished")
public class CustomProviderTest {

    @Test
    public void sha256() throws NoSuchAlgorithmException {
        Provider provider = null;

        MessageDigest digest = MessageDigest.getInstance("SHA-256");

        String s = "abc";
        byte[] bytes = s.getBytes(StandardCharsets.UTF_8);
        byte[] encodedHash = digest.digest(bytes);
        System.out.println(Arrays.toString(encodedHash));
        System.out.println(bytesToHex(encodedHash));
    }

    private static String bytesToHex(byte[] hash) {
        StringBuilder hexString = new StringBuilder();
        for (byte b : hash) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) hexString.append('0');
            hexString.append(hex);
        }
        return hexString.toString();
    }

    public static final class MyProvider extends Provider {
        public MyProvider() {
            super("MyProvider", 1.0,
                    "Some info about my provider and which algorithms it supports");
            // com.my.crypto.provider.MyCipher extends CipherSPI
            put("Cipher.MyCipher", "com.my.crypto.provider.MyCipher");
        }
    }
}
