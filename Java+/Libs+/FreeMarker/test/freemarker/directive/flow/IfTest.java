package freemarker.directive.flow;

import freemarker.BaseClassTemplateLoaderTest;
import freemarker.template.TemplateException;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class IfTest extends BaseClassTemplateLoaderTest {

    @Test
    void ifBoolean() throws IOException, TemplateException {
        var data = Map.of(
                "large", true,
                "fat", false
        );
        var template = cfg.getTemplate("directive/flow/if_boolean.ftl");
        template.process(data, out);
        assertThat(out).hasToString("Large\nThin");
    }

    @Test
    void ifString() throws IOException, TemplateException {
        var data = Map.of(
                "name", "Aleksey",
                "sex", "M"
        );
        var template = cfg.getTemplate("directive/flow/if_string.ftl");
        template.process(data, out);
        assertThat(out).hasToString("Hello, Mr. Aleksey!");
    }

}