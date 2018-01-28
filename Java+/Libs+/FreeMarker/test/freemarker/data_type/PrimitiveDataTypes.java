package freemarker.data_type;

import freemarker.BaseFreemarkerTest;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.junit.Test;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class PrimitiveDataTypes extends BaseFreemarkerTest {

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

        Template template = cfg.getTemplate("data_type/primitive_data_types.ftl");

        Writer out = new OutputStreamWriter(System.out);
        template.process(root, out);
    }
}