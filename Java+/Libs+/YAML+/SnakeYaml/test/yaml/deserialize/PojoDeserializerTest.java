package yaml.deserialize;

import org.junit.Test;
import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;

public class PojoDeserializerTest {

    @Test
    public void deserialize() {
        Yaml yaml = new Yaml();
        InputStream is = PojoDeserializerTest.class.getResourceAsStream("data.yml");
        Data obj = yaml.loadAs(is, Data.class);
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
