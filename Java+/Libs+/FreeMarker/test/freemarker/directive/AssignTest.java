package freemarker.directive;

import freemarker.BaseFreemarkerTest;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Use #assign directive.
 */
class AssignTest extends BaseFreemarkerTest {

    @Test
    void test() throws IOException, TemplateException {
        Map<String, Object> data = new HashMap<>();

        Template template = cfg.getTemplate("directive/assign.ftl");

        StringWriter out = new StringWriter();
        template.process(data, out);

        assertThat(out).hasToString("String=abc");
    }
}