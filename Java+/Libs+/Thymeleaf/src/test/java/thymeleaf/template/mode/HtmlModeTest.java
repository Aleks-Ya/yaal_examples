package thymeleaf.template.mode;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.thymeleaf.templateresolver.ITemplateResolver;

import static org.junit.jupiter.api.Assertions.assertEquals;

class HtmlModeTest {

    @Test
    void test() {
        ITemplateResolver resolver = new ClassLoaderTemplateResolver();

        TemplateEngine engine = new TemplateEngine();
        engine.setTemplateResolver(resolver);

        Context context = new Context();
        context.setVariable("welcome", "Hello!");

        String template = "thymeleaf/template/mode/html_mode.html";
        String result = engine.process(template, context);

        String expContent = Assertions.contentOf(HtmlModeTest.class.getResource("html_mode.txt"));
        assertEquals(expContent, result);
    }

}
