package freemarker;

import freemarker.template.Configuration;
import freemarker.template.TemplateExceptionHandler;
import freemarker.template.Version;
import org.junit.jupiter.api.BeforeAll;

import java.io.StringWriter;

import static java.nio.charset.StandardCharsets.UTF_8;

public abstract class BaseTest {
    protected final static Version version = Configuration.VERSION_2_3_34;
    protected final static Configuration cfg = new Configuration(version);
    protected StringWriter out = new StringWriter();
}