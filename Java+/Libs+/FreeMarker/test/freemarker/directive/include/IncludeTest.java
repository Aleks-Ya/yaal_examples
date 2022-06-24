package freemarker.directive.include;

import freemarker.BaseFreemarkerTest;
import freemarker.template.TemplateException;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;

import static org.assertj.core.api.Assertions.assertThat;

class IncludeTest extends BaseFreemarkerTest {

    @Test
    void include() throws TemplateException, IOException {
        var template = cfg.getTemplate("directive/include/outer.ftl");
        Writer out = new StringWriter();
        template.process(null, out);
        assertThat(out.toString()).isEqualTo("Man John 25");
    }

}
