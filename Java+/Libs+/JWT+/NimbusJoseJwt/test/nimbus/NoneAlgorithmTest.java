package nimbus;

import com.nimbusds.jose.PlainHeader;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.PlainJWT;
import org.junit.jupiter.api.Test;

import java.text.ParseException;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Create a JWT token without signature.
 */
class NoneAlgorithmTest {
    @Test
    void sign() throws ParseException {
        var claimsSet = new JWTClaimsSet.Builder()
                .subject("boss")
                .claim("name", "John")
                .claim("age", 30L)
                .build();

        var header = new PlainHeader();
        var expJwt = new PlainJWT(header, claimsSet);
        var expPayload = expJwt.getPayload();

        var serialized = expJwt.serialize();
        assertThat(serialized).isEqualTo("eyJhbGciOiJub25lIn0.eyJuYW1lIjoiSm9obiIsInN1YiI6ImJvc3MiLCJhZ2UiOjMwfQ.");

        var actJwt = PlainJWT.parse(serialized);
        var actPayload = actJwt.getPayload();

        var expPayloadMap = expPayload.toJSONObject();
        var actPayloadMap = actPayload.toJSONObject();
        assertThat(actPayloadMap.entrySet()).allMatch(item -> expPayloadMap.entrySet().contains(item));
    }
}
