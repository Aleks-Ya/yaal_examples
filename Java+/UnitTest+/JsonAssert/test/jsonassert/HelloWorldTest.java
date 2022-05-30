package jsonassert;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;

import static org.junit.jupiter.api.Assertions.assertThrows;

class HelloWorldTest {

    @Test
    void positive() throws JSONException {
        var expected = "{friends:[" +
                "{id:123,name:'Corby Page'}," +
                "{id:456,name:'Carter Page'}" +
                "]}";
        var actual = "{friends:[{id:123,name:\"Corby Page\"},{id:456,name:\"Carter Page\"}]}";
        JSONAssert.assertEquals(expected, actual, false);
    }

    @Test
    void object() throws JSONException {
        var actual = new JSONObject();
        actual.put("id", Double.valueOf(12345));
        JSONAssert.assertEquals("Message", "{id:12345}", actual, false);
    }

    @Test
    void negative() {
        assertThrows(AssertionError.class, () -> {
            var expected = "{id:1,name:'Joe',friends:[{id:2,name:'Pat',pets:['dog']},{id:3,name:'Sue',pets:['bird','fish']}],pets:[]}";
            var actual = "{id:1,name:\"Joe\",friends:[{id:2,name:\"Pat\",pets:[\"dog\"]},{id:3,name:\"Sue\",pets:[\"cat\",\"fish\"]}],pets:[]}";
            JSONAssert.assertEquals(expected, actual, false);
        });
    }

    @Test
    void apostrophes() throws JSONException {
        var expected = "{id:123,name: 'Corby Page'}";
        var actual = "{id:123,name: \"Corby Page\"}";
        JSONAssert.assertEquals(expected, actual, false);
    }
}
