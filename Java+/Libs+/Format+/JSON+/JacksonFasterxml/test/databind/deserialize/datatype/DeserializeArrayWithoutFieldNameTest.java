package databind.deserialize.datatype;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import util.JsonUtil;

import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Deserialize a JSON array (that has NOT field name) to Java Array, List, Set.
 */
class DeserializeArrayWithoutFieldNameTest {

    @Test
    void stringArrayToList() throws IOException {
        var json = JsonUtil.singleQuoteToDouble("['aleks', 'john']");
        var mapper = new ObjectMapper();
        var users = mapper.readValue(json, new TypeReference<List<String>>() {
        });
        assertThat(users).hasSize(2).contains("aleks", "john");
    }

    @Test
    void objectArrayToList() throws IOException {
        var json = JsonUtil.singleQuoteToDouble("[ {'name': 'aleks'}, { 'name': 'john'}]");
        var mapper = new ObjectMapper();
        var users = mapper.readValue(json, new TypeReference<List<User>>() {
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
