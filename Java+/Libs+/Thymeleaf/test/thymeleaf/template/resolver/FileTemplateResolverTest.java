package thymeleaf.template.resolver;

import org.junit.jupiter.api.Test;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templateresolver.FileTemplateResolver;
import org.thymeleaf.templateresolver.ITemplateResolver;

import static org.assertj.core.api.Assertions.assertThat;

class FileTemplateResolverTest {
    @Test
    void test() {
        ITemplateResolver resolver = new FileTemplateResolver();

        var engine = new TemplateEngine();
        engine.setTemplateResolver(resolver);

        var context = new Context();
        context.setVariable("welcome", "Hello");

        var template = StringTemplateResolverTest.class.getResource("FileTemplateResolver_template.txt").getFile();
        var result = engine.process(template, context);
        assertThat(result).isEqualTo("Say Hello, FileTemplateResolver!");
    }

}
