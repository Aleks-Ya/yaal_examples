package freemarker;

import freemarker.ext.beans.BeansWrapper;
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
 * Use static methods in templates.
 */
public class InvokeStaticMethod {
    private static Configuration cfg;

    @BeforeClass
    public static void setUp() {
        cfg = new Configuration(Configuration.VERSION_2_3_22);
        cfg.setClassForTemplateLoading(InvokeStaticMethod.class, "/templates");
        cfg.setDefaultEncoding("UTF-8");
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
    }

    @Test
    public void test() throws IOException, TemplateException {
        Map<String, Object> data = new HashMap<>();
        data.put("statics", BeansWrapper.getDefaultInstance().getStaticModels());

        Template template = cfg.getTemplate("invoke_static_method.ftl");

        StringWriter out = new StringWriter();
        template.process(data, out);

        assertEquals("Maximum=2", out.toString());
    }
}