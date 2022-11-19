package nimbus;

import com.nimbusds.jwt.JWTClaimsSet;
import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;

import java.util.List;
import java.util.Map;

import static com.nimbusds.jwt.JWTClaimNames.AUDIENCE;
import static com.nimbusds.jwt.JWTClaimNames.SUBJECT;
import static org.assertj.core.api.Assertions.assertThat;

class AssertTest {
    @Test
    void claimsSet() throws JSONException {
        var claimsSet = new JWTClaimsSet.Builder()
                .subject("boss")
                .audience("servers")
                .claim("name", "John")
                .claim("age", 30L)
                .build();
        assertThat(claimsSet.toJSONObject()).containsExactlyInAnyOrderEntriesOf(Map.of(
                SUBJECT, "boss",
                AUDIENCE, "servers",
                "name", "John",
                "age", 30L));
        assertThat(claimsSet.getClaims()).containsExactlyInAnyOrderEntriesOf(Map.of(
                SUBJECT, "boss",
                AUDIENCE, List.of("servers"),
                "name", "John",
                "age", 30L));
        assertThat(claimsSet).hasToString("{\"sub\":\"boss\",\"aud\":\"servers\",\"name\":\"John\",\"age\":30}");
        assertThat(claimsSet.getClaims()).containsEntry(SUBJECT, "boss").containsEntry(AUDIENCE, List.of("servers"))
                .containsEntry("name", "John").containsEntry("age", 30L);
        JSONAssert.assertEquals("{sub:boss,aud:servers,name:John,age:30}", claimsSet.toString(), true);
    }
}
