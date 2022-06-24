package thymeleaf.template.resolver;

import org.junit.jupiter.api.Test;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ClassLoaderTemplateResolverTest {
    @Test
    void test() {
        ClassLoaderTemplateResolver resolver = new ClassLoaderTemplateResolver();
        resolver.setPrefix("thymeleaf/template/resolver/");

        TemplateEngine engine = new TemplateEngine();
        engine.setTemplateResolver(resolver);

        Context context = new Context();
        context.setVariable("welcome", "Hello");

        String template = "ClassLoaderTemplateResolver_template.txt";
        String result = engine.process(template, context);
        assertEquals("Say Hello, ClassLoaderTemplateResolver!", result);
    }

}
