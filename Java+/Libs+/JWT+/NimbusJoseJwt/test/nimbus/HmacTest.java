package nimbus;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import org.junit.jupiter.api.Test;

import java.text.ParseException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Create and validate a JWT token (HMAC signed).
 */
public class HmacTest {
    @Test
    public void sign() throws JOSEException, ParseException {
        var sharedSecret = "my-secret-more-longer-than-256-bits".getBytes();

        JWSSigner signer = new MACSigner(sharedSecret);

        var expSubject = "boss";
        var expNameValue = "John";
        var nameClaim = "name";
        var ageClaim = "age";
        var expAgeValue = 30L;
        var claimsSet = new JWTClaimsSet.Builder()
                .subject(expSubject)
                .claim(nameClaim, expNameValue)
                .claim(ageClaim, expAgeValue)
                .build();

        var header = new JWSHeader(JWSAlgorithm.HS256);
        var signedJWT = new SignedJWT(header, claimsSet);

        assertThat(signedJWT.getSignature(), nullValue());
        signedJWT.sign(signer);
        assertThat(signedJWT.getSignature(), notNullValue());

        var s = signedJWT.serialize();
        assertThat(s, equalTo("eyJhbGciOiJIUzI1NiJ9.eyJuYW1lIjoiSm9obiIsInN1YiI6ImJvc3MiLCJhZ2UiOjMwfQ.7bHaRNV6OlggVoWsGmMgeOTr5gAgvfclK4IWMCbdECU"));

        var actJwt = SignedJWT.parse(s);

        JWSVerifier verifier = new MACVerifier(sharedSecret);

        assertTrue(actJwt.verify(verifier));
        var actClaimSet = actJwt.getJWTClaimsSet();
        assertThat(actClaimSet.getSubject(), equalTo(expSubject));
        assertThat(actClaimSet.getClaim(nameClaim), equalTo(expNameValue));
        assertThat(actClaimSet.getClaim(ageClaim), equalTo(expAgeValue));
    }
}
