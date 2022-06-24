package thymeleaf.template.mode;

import org.junit.jupiter.api.Test;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templateresolver.StringTemplateResolver;

import static org.assertj.core.api.Assertions.assertThat;

class TextModeTest {
    @Test
    void test() {
        var resolver = new StringTemplateResolver();

        var engine = new TemplateEngine();
        engine.setTemplateResolver(resolver);

        var context = new Context();
        context.setVariable("welcome", "Hello!");

        var template = "Say [(${welcome})]";
        var result = engine.process(template, context);
        assertThat(result).isEqualTo("Say Hello!");
    }
}
