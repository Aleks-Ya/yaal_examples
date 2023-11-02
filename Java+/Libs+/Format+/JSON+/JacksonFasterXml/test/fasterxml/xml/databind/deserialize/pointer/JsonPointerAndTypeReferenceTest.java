package fasterxml.xml.databind.deserialize.pointer;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import util.JsonUtil;

import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Convert part of JSON to POJO (using TypeReference for List parsing).
 */
class JsonPointerAndTypeReferenceTest {

    @Test
    void pointer() throws IOException {
        var json = JsonUtil.singleQuoteToDouble("{ 'users': [{'name': 'aleks'}, {'name': 'john'} ]}");
        var mapper = new ObjectMapper();
        var rootNode = mapper.readTree(json);
        var userNode = rootNode.at("/users");
        var userJson = userNode.toString();
        System.out.println("Text:" + userJson);
        var users = mapper.readValue(userJson, new TypeReference<List<User>>() {
        });

        assertThat(users).hasSize(2);
        assertThat(users.get(0).name).isEqualTo("aleks");
        assertThat(users.get(1).name).isEqualTo("john");
    }

    @SuppressWarnings("WeakerAccess")
    private static class User {
        public String name;
    }
}
