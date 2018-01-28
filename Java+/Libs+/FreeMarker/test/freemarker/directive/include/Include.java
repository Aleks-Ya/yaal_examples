package freemarker.directive.include;

import freemarker.BaseFreemarkerTest;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.junit.Test;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

public class Include extends BaseFreemarkerTest {

    @Test
    public void include() throws TemplateException, IOException {
        Template template = cfg.getTemplate("directive/include/outer.ftl");
        Writer out = new StringWriter();
        template.process(null, out);
        assertThat(out.toString(), equalTo("Man John 25"));
    }

}
