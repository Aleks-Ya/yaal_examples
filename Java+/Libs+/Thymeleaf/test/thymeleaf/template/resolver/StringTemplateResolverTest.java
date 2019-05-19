package thymeleaf.template.resolver;

import org.junit.Test;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templateresolver.ITemplateResolver;
import org.thymeleaf.templateresolver.StringTemplateResolver;

import static org.junit.Assert.assertEquals;

public class StringTemplateResolverTest {
    @Test
    public void test() {
        ITemplateResolver resolver = new StringTemplateResolver();

        TemplateEngine engine = new TemplateEngine();
        engine.setTemplateResolver(resolver);

        Context context = new Context();
        context.setVariable("welcome", "Hello!");

        String template = "Say [(${welcome})]";
        String result = engine.process(template, context);
        assertEquals("Say Hello!", result);
    }

}