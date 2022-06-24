package nimbus;

import com.nimbusds.jose.PlainHeader;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.PlainJWT;
import org.junit.jupiter.api.Test;

import java.text.ParseException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.everyItem;
import static org.hamcrest.Matchers.in;
import static org.hamcrest.Matchers.is;

/**
 * Create a JWT token without signature.
 */
public class NoneAlgorithmTest {
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
        assertThat(serialized, equalTo("eyJhbGciOiJub25lIn0.eyJuYW1lIjoiSm9obiIsInN1YiI6ImJvc3MiLCJhZ2UiOjMwfQ."));

        var actJwt = PlainJWT.parse(serialized);
        var actPayload = actJwt.getPayload();

        var expPayloadMap = expPayload.toJSONObject();
        var actPayloadMap = actPayload.toJSONObject();
        assertThat(actPayloadMap.entrySet(), everyItem(is(in((expPayloadMap.entrySet())))));
    }
}
