package thymeleaf.expression.message;

import org.junit.jupiter.api.Test;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.thymeleaf.templateresolver.ITemplateResolver;
import util.ResourceUtil;

import static org.assertj.core.api.Assertions.assertThat;

class MessageTest {
    @Test
    void test() {
        ITemplateResolver resolver = new ClassLoaderTemplateResolver();

        var engine = new TemplateEngine();
        engine.setTemplateResolver(resolver);

        var context = new Context();

        var template = "thymeleaf/expression/message/message_template.html";
        var result = engine.process(template, context);

        var expContent = ResourceUtil.resourceToString(MessageTest.class, "message_expected.html");
        assertThat(result).isEqualTo(expContent);
    }
}
