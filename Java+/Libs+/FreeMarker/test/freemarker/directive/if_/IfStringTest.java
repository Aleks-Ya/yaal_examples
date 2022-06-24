package freemarker.directive.if_;

import freemarker.BaseFreemarkerTest;
import freemarker.template.TemplateException;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Условные операторы в шаблонах.
 */
class IfStringTest extends BaseFreemarkerTest {

    @Test
    void test() throws IOException, TemplateException {
        Map<String, Object> data = new HashMap<>();
        data.put("name", "Aleksey");
        data.put("sex", "M");

        var template = cfg.getTemplate("directive/if_/if_string.ftl");

        var out = new StringWriter();
        template.process(data, out);

        assertThat(out).hasToString("Hello, Mr. Aleksey!");
    }
}