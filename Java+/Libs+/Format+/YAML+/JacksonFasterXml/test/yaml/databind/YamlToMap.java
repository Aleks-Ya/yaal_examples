package yaml.databind;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import org.junit.jupiter.api.Test;
import util.ResourceUtil;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.aMapWithSize;
import static org.hamcrest.Matchers.hasEntry;

public class YamlToMap {

    @Test
    public void convert() throws Exception {
        var yaml = ResourceUtil.resourceToString(YamlToJson.class, "YamlToMap.yaml");
        var map = yamlToMap(yaml);

        assertThat(map, aMapWithSize(9));
        assertThat(map, hasEntry("person.name.second", "Smith"));
        assertThat(map, hasEntry("person.name.first", "John"));
        assertThat(map, hasEntry("person.age", 30));
        assertThat(map, hasEntry("person.hobbies.0", "Skiing"));
        assertThat(map, hasEntry("person.hobbies.1", "Wakeboarding"));
        assertThat(map, hasEntry("person.passports.0.country", "RU"));
        assertThat(map, hasEntry("person.passports.0.expires", 2030));
        assertThat(map, hasEntry("person.passports.1.country", "USA"));
        assertThat(map, hasEntry("person.passports.1.expires", 2050));
    }

    private static Map<String, Object> yamlToMap(String yaml) throws IOException {
        var mapper = new YAMLMapper();
        var rootNode = mapper.readTree(yaml);
        var map = new HashMap<String, Object>();
        processElement(map, "", rootNode);
        return Map.copyOf(map);
    }

    private static void processElement(Map<String, Object> map, String path, JsonNode node) throws IOException {
        if (node.isValueNode()) {
            Object value;
            switch (node.getNodeType()) {
                case BINARY:
                    value = node.binaryValue();
                    break;
                case BOOLEAN:
                    value = node.booleanValue();
                    break;
                case MISSING:
                case NULL:
                    value = null;
                    break;
                case NUMBER:
                    value = node.numberValue();
                    break;
                case STRING:
                    value = node.textValue();
                    break;
                default:
                    throw new RuntimeException("Unknown node type: " + node.getNodeType());
            }
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
}