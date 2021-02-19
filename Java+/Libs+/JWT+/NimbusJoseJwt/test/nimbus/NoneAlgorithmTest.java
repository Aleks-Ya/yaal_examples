package nimbus;

import com.nimbusds.jose.PlainHeader;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.PlainJWT;
import org.junit.Test;

import java.text.ParseException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.everyItem;
import static org.hamcrest.Matchers.in;
import static org.hamcrest.Matchers.is;

/**
 * Create a JWT token without signature.
 */
public class NoneAlgorithmTest {
    @Test
    public void sign() throws ParseException {
        JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
                .subject("boss")
                .claim("name", "John")
                .claim("age", 30L)
                .build();

        var header = new PlainHeader();
        var expJwt = new PlainJWT(header, claimsSet);
        var expPayload = expJwt.getPayload();

        var serialized = expJwt.serialize();

        var actJwt = PlainJWT.parse(serialized);
        var actPayload = actJwt.getPayload();

        var expPayloadMap = expPayload.toJSONObject();
        var actPayloadMap = actPayload.toJSONObject();
        assertThat(actPayloadMap.entrySet(), everyItem(is(in((expPayloadMap.entrySet())))));
    }
}
