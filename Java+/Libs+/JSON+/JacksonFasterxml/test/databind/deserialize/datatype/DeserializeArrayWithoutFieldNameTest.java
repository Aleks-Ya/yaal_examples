package databind.deserialize.datatype;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import util.JsonUtil;

import java.io.IOException;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.hasSize;

/**
 * Deserialize a JSON array (that has NOT field name) to Java Array, List, Set.
 */
public class DeserializeArrayWithoutFieldNameTest {

    @Test
    public void stringArrayToList() throws IOException {
        var json = JsonUtil.singleQuoteToDouble("['aleks', 'john']");
        var mapper = new ObjectMapper();
        var users = mapper.readValue(json, new TypeReference<List<String>>() {
        });
        assertThat(users, hasSize(2));
        assertThat(users, hasItems("aleks", "john"));
    }

    @Test
    public void objectArrayToList() throws IOException {
        var json = JsonUtil.singleQuoteToDouble("[ {'name': 'aleks'}, { 'name': 'john'}]");
        var mapper = new ObjectMapper();
        var users = mapper.readValue(json, new TypeReference<List<User>>() {
        });
        assertThat(users, hasSize(2));
        assertThat(users.get(0).name, equalTo("aleks"));
        assertThat(users.get(1).name, equalTo("john"));
    }

    @SuppressWarnings("WeakerAccess")
    private static class User {
        public String name;
    }

}
