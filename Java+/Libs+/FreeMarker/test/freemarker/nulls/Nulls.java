package freemarker.nulls;

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
 * Handling nulls.
 */
public class Nulls {
    private static Configuration cfg;

    @BeforeClass
    public static void setUp() {
        cfg = new Configuration(Configuration.VERSION_2_3_22);
        cfg.setClassForTemplateLoading(Nulls.class, "/templates");
        cfg.setDefaultEncoding("UTF-8");
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
    }

    @Test
    public void useDefaultValueIfNull() throws IOException, TemplateException {
        Map<String, Object> data = new HashMap<>();
        data.put("name", null);

        Template template = cfg.getTemplate("nulls/Nulls_useDefaultValueIfNull.ftl");

        StringWriter out = new StringWriter();
        template.process(data, out);

        assertEquals("My name is empty value.", out.toString());
    }

    @Test
    public void ifNotNull() throws IOException, TemplateException {
        Map<String, Object> data = new HashMap<>();
        data.put("name", null);
        data.put("title", "a");

        Template template = cfg.getTemplate("nulls/Nulls_ifNotNull.ftl");

        StringWriter out = new StringWriter();
        template.process(data, out);

        assertEquals("name is null\ntitle is not null\n\nname or surname is null", out.toString());
    }
}