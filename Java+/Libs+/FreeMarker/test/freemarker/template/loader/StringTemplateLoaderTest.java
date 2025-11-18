package freemarker.template.loader;

import freemarker.BaseTest;
import freemarker.cache.StringTemplateLoader;
import freemarker.template.TemplateException;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class StringTemplateLoaderTest extends BaseTest {
    private static final Map<String, String> data = Map.of("name", "John");
    private static final String TEMPLATE_NAME_1 = "template/hello.ftl";
    private static final String TEMPLATE_NAME_2 = "template/bye.ftl";
    private static final String TEMPLATE_CONTENT_1 = "Hello, ${name}";
    private static final String TEMPLATE_CONTENT_2 = "Bye, ${name}";
    private static final String OUTPUT_1 = "Hello, John";
    private static final String OUTPUT_2 = "Bye, John";

    @Test
    void oneTemplate() throws IOException, TemplateException {
        var templateLoader = new StringTemplateLoader();
        templateLoader.putTemplate(TEMPLATE_NAME_1, TEMPLATE_CONTENT_1);
        cfg.setTemplateLoader(templateLoader);

        cfg.getTemplate(TEMPLATE_NAME_1).process(data, out);
        assertThat(out).hasToString(OUTPUT_1);
    }

    @Test
    void twoTemplates() throws IOException, TemplateException {
        var templateLoader = new StringTemplateLoader();
        templateLoader.putTemplate(TEMPLATE_NAME_1, TEMPLATE_CONTENT_1);
        templateLoader.putTemplate(TEMPLATE_NAME_2, TEMPLATE_CONTENT_2);
        cfg.setTemplateLoader(templateLoader);

        var out1 = new StringWriter();
        cfg.getTemplate(TEMPLATE_NAME_1).process(data, out1);
        assertThat(out1).hasToString(OUTPUT_1);

        var out2 = new StringWriter();
        cfg.getTemplate(TEMPLATE_NAME_2).process(data, out2);
        assertThat(out2).hasToString(OUTPUT_2);
    }

    @Test
    void updateTemplate() throws IOException, TemplateException {
        var templateLoader = new StringTemplateLoader();
        cfg.setTemplateLoader(templateLoader);

        templateLoader.putTemplate(TEMPLATE_NAME_1, TEMPLATE_CONTENT_1);
        var out1 = new StringWriter();
        cfg.getTemplate(TEMPLATE_NAME_1).process(data, out1);
        assertThat(out1).hasToString(OUTPUT_1);

        templateLoader.putTemplate(TEMPLATE_NAME_1, TEMPLATE_CONTENT_2);
        cfg.clearTemplateCache();
        var out2 = new StringWriter();
        cfg.getTemplate(TEMPLATE_NAME_1).process(data, out2);
        assertThat(out2).hasToString(OUTPUT_2);
    }
}