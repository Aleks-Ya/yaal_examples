package thymeleaf.template.layout.include;

import org.junit.jupiter.api.Test;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.thymeleaf.templateresolver.ITemplateResolver;

import static org.assertj.core.api.Assertions.assertThat;
import static util.ResourceUtil.resourceToString;

class IncludeInsertReplaceTest {

    @Test
    void test() {
        ITemplateResolver resolver = new ClassLoaderTemplateResolver();

        var engine = new TemplateEngine();
        engine.setTemplateResolver(resolver);

        var context = new Context();

        var template = "thymeleaf/template/layout/include/include_insert_replace_template.html";
        var result = engine.process(template, context);

        var expContent = resourceToString(IncludeInsertReplaceTest.class, "include_insert_replace_expected.html");
        assertThat(result).isEqualTo(expContent);
    }

}
