package security;

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
        byte[] encodedhash = digest.digest(s.getBytes(StandardCharsets.UTF_8));
        System.out.println(Arrays.toString(encodedhash));
        System.out.println(bytesToHex(encodedhash));
    }

    private static String bytesToHex(byte[] hash) {
        StringBuffer hexString = new StringBuffer();
        for (int i = 0; i < hash.length; i++) {
            String hex = Integer.toHexString(0xff & hash[i]);
            if(hex.length() == 1) hexString.append('0');
            hexString.append(hex);
        }
        return hexString.toString();
    }
}
