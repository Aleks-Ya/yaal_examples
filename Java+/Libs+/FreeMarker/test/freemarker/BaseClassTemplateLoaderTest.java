package freemarker;

import freemarker.template.TemplateExceptionHandler;
import org.junit.jupiter.api.BeforeAll;

import static java.nio.charset.StandardCharsets.UTF_8;

public abstract class BaseClassTemplateLoaderTest extends BaseTest {
    @BeforeAll
    public static void setUp() {
        cfg.setClassForTemplateLoading(BaseClassTemplateLoaderTest.class, "/templates");
        cfg.setDefaultEncoding(UTF_8.name());
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
    }
}