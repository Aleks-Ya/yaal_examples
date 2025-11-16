package freemarker.data_type;

import freemarker.BaseFreemarkerTest;
import freemarker.template.TemplateException;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

class PrimitiveDataTypesTest extends BaseFreemarkerTest {
    @Test
    void allPrimitiveDataTypes() throws IOException, TemplateException {
        var data = Map.of("string_in_map", "String in Map");

        Map<String, Object> root = new HashMap<>();
        root.put("string", "MyString");
        root.put("integer", 123);
        root.put("decimal", 789.1);
        root.put("boolean", true);
        root.put("date", new Date());
        root.put("list", Arrays.asList("A", "B", "C"));
        root.put("array", new int[]{1, 2, 3});
        root.put("map", data);

        var template = cfg.getTemplate("data_type/primitive_data_types.ftl");
        template.process(root, out);
        System.out.println(out.toString());
    }
}