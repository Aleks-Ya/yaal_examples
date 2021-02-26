package databind.json_node_factory;

import org.json.JSONException;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;

import static com.fasterxml.jackson.databind.node.JsonNodeFactory.instance;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class ObjectNodeTest {
    @Test
    public void empty() throws JSONException {
        var node = instance.objectNode();
        var exp = "{}";
        JSONAssert.assertEquals(node.toString(), exp, JSONCompareMode.STRICT);
    }

    @Test
    public void stringField() throws JSONException {
        var node = instance.objectNode().put("the_field", "the_value");
        var exp = "{'the_field': 'the_value'}";
        JSONAssert.assertEquals(node.toString(), exp, JSONCompareMode.STRICT);
    }

    @Test
    public void integerField() throws JSONException {
        var node = instance.objectNode().put("the_field", 3);
        var exp = "{'the_field': 3}";
        JSONAssert.assertEquals(node.toString(), exp, JSONCompareMode.STRICT);
    }

    @Test
    public void nestedObject() throws JSONException {
        var theNestedField = "the_nested_field";
        var theNestedValue = "the_nested_value";
        var nestedNode = instance.objectNode().put(theNestedField, theNestedValue);

        var theField = "the_field";
        var outerNode = instance.objectNode().set(theField, nestedNode);

        var exp = "{'the_field': {'the_nested_field': 'the_nested_value'} }";
        JSONAssert.assertEquals(outerNode.toString(), exp, JSONCompareMode.STRICT);

        assertThat(outerNode.findValue(theField).findValue(theNestedField).asText(), equalTo(theNestedValue));
    }
}
