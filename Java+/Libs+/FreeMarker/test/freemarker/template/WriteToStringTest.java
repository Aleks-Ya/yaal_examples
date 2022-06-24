package freemarker.template;

import freemarker.BaseFreemarkerTest;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Результат работы - строка (а не файл).
 */
class WriteToStringTest extends BaseFreemarkerTest {

    @Test
    void test() throws IOException, TemplateException {
        Map<String, Object> data = new HashMap<>();
        data.put("name", "Aleksey");

        Template template = cfg.getTemplate("template/write_to_string.ftl");

        StringWriter out = new StringWriter();
        template.process(data, out);

        assertThat(out).hasToString("My name is Aleksey.");
    }
}