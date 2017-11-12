package freemarker.include;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

public class Include {
    private static Configuration cfg;

    @BeforeClass
    public static void setUp() {
        cfg = new Configuration(Configuration.VERSION_2_3_22);
        cfg.setClassForTemplateLoading(Include.class, "/templates/include");
        cfg.setDefaultEncoding("UTF-8");
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
    }

    @Test
    public void include() throws TemplateException, IOException {
        Template template = cfg.getTemplate("outer.ftl");
        Writer out = new StringWriter();
        template.process(null, out);
        assertThat(out.toString(), equalTo("outer-inner"));
    }

}
