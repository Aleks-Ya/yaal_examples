package nimbus.docs;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSObject;
import com.nimbusds.jose.Payload;
import com.nimbusds.jose.crypto.MACSigner;
import org.junit.jupiter.api.Test;

import java.security.SecureRandom;

/**
 * Source: https://connect2id.com/products/nimbus-jose-jwt#example
 */
public class HelloWorldTest {
    @Test
    void hello() throws JOSEException {
        // Create an HMAC-protected JWS object with some payload
        var jwsObject = new JWSObject(new JWSHeader(JWSAlgorithm.HS256), new Payload("Hello, world!"));

        // We need a 256-bit key for HS256 which must be pre-shared
        var sharedKey = new byte[32];
        new SecureRandom().nextBytes(sharedKey);

        // Apply the HMAC to the JWS object
        jwsObject.sign(new MACSigner(sharedKey));

        // Output in URL-safe format
        System.out.println(jwsObject.serialize());
    }
}
