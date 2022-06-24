package freemarker.directive.if_;

import freemarker.BaseFreemarkerTest;
import freemarker.template.TemplateException;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class IfBooleanTest extends BaseFreemarkerTest {

    @Test
    void test() throws IOException, TemplateException {
        Map<String, Object> data = new HashMap<>();
        data.put("large", true);
        data.put("fat", false);

        var template = cfg.getTemplate("directive/if_/if_boolean.ftl");

        var out = new StringWriter();
        template.process(data, out);

        assertThat(out).hasToString("Large\nThin");
    }
}