package security.hash;

import org.junit.Test;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

/**
 * Generate hash-code of a string.
 */
public class MessageDigestTest {

    @Test
    public void sha256() throws NoSuchAlgorithmException {
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
}
