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

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Create and validate a JWT token (HMAC signed).
 */
class HmacTest {
    private static final String NAME_CLAIM = "name";
    private static final String AGE_CLAIM = "age";

    private static String createToken(byte[] sharedSecret, String sub, String name, Long age) throws JOSEException {
        JWSSigner signer = new MACSigner(sharedSecret);

        var claimsSet = new JWTClaimsSet.Builder()
                .subject(sub)
                .claim(NAME_CLAIM, name)
                .claim(AGE_CLAIM, age)
                .build();

        var header = new JWSHeader(JWSAlgorithm.HS256);
        var signedJWT = new SignedJWT(header, claimsSet);

        assertThat(signedJWT.getSignature()).isNull();
        signedJWT.sign(signer);
        assertThat(signedJWT.getSignature()).isNotNull();
        return signedJWT.serialize();
    }

    private static JWTClaimsSet parseToken(byte[] sharedSecret, String token) throws ParseException, JOSEException {
        var actJwt = SignedJWT.parse(token);
        JWSVerifier verifier = new MACVerifier(sharedSecret);
        assertThat(actJwt.verify(verifier)).isTrue();
        return actJwt.getJWTClaimsSet();
    }

    @Test
    void sign() throws JOSEException, ParseException {
        var expSubject = "boss";
        var expNameValue = "John";
        var expAgeValue = 30L;

        var sharedSecret = "my-secret-more-longer-than-256-bits".getBytes();
        var token = createToken(sharedSecret, expSubject, expNameValue, expAgeValue);
        assertThat(token).isEqualTo("eyJhbGciOiJIUzI1NiJ9.eyJuYW1lIjoiSm9obiIsInN1YiI6ImJvc3MiLCJhZ2UiOjMwfQ.7bHaRNV6OlggVoWsGmMgeOTr5gAgvfclK4IWMCbdECU");

        var actClaimSet = parseToken(sharedSecret, token);
        assertThat(actClaimSet.getSubject()).isEqualTo(expSubject);
        assertThat(actClaimSet.getStringClaim(NAME_CLAIM)).isEqualTo(expNameValue);
        assertThat(actClaimSet.getLongClaim(AGE_CLAIM)).isEqualTo(expAgeValue);
    }
}
