package databind.json_node_factory;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.json.JSONException;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;

import static com.fasterxml.jackson.databind.node.JsonNodeFactory.instance;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

public class ObjectNodeTest {
    @Test
    public void empty() throws JSONException {
        ObjectNode node = instance.objectNode();
        String exp = "{}";
        JSONAssert.assertEquals(node.toString(), exp, JSONCompareMode.STRICT);
    }

    @Test
    public void stringField() throws JSONException {
        ObjectNode node = instance.objectNode().put("the_field", "the_value");
        String exp = "{'the_field': 'the_value'}";
        JSONAssert.assertEquals(node.toString(), exp, JSONCompareMode.STRICT);
    }

    @Test
    public void integerField() throws JSONException {
        ObjectNode node = instance.objectNode().put("the_field", 3);
        String exp = "{'the_field': 3}";
        JSONAssert.assertEquals(node.toString(), exp, JSONCompareMode.STRICT);
    }

    @Test
    public void nestedObject() throws JSONException {
        String theNestedField = "the_nested_field";
        String theNestedValue = "the_nested_value";
        ObjectNode nestedNode = instance.objectNode().put(theNestedField, theNestedValue);

        String theField = "the_field";
        JsonNode outerNode = instance.objectNode().set(theField, nestedNode);

        String exp = "{'the_field': {'the_nested_field': 'the_nested_value'} }";
        JSONAssert.assertEquals(outerNode.toString(), exp, JSONCompareMode.STRICT);

        assertThat(outerNode.findValue(theField).findValue(theNestedField).asText(), equalTo(theNestedValue));
    }
}
