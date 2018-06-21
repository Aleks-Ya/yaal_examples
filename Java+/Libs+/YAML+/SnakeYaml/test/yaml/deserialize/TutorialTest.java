package yaml.deserialize;

import org.junit.Test;
import org.yaml.snakeyaml.Yaml;

public class TutorialTest {

    @Test
    public void name() {
        Yaml yaml = new Yaml();
        Object obj = yaml.load("a: 1\nb: 2\nc:\n  - aaa\n  - bbb");
        System.out.println(obj);
    }
}
