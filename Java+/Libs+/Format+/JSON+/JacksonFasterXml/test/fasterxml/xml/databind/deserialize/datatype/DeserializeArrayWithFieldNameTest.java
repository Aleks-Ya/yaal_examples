package fasterxml.xml.databind.deserialize.datatype;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import util.JsonUtil;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Deserialize a JSON array (that has field name) to Java Array, List, Set.
 */
class DeserializeArrayWithFieldNameTest {

    @Test
    void deserialize() throws IOException {
        var json = JsonUtil.singleQuoteToDouble("{" +
                "'list': ['aleks', 'john'], " +
                "'set': ['spb', 'ny']," +
                "'array': ['russia', 'usa']" +
                "}");
        var mapper = new ObjectMapper();
        var names = mapper.readValue(json, Names.class);
        assertThat(names.list).containsExactlyInAnyOrder("aleks", "john");
        assertThat(names.set).containsExactlyInAnyOrder("spb", "ny");
        assertThat(names.array).containsExactlyInAnyOrder("russia", "usa");
    }

    @SuppressWarnings("WeakerAccess")
    private static class Names {
        public List<String> list;
        public Set<String> set;
        public String[] array;
    }

}
