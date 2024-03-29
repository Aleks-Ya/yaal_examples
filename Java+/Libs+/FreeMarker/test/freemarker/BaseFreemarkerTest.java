package freemarker;

import freemarker.template.Configuration;
import freemarker.template.TemplateExceptionHandler;
import org.junit.jupiter.api.BeforeAll;

public abstract class BaseFreemarkerTest {
    protected final static Configuration cfg = new Configuration(Configuration.VERSION_2_3_22);

    @BeforeAll
    public static void setUp() {
        cfg.setClassForTemplateLoading(BaseFreemarkerTest.class, "/templates");
        cfg.setDefaultEncoding("UTF-8");
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
    }
}