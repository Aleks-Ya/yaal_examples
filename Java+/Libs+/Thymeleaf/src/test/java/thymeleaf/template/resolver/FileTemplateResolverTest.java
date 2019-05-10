package thymeleaf.template.resolver;

import org.junit.jupiter.api.Test;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templateresolver.FileTemplateResolver;
import org.thymeleaf.templateresolver.ITemplateResolver;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FileTemplateResolverTest {
    @Test
    void test() {
        ITemplateResolver resolver = new FileTemplateResolver();

        TemplateEngine engine = new TemplateEngine();
        engine.setTemplateResolver(resolver);

        Context context = new Context();
        context.setVariable("welcome", "Hello");

        String template = StringTemplateResolverTest.class.getResource("FileTemplateResolverTest.txt").getFile();
        String result = engine.process(template, context);
        assertEquals("Say Hello, FileTemplateResolver!", result);
    }

}
