package yaml.databind;

import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import org.junit.Test;
import util.ResourceUtil;

import static net.javacrumbs.jsonunit.JsonAssert.assertJsonEquals;

public class YamlToJson {

    @Test
    public void convert() throws Exception {
        var yaml = ResourceUtil.resourceToString(YamlToJson.class, "YamlToJson.yaml");
        var yamlMapper = new YAMLMapper();
        var node = yamlMapper.readTree(yaml);

        var jsonMapper = new JsonMapper();
        var jsonAct = jsonMapper.writeValueAsString(node);
        var jsonExp = ResourceUtil.resourceToString(YamlToJson.class, "YamlToJson.json");
        assertJsonEquals(jsonExp, jsonAct);
    }
}
