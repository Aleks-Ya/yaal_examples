package thymeleaf.expression.variable;

import org.junit.jupiter.api.Test;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.thymeleaf.templateresolver.ITemplateResolver;
import util.ResourceUtil;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

class VariableTest {
    @Test
    void test() {
        ITemplateResolver resolver = new ClassLoaderTemplateResolver();

        var engine = new TemplateEngine();
        engine.setTemplateResolver(resolver);

        var list = Arrays.asList("aaa", "bbb");

        var context = new Context();
        context.setVariable("list", list);

        var template = "thymeleaf/expression/variable/variable_template.html";
        var result = engine.process(template, context);

        var expContent = ResourceUtil.resourceToString(VariableTest.class, "variable_expected.html");
        assertThat(result).isEqualTo(expContent);
    }
}
