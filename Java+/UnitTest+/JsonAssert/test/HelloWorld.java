import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;

public class HelloWorld {

    @Test
    public void positive() throws JSONException {
        String expected = "{friends:[" +
                "{id:123,name:\"Corby Page\"}," +
                "{id:456,name:\"Carter Page\"}" +
                "]}";
        String actual = "{friends:[{id:123,name:\"Corby Page\"},{id:456,name:\"Carter Page\"}]}";
        JSONAssert.assertEquals(expected, actual, false);
    }

    @Test
    public void object() throws JSONException {
        JSONObject actual = new JSONObject();
        actual.put("id", Double.valueOf(12345));
        JSONAssert.assertEquals("Message", "{id:12345}", actual, false);
    }

    @Test(expected = AssertionError.class)
    public void negative() throws JSONException {
        String expected = "{id:1,name:\"Joe\",friends:[{id:2,name:\"Pat\",pets:[\"dog\"]},{id:3,name:\"Sue\",pets:[\"bird\",\"fish\"]}],pets:[]}";
        String actual = "{id:1,name:\"Joe\",friends:[{id:2,name:\"Pat\",pets:[\"dog\"]},{id:3,name:\"Sue\",pets:[\"cat\",\"fish\"]}],pets:[]}";
        JSONAssert.assertEquals(expected, actual, false);
    }

    @Test
    public void apostrophes() throws JSONException {
        String expected = "{id:123,name: 'Corby Page'}";
        String actual = "{id:123,name: \"Corby Page\"}";
        JSONAssert.assertEquals(expected, actual, false);
    }
}
