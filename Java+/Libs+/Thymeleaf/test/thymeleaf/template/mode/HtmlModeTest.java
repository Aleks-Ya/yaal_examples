package thymeleaf.template.mode;

import org.junit.Test;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.thymeleaf.templateresolver.ITemplateResolver;
import util.ResourceUtil;

import static org.junit.Assert.assertEquals;

public class HtmlModeTest {

    @Test
    public void test() {
        ITemplateResolver resolver = new ClassLoaderTemplateResolver();

        TemplateEngine engine = new TemplateEngine();
        engine.setTemplateResolver(resolver);

        Context context = new Context();
        context.setVariable("welcome", "Hello!");

        String template = "thymeleaf/template/mode/html_mode.html";
        String result = engine.process(template, context);

        String expContent = ResourceUtil.resourceToString(HtmlModeTest.class, "html_mode.txt");
        assertEquals(expContent, result);
    }

}
