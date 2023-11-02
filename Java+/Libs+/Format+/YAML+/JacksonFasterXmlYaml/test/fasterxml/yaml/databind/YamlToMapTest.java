package fasterxml.yaml.databind;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import org.junit.jupiter.api.Test;
import util.ResourceUtil;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class YamlToMapTest {

    private static Map<String, Object> yamlToMap(String yaml) throws IOException {
        var mapper = new YAMLMapper();
        var rootNode = mapper.readTree(yaml);
        var map = new HashMap<String, Object>();
        processElement(map, "", rootNode);
        return Map.copyOf(map);
    }

    private static void processElement(Map<String, Object> map, String path, JsonNode node) throws IOException {
        if (node.isValueNode()) {
            var value = switch (node.getNodeType()) {
                case BINARY -> node.binaryValue();
                case BOOLEAN -> node.booleanValue();
                case MISSING, NULL -> null;
                case NUMBER -> node.numberValue();
                case STRING -> node.textValue();
                default -> throw new RuntimeException("Unknown node type: " + node.getNodeType());
            };
            map.put(path, value);
        } else if (node.isArray()) {
            var nested = node.elements();
            var i = 0;
            while (nested.hasNext()) {
                var path1 = makePath(path, String.valueOf(i));
                var el = nested.next();
                processElement(map, path1, el);
                i++;
            }
        } else {
            var names = node.fieldNames();
            while (names.hasNext()) {
                var field = names.next();
                var node1 = node.get(field);
                var path1 = makePath(path, field);
                processElement(map, path1, node1);
            }
        }
    }

    private static String makePath(String path, String field) {
        var separator = path.isBlank() ? "" : ".";
        return path + separator + field;
    }

    @Test
    void convert() throws Exception {
        var yaml = ResourceUtil.resourceToString(YamlToJsonTest.class, "YamlToMap.yaml");
        var map = yamlToMap(yaml);

        assertThat(map).hasSize(9)
                .containsEntry("person.name.second", "Smith")
                .containsEntry("person.name.first", "John")
                .containsEntry("person.age", 30)
                .containsEntry("person.hobbies.0", "Skiing")
                .containsEntry("person.hobbies.1", "Wakeboarding")
                .containsEntry("person.passports.0.country", "RU")
                .containsEntry("person.passports.0.expires", 2030)
                .containsEntry("person.passports.1.country", "USA")
                .containsEntry("person.passports.1.expires", 2050);
    }
}
