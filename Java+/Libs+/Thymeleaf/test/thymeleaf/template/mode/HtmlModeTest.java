package thymeleaf.template.mode;

import org.junit.jupiter.api.Test;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.thymeleaf.templateresolver.ITemplateResolver;
import util.ResourceUtil;

import static org.assertj.core.api.Assertions.assertThat;

class HtmlModeTest {

    @Test
    void test() {
        ITemplateResolver resolver = new ClassLoaderTemplateResolver();

        var engine = new TemplateEngine();
        engine.setTemplateResolver(resolver);

        var context = new Context();
        context.setVariable("welcome", "Hello!");

        var template = "thymeleaf/template/mode/html_mode_template.html";
        var result = engine.process(template, context);

        var expContent = ResourceUtil.resourceToString(HtmlModeTest.class, "html_mode_expected.html");
        assertThat(result).isEqualTo(expContent);
    }

}
