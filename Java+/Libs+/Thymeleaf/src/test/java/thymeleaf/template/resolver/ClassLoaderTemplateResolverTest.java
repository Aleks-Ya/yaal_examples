package thymeleaf.template.resolver;

import org.junit.jupiter.api.Test;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.thymeleaf.templateresolver.ITemplateResolver;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ClassLoaderTemplateResolverTest {
    @Test
    void test() {
        ITemplateResolver resolver = new ClassLoaderTemplateResolver();

        TemplateEngine engine = new TemplateEngine();
        engine.setTemplateResolver(resolver);

        Context context = new Context();
        context.setVariable("welcome", "Hello");

        String template = "thymeleaf/template/resolver/ClassLoaderTemplateResolverTest.txt";
        String result = engine.process(template, context);
        assertEquals("Say Hello, ClassLoaderTemplateResolver!", result);
    }

}
