package thymeleaf.expression.link;

import org.junit.jupiter.api.Test;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.thymeleaf.templateresolver.ITemplateResolver;
import util.ResourceUtil;

import static org.assertj.core.api.Assertions.assertThat;

class LinkTest {
    @Test
    void test() {
        ITemplateResolver resolver = new ClassLoaderTemplateResolver();

        var engine = new TemplateEngine();
        engine.setTemplateResolver(resolver);

        var context = new Context();
        context.setVariable("city", "Moscow");

        var template = "thymeleaf/expression/link/link_template.html";
        var result = engine.process(template, context);

        var expContent = ResourceUtil.resourceToString(LinkTest.class, "link_expected.html");
        assertThat(result).isEqualTo(expContent);
    }
}
