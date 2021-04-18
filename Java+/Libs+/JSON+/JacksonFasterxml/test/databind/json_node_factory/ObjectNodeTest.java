package databind.json_node_factory;

import org.junit.jupiter.api.Test;

import static com.fasterxml.jackson.databind.node.JsonNodeFactory.instance;
import static net.javacrumbs.jsonunit.JsonAssert.assertJsonEquals;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class ObjectNodeTest {
    @Test
    public void empty() {
        var node = instance.objectNode();
        var exp = "{}";
        assertJsonEquals(exp, node.toString());
    }

    @Test
    public void stringField() {
        var node = instance.objectNode().put("the_field", "the_value");
        var exp = "{'the_field': 'the_value'}";
        assertJsonEquals(exp, node.toString());
    }

    @Test
    public void integerField() {
        var node = instance.objectNode().put("the_field", 3);
        var exp = "{'the_field': 3}";
        assertJsonEquals(exp, node.toString());
    }

    @Test
    public void nestedObject() {
        var theNestedField = "the_nested_field";
        var theNestedValue = "the_nested_value";
        var nestedNode = instance.objectNode().put(theNestedField, theNestedValue);

        var theField = "the_field";
        var outerNode = instance.objectNode().set(theField, nestedNode);

        var exp = "{'the_field': {'the_nested_field': 'the_nested_value'} }";
        assertJsonEquals(exp, outerNode.toString());

        assertThat(outerNode.findValue(theField).findValue(theNestedField).asText(), equalTo(theNestedValue));
    }
}
