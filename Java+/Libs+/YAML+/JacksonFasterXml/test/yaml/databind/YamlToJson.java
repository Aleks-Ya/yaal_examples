package yaml.databind;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import org.junit.Test;
import util.ResourceUtil;

import static net.javacrumbs.jsonunit.JsonAssert.assertJsonEquals;

public class YamlToJson {

    @Test
    public void convert() throws Exception {
        var yaml = ResourceUtil.resourceToString(YamlToJson.class, "YamlToJson.yaml");
        var yamlMapper = new ObjectMapper(new YAMLFactory());
        var object = yamlMapper.readValue(yaml, Object.class);

        var jsonMapper = new ObjectMapper();
        var jsonAct = jsonMapper.writeValueAsString(object);
        var jsonExp = ResourceUtil.resourceToString(YamlToJson.class, "YamlToJson.json");
        assertJsonEquals(jsonExp, jsonAct);
    }
}
