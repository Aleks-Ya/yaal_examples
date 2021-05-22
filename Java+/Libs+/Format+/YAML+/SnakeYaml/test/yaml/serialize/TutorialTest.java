package yaml.serialize;

import org.junit.jupiter.api.Test;
import org.yaml.snakeyaml.Yaml;

import java.util.HashMap;

public class TutorialTest {

    @Test
    public void name() {
        var map = new HashMap<String, String>();
        map.put("name", "Pushkin");
        var yaml = new Yaml();
        var output = yaml.dump(map);
        System.out.println(output);

    }
}
