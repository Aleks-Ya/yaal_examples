package databind.deserialize.datatype;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import util.JsonUtil;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import static org.hamcrest.Matchers.arrayContainingInAnyOrder;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Deserialize a JSON array (that has field name) to Java Array, List, Set.
 */
public class DeserializeArrayWithFieldNameTest {

    @Test
    public void deserialize() throws IOException {
        String json = JsonUtil.singleQuoteToDouble("{" +
                "'list': ['aleks', 'john'], " +
                "'set': ['spb', 'ny']," +
                "'array': ['russia', 'usa']" +
                "}");
        ObjectMapper mapper = new ObjectMapper();
        Names names = mapper.readValue(json, Names.class);
        assertThat(names.list, containsInAnyOrder("aleks", "john"));
        assertThat(names.set, containsInAnyOrder("spb", "ny"));
        assertThat(names.array, arrayContainingInAnyOrder("russia", "usa"));
    }

    @SuppressWarnings("WeakerAccess")
    private static class Names {
        public List<String> list;
        public Set<String> set;
        public String[] array;
    }

}
