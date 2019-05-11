package thymeleaf.expression.message;

import org.junit.Test;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.thymeleaf.templateresolver.ITemplateResolver;
import util.ResourceUtil;

import static org.junit.Assert.assertEquals;

public class MessageTest {
    @Test
    public void test() {
        ITemplateResolver resolver = new ClassLoaderTemplateResolver();

        TemplateEngine engine = new TemplateEngine();
        engine.setTemplateResolver(resolver);

        Context context = new Context();

        String template = "thymeleaf/expression/message/message_template.html";
        String result = engine.process(template, context);

        String expContent = ResourceUtil.resourceToString(MessageTest.class, "message_expected.html");
        assertEquals(expContent, result);
    }
}
