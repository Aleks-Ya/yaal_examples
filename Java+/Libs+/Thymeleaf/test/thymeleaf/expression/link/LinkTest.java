package thymeleaf.expression.link;

import org.junit.Test;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.thymeleaf.templateresolver.ITemplateResolver;
import util.ResourceUtil;

import static org.junit.Assert.assertEquals;

public class LinkTest {
    @Test
    public void test() {
        ITemplateResolver resolver = new ClassLoaderTemplateResolver();

        TemplateEngine engine = new TemplateEngine();
        engine.setTemplateResolver(resolver);

        Context context = new Context();
        context.setVariable("city", "Moscow");

        String template = "thymeleaf/expression/link/link_template.html";
        String result = engine.process(template, context);

        String expContent = ResourceUtil.resourceToString(LinkTest.class, "link_expected.html");
        assertEquals(expContent, result);
    }
}
