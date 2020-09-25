package treemodel.deserialize;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.Test;
import util.ResourceUtil;

import java.io.IOException;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Using Data Bind for parsing specific Nodes in Tree Model.
 */
public class TreeModelAndDataBindTest {
    private static final String JSON = ResourceUtil.resourceToString(TreeModelAndDataBindTest.class, "TreeModelAndDataBindTest.json");
    private static final ObjectMapper MAPPER = new ObjectMapper();
    private static final String EXP_HEAD_NAME = "John Smith";
    private static final String EXP_HEAD_TITLE = "Executive";
    private static final String EXP_COMPANY = "Oracle";
    private static final String COMPANY_FIELD = "company";
    private static final String HEAD_FIELD = "head";

    @Test
    public void treeToValue() throws IOException {
        ObjectNode rootNode = (ObjectNode) MAPPER.readTree(JSON);
        String company = rootNode.get(COMPANY_FIELD).textValue();
        assertThat(company, equalTo(EXP_COMPANY));

        JsonNode headNode = rootNode.get(HEAD_FIELD);
        Head head = MAPPER.treeToValue(headNode, Head.class);

        assertThat(head.name, equalTo(EXP_HEAD_NAME));
        assertThat(head.title, equalTo(EXP_HEAD_TITLE));
    }

    @Test
    public void convertValue() throws IOException {
        ObjectNode rootNode = (ObjectNode) MAPPER.readTree(JSON);
        String company = rootNode.get(COMPANY_FIELD).textValue();
        assertThat(company, equalTo(EXP_COMPANY));

        JsonNode headNode = rootNode.get(HEAD_FIELD);
        Head head = MAPPER.convertValue(headNode, Head.class);

        assertThat(head.name, equalTo(EXP_HEAD_NAME));
        assertThat(head.title, equalTo(EXP_HEAD_TITLE));
    }

    @SuppressWarnings("WeakerAccess")
    private static class Head {
        public String name;
        public String title;
    }

}
