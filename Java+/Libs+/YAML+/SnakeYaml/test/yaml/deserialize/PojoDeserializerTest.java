package yaml.deserialize;

import org.junit.Test;
import org.yaml.snakeyaml.Yaml;

public class PojoDeserializerTest {

    @Test
    public void deserialize() {
        var yaml = new Yaml();
        var is = PojoDeserializerTest.class.getResourceAsStream("data.yml");
        var obj = yaml.loadAs(is, Data.class);
        System.out.println(obj.getText());
    }

    public static class Data {
        private String text;

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }
    }
}
