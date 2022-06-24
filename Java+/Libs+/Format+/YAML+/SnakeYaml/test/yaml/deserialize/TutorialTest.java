package yaml.deserialize;

import org.junit.jupiter.api.Test;
import org.yaml.snakeyaml.Yaml;

class TutorialTest {

    @Test
    void name() {
        var yaml = new Yaml();
        var obj = yaml.load("a: 1\nb: 2\nc:\n  - aaa\n  - bbb");
        System.out.println(obj);
    }
}
