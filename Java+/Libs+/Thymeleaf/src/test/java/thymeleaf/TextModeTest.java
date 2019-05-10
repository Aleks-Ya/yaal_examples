package thymeleaf;

import org.junit.jupiter.api.Test;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.TemplateSpec;
import org.thymeleaf.context.Context;
import org.thymeleaf.messageresolver.IMessageResolver;
import org.thymeleaf.messageresolver.StandardMessageResolver;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.StringTemplateResolver;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TextModeTest {
    @Test
    void noMode() {
        StringTemplateResolver resolver = new StringTemplateResolver();

        TemplateEngine engine = new TemplateEngine();
        engine.setTemplateResolver(resolver);

        Context context = new Context();
        context.setVariable("welcome", "Hello!");

        String template = "Say [(${welcome})]";
        String result = engine.process(template, context);
        assertEquals("Say Hello!", result);
    }

    @Test
    void setModeAndTemplateSpec() {
        StringTemplateResolver resolver = new StringTemplateResolver();
        resolver.setTemplateMode(TemplateMode.TEXT);

        IMessageResolver messageResolver = new StandardMessageResolver();

        TemplateEngine engine = new TemplateEngine();
        engine.setTemplateResolver(resolver);
        engine.setMessageResolver(messageResolver);

        String template = "Hello: [(${welcome})] abc";
        Context context = new Context();
        context.setVariable("welcome", "Hello!!!");
        assertTrue(context.containsVariable("welcome"));

        TemplateSpec templateSpec = new TemplateSpec(template, TemplateMode.TEXT);

        String result = engine.process(templateSpec, context);

        System.out.println(result);

    }
}
