package thymeleaf.expression.variable;

import org.junit.jupiter.api.Test;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.thymeleaf.templateresolver.ITemplateResolver;
import util.ResourceUtil;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class VariableTest {
    @Test
    void test() {
        ITemplateResolver resolver = new ClassLoaderTemplateResolver();

        TemplateEngine engine = new TemplateEngine();
        engine.setTemplateResolver(resolver);

        List<String> list = Arrays.asList("aaa", "bbb");

        Context context = new Context();
        context.setVariable("list", list);

        String template = "thymeleaf/expression/variable/variable_template.html";
        String result = engine.process(template, context);

        String expContent = ResourceUtil.resourceToString(VariableTest.class, "variable_expected.html");
        assertEquals(expContent, result);
    }
}
