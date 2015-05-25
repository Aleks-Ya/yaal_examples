import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class PrimitiveDataTypes {
    private static Configuration cfg;

    @BeforeClass
    public static void setUp() throws Exception {
        cfg = new Configuration(Configuration.VERSION_2_3_22);
        cfg.setClassForTemplateLoading(PrimitiveDataTypes.class, "templates");
        cfg.setDefaultEncoding("UTF-8");
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
    }

    @Test
    public void documentationExample() throws IOException, TemplateException {
        Map<String, Object> root = new HashMap<>();
        root.put("user", "Big Joe");
        Map<String, Object> latest = new HashMap<>();
        root.put("latestProduct", latest);
        latest.put("url", "products/greenmouse.html");
        latest.put("name", "green mouse");

        Template template = cfg.getTemplate("test.ftl");

        Writer out = new OutputStreamWriter(System.out);
        template.process(root, out);
    }

    @Test
    public void allPrimitiveDataTypes() throws IOException, TemplateException {
        Map<String, Object> map = new HashMap<>();
        map.put("string_in_map", "String in Map");


        Map<String, Object> root = new HashMap<>();
        root.put("string", "MyString");
        root.put("integer", 123);
        root.put("decimal", 789.1);
        root.put("boolean", true);
        root.put("date", new Date());
        root.put("list", Arrays.asList("A", "B", "C"));
        root.put("array", new int[]{1, 2, 3});
        root.put("map", map);

        Template template = cfg.getTemplate("all_primitive_data_types.ftl");

        Writer out = new OutputStreamWriter(System.out);
        template.process(root, out);
    }
}