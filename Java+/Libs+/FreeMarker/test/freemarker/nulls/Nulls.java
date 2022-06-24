package freemarker.nulls;

import freemarker.BaseFreemarkerTest;
import freemarker.template.TemplateException;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Handling nulls.
 */
public class Nulls extends BaseFreemarkerTest {

    @Test
    void useDefaultValueIfNull() throws IOException, TemplateException {
        Map<String, Object> data = new HashMap<>();
        data.put("name", null);

        var template = cfg.getTemplate("nulls/Nulls_useDefaultValueIfNull.ftl");

        var out = new StringWriter();
        template.process(data, out);

        assertThat(out).hasToString("My name is empty value.");
    }

    @Test
    void ifNotNull() throws IOException, TemplateException {
        Map<String, Object> data = new HashMap<>();
        data.put("name", null);
        data.put("title", "a");

        var template = cfg.getTemplate("nulls/Nulls_ifNotNull.ftl");

        var out = new StringWriter();
        template.process(data, out);

        assertThat(out).hasToString("name is null\ntitle is not null\n\nname or surname is null");
    }
}