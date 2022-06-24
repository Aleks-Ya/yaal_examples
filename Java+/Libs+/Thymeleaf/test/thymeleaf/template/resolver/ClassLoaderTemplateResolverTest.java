package thymeleaf.template.resolver;

import org.junit.jupiter.api.Test;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

import static org.assertj.core.api.Assertions.assertThat;

class ClassLoaderTemplateResolverTest {
    @Test
    void test() {
        var resolver = new ClassLoaderTemplateResolver();
        resolver.setPrefix("thymeleaf/template/resolver/");

        var engine = new TemplateEngine();
        engine.setTemplateResolver(resolver);

        var context = new Context();
        context.setVariable("welcome", "Hello");

        var template = "ClassLoaderTemplateResolver_template.txt";
        var result = engine.process(template, context);
        assertThat(result).isEqualTo("Say Hello, ClassLoaderTemplateResolver!");
    }

}
