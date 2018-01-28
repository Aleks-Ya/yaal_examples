package freemarker.directive;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

/**
 * Use #assign directive.
 */
public class Assign {
    private static Configuration cfg;

    @BeforeClass
    public static void setUp() {
        cfg = new Configuration(Configuration.VERSION_2_3_22);
        cfg.setClassForTemplateLoading(Assign.class, "/templates");
        cfg.setDefaultEncoding("UTF-8");
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
    }

    @Test
    public void test() throws IOException, TemplateException {
        Map<String, Object> data = new HashMap<>();

        Template template = cfg.getTemplate("directive/assign.ftl");

        StringWriter out = new StringWriter();
        template.process(data, out);

        assertEquals("String=abc", out.toString());
    }
}