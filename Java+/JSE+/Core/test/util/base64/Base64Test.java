package util.base64;

import org.junit.jupiter.api.Test;

import java.util.Base64;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

class Base64Test {

    @Test
    void encode() {
        var expText = "{098ef7bc-a96c-43a9-927a-912fc7471ba2}";
        var expBytes = expText.getBytes();
        System.out.println("expBytes length=" + expBytes.length);

        var encoder = Base64.getEncoder();
        var encoded = encoder.encode(expBytes);
        System.out.println("Encoded length: " + encoded.length);
        System.out.println("Encoded: " + new String(encoded));

        var decoder = Base64.getDecoder();
        var decodedBytes = decoder.decode(encoded);
        var actText = new String(decodedBytes);
        System.out.println("decodedBytes length: " + decodedBytes.length);
        System.out.println("Decoded: " + actText);

        assertThat(actText, equalTo(expText));
    }

    @Test
    void encodeToString() {
        var expText = "{098ef7bc-a96c-43a9-927a-912fc7471ba2}";
        var expBytes = expText.getBytes();
        System.out.println("expBytes length=" + expBytes.length);

        var encoder = Base64.getEncoder();
        var encodedStr = encoder.encodeToString(expBytes);
        System.out.println("encodedStr: " + encodedStr);

        var decoder = Base64.getDecoder();
        var decodedBytes = decoder.decode(encodedStr);
        var actText = new String(decodedBytes);
        System.out.println("decodedBytes length: " + decodedBytes.length);
        System.out.println("Decoded: " + actText);

        assertThat(actText, equalTo(expText));
    }


    @Test
    void getMimeEncoder() {
        var expText = "{098ef7bc-a96c-43a9-927a-912fc7471ba2}";
        var expBytes = expText.getBytes();
        System.out.println("expBytes length=" + expBytes.length);

        var encoder = Base64.getMimeEncoder();
        var encodedStr = encoder.encodeToString(expBytes);
        System.out.println("encodedStr: " + encodedStr);

        var decoder = Base64.getDecoder();
        var decodedBytes = decoder.decode(encodedStr);
        var actText = new String(decodedBytes);
        System.out.println("decodedBytes length: " + decodedBytes.length);
        System.out.println("Decoded: " + actText);

        assertThat(actText, equalTo(expText));
    }
}
