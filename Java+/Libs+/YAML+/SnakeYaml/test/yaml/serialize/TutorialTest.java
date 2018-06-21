package yaml.serialize;

import org.junit.Test;
import org.yaml.snakeyaml.Yaml;

import java.util.HashMap;
import java.util.Map;

public class TutorialTest {

    @Test
    public void name() {
        Map<String, String> map = new HashMap<>();
        map.put("name", "Pushkin");
        Yaml yaml = new Yaml();
        String output = yaml.dump(map);
        System.out.println(output);

    }
}
