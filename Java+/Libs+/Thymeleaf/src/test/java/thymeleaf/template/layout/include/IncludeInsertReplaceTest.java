package thymeleaf.template.layout.include;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.thymeleaf.templateresolver.ITemplateResolver;

import static org.junit.jupiter.api.Assertions.assertEquals;

class IncludeInsertReplaceTest {

    @Test
    void test() {
        ITemplateResolver resolver = new ClassLoaderTemplateResolver();

        TemplateEngine engine = new TemplateEngine();
        engine.setTemplateResolver(resolver);

        Context context = new Context();

        String template = "thymeleaf/template/layout/include/include_insert_replace.html";
        String result = engine.process(template, context);

        String expContent = Assertions.contentOf(IncludeInsertReplaceTest.class.getResource("include_insert_replace.txt"));
        assertEquals(expContent, result);
    }

}
