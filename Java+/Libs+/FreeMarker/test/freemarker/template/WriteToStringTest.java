package freemarker.template;

import freemarker.BaseClassTemplateLoaderTest;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class WriteToStringTest extends BaseClassTemplateLoaderTest {
    @Test
    void test() throws IOException, TemplateException {
        var data = Map.of("name", "Aleksey");
        var template = cfg.getTemplate("template/write_to_string.ftl");
        template.process(data, out);
        assertThat(out).hasToString("My name is Aleksey.");
    }
}