package freemarker;

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
 * Результат работы - строка (а не файл).
 */
public class WriteToString {
    private static Configuration cfg;

    @BeforeClass
    public static void setUp() {
        cfg = new Configuration(Configuration.VERSION_2_3_22);
        cfg.setClassForTemplateLoading(WriteToString.class, "/templates");
        cfg.setDefaultEncoding("UTF-8");
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
    }

    @Test
    public void test() throws IOException, TemplateException {
        Map<String, Object> data = new HashMap<>();
        data.put("name", "Aleksey");

        Template template = cfg.getTemplate("write_to_string.ftl");

        StringWriter out = new StringWriter();
        template.process(data, out);

        assertEquals("My name is Aleksey.", out.toString());
    }
}