package thymeleaf.template.layout.include;

import org.junit.Test;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.thymeleaf.templateresolver.ITemplateResolver;

import static org.junit.Assert.assertEquals;
import static util.ResourceUtil.resourceToString;

public class IncludeInsertReplaceTest {

    @Test
    public void test() {
        ITemplateResolver resolver = new ClassLoaderTemplateResolver();

        TemplateEngine engine = new TemplateEngine();
        engine.setTemplateResolver(resolver);

        Context context = new Context();

        String template = "thymeleaf/template/layout/include/include_insert_replace_template.html";
        String result = engine.process(template, context);

        String expContent = resourceToString(IncludeInsertReplaceTest.class, "include_insert_replace_expected.html");
        assertEquals(expContent, result);
    }

}
