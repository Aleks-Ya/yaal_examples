package freemarker.directive.include;

import freemarker.BaseClassTemplateLoaderTest;
import freemarker.template.TemplateException;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

class IncludeTest extends BaseClassTemplateLoaderTest {
    @Test
    void include() throws TemplateException, IOException {
        var template = cfg.getTemplate("directive/include/outer.ftl");
        template.process(null, out);
        assertThat(out.toString()).isEqualTo("Man John 25");
    }
}
