package freemarker;

import freemarker.template.Configuration;
import freemarker.template.TemplateExceptionHandler;
import org.junit.jupiter.api.BeforeAll;

import java.io.StringWriter;

import static java.nio.charset.StandardCharsets.UTF_8;

public abstract class BaseFreemarkerTest {
    protected final static Configuration cfg = new Configuration(Configuration.VERSION_2_3_34);
    protected StringWriter out = new StringWriter();

    @BeforeAll
    public static void setUp() {
        cfg.setClassForTemplateLoading(BaseFreemarkerTest.class, "/templates");
        cfg.setDefaultEncoding(UTF_8.name());
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
    }
}