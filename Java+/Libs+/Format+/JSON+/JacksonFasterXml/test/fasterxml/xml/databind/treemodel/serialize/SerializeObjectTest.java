package fasterxml.xml.databind.treemodel.serialize;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;

class SerializeObjectTest {
    @Test
    void recordClass() throws IOException {
        var person = new Person("John", 30);
        var mapper = new ObjectMapper();
        var node = mapper.valueToTree(person);
        var actJson = mapper.writeValueAsString(node);
        assertThatJson(actJson).isEqualTo("""
                {"name":"John","age":30}""");
    }

    record Person(String name, Integer age) {
    }

    @Test
    void objectNode() throws IOException {
        var mapper = new ObjectMapper();
        var rootNode = mapper.createObjectNode();
        rootNode.put("name", "John");
        rootNode.put("age", 30);
        var graduation = rootNode.putObject("graduation");
        graduation.put("institution", "Harvard");
        graduation.put("year", 2010);
        var actJson = mapper.writeValueAsString(rootNode);
        assertThatJson(actJson).isEqualTo("""
                {"name":"John","age":30,"graduation":{"institution":"Harvard", "year": 2010}}""");
    }
}
