package security.provider;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Provider;
import java.util.Arrays;

/**
 * Custom JCA Provider implementation.
 */
@Disabled("not finished")
class CustomProviderTest {

    @Test
    void sha256() throws NoSuchAlgorithmException {
        Provider provider = new MyProvider();

        var digest = MessageDigest.getInstance("SHA-256");

        var s = "abc";
        var bytes = s.getBytes(StandardCharsets.UTF_8);
        var encodedHash = digest.digest(bytes);
        System.out.println(Arrays.toString(encodedHash));
        System.out.println(bytesToHex(encodedHash));
    }

    private static String bytesToHex(byte[] hash) {
        var hexString = new StringBuilder();
        for (var b : hash) {
            var hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) hexString.append('0');
            hexString.append(hex);
        }
        return hexString.toString();
    }

    public static final class MyProvider extends Provider {
        public MyProvider() {
            super("MyProvider", "1.0", "Some info about my provider and which algorithms it supports");
            // com.my.crypto.provider.MyCipher extends CipherSPI
            put("Cipher.MyCipher", "com.my.crypto.provider.MyCipher");
        }
    }
}
