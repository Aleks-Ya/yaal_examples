package util.base64;

import org.junit.Test;

import java.util.Base64;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;


public class Base64Test {

    @Test
    public void encode() {
        String expText = "{098ef7bc-a96c-43a9-927a-912fc7471ba2}";
        byte[] expBytes = expText.getBytes();
        System.out.println("expBytes length=" + expBytes.length);

        Base64.Encoder encoder = Base64.getEncoder();
        byte[] encoded = encoder.encode(expBytes);
        System.out.println("Encoded length: " + encoded.length);
        System.out.println("Encoded: " + new String(encoded));

        Base64.Decoder decoder = Base64.getDecoder();
        byte[] decodedBytes = decoder.decode(encoded);
        String actText = new String(decodedBytes);
        System.out.println("decodedBytes length: " + decodedBytes.length);
        System.out.println("Decoded: " + actText);

        assertThat(actText, equalTo(expText));
    }

    @Test
    public void encodeToString() {
        String expText = "{098ef7bc-a96c-43a9-927a-912fc7471ba2}";
        byte[] expBytes = expText.getBytes();
        System.out.println("expBytes length=" + expBytes.length);

        Base64.Encoder encoder = Base64.getEncoder();
        String encodedStr = encoder.encodeToString(expBytes);
        System.out.println("encodedStr: " + encodedStr);

        Base64.Decoder decoder = Base64.getDecoder();
        byte[] decodedBytes = decoder.decode(encodedStr);
        String actText = new String(decodedBytes);
        System.out.println("decodedBytes length: " + decodedBytes.length);
        System.out.println("Decoded: " + actText);

        assertThat(actText, equalTo(expText));
    }


    @Test
    public void getMimeEncoder() {
        String expText = "{098ef7bc-a96c-43a9-927a-912fc7471ba2}";
        byte[] expBytes = expText.getBytes();
        System.out.println("expBytes length=" + expBytes.length);

        Base64.Encoder encoder = Base64.getMimeEncoder();
        String encodedStr = encoder.encodeToString(expBytes);
        System.out.println("encodedStr: " + encodedStr);

        Base64.Decoder decoder = Base64.getDecoder();
        byte[] decodedBytes = decoder.decode(encodedStr);
        String actText = new String(decodedBytes);
        System.out.println("decodedBytes length: " + decodedBytes.length);
        System.out.println("Decoded: " + actText);

        assertThat(actText, equalTo(expText));
    }
}
