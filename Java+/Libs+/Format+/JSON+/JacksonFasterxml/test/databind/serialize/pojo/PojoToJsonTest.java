package databind.serialize.pojo;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static net.javacrumbs.jsonunit.JsonAssert.assertJsonEquals;

class PojoToJsonTest {

    @Test
    void test() throws IOException {
        var mapper = new ObjectMapper();
        var album = new Album(1L, "Platinum", 2022);
        var act = mapper.writeValueAsString(album);
        var exp = "{'id':1,'title':'Platinum','year':2022}";
        assertJsonEquals(exp, act);
    }

    private record Album(Long id, String title, Integer year) {
    }

}
