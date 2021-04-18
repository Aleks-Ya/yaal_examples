package freemarker.template;

import freemarker.BaseFreemarkerTest;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Результат работы - строка (а не файл).
 */
public class WriteToString extends BaseFreemarkerTest {

    @Test
    public void test() throws IOException, TemplateException {
        Map<String, Object> data = new HashMap<>();
        data.put("name", "Aleksey");

        Template template = cfg.getTemplate("template/write_to_string.ftl");

        StringWriter out = new StringWriter();
        template.process(data, out);

        assertEquals("My name is Aleksey.", out.toString());
    }
}