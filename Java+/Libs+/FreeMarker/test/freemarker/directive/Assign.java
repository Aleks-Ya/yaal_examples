package freemarker.directive;

import freemarker.BaseFreemarkerTest;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Use #assign directive.
 */
public class Assign extends BaseFreemarkerTest {

    @Test
    public void test() throws IOException, TemplateException {
        Map<String, Object> data = new HashMap<>();

        Template template = cfg.getTemplate("directive/assign.ftl");

        StringWriter out = new StringWriter();
        template.process(data, out);

        assertEquals("String=abc", out.toString());
    }
}