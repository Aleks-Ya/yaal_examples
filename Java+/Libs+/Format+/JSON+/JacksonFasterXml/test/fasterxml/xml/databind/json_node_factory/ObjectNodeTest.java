package fasterxml.xml.databind.json_node_factory;

import org.junit.jupiter.api.Test;

import static com.fasterxml.jackson.databind.node.JsonNodeFactory.instance;
import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;
import static org.assertj.core.api.Assertions.assertThat;

class ObjectNodeTest {
    @Test
    void empty() {
        var node = instance.objectNode();
        assertThatJson(node.toString()).isEqualTo("{}");
    }

    @Test
    void stringField() {
        var node = instance.objectNode().put("the_field", "the_value");
        assertThatJson(node.toString()).isEqualTo("{'the_field': 'the_value'}");
    }

    @Test
    void integerField() {
        var node = instance.objectNode().put("the_field", 3);
        assertThatJson(node.toString()).isEqualTo("{'the_field': 3}");
    }

    @Test
    void nestedObject() {
        var theNestedField = "the_nested_field";
        var theNestedValue = "the_nested_value";
        var nestedNode = instance.objectNode().put(theNestedField, theNestedValue);

        var theField = "the_field";
        var outerNode = instance.objectNode().set(theField, nestedNode);

        assertThatJson(outerNode.toString()).isEqualTo("{'the_field': {'the_nested_field': 'the_nested_value'} }");
        assertThat(outerNode.findValue(theField).findValue(theNestedField).asText()).isEqualTo(theNestedValue);
    }
}
