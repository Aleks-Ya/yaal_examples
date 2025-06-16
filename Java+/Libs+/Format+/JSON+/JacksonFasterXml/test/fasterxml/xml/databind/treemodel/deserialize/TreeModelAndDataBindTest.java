package fasterxml.xml.databind.treemodel.deserialize;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static util.ResourceUtil.resourceToString;

/**
 * Using Data Bind for parsing specific Nodes in Tree Model.
 */
class TreeModelAndDataBindTest {
    private static final String JSON = resourceToString(TreeModelAndDataBindTest.class, "TreeModelAndDataBindTest.json");
    private static final ObjectMapper MAPPER = new ObjectMapper();
    private static final String EXP_HEAD_NAME = "John Smith";
    private static final String EXP_HEAD_TITLE = "Executive";
    private static final String EXP_COMPANY = "Oracle";
    private static final String COMPANY_FIELD = "company";
    private static final String HEAD_FIELD = "head";

    @Test
    void treeToValue() throws IOException {
        var rootNode = (ObjectNode) MAPPER.readTree(JSON);
        var company = rootNode.get(COMPANY_FIELD).textValue();
        assertThat(company).isEqualTo(EXP_COMPANY);

        var headNode = rootNode.get(HEAD_FIELD);
        var head = MAPPER.treeToValue(headNode, Head.class);

        assertThat(head.name).isEqualTo(EXP_HEAD_NAME);
        assertThat(head.title).isEqualTo(EXP_HEAD_TITLE);
    }

    @Test
    void convertValue() throws IOException {
        var rootNode = (ObjectNode) MAPPER.readTree(JSON);
        var company = rootNode.get(COMPANY_FIELD).textValue();
        assertThat(company).isEqualTo(EXP_COMPANY);

        var headNode = rootNode.get(HEAD_FIELD);
        var head = MAPPER.convertValue(headNode, Head.class);

        assertThat(head.name).isEqualTo(EXP_HEAD_NAME);
        assertThat(head.title).isEqualTo(EXP_HEAD_TITLE);
    }

    @SuppressWarnings("WeakerAccess")
    private static class Head {
        public String name;
        public String title;
    }

}
