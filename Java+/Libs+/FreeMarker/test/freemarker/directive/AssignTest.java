package freemarker.directive;

import freemarker.BaseFreemarkerTest;
import freemarker.template.TemplateException;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Use #assign directive.
 */
class AssignTest extends BaseFreemarkerTest {
    @Test
    void test() throws IOException, TemplateException {
        var data = Map.of();
        var template = cfg.getTemplate("directive/assign.ftl");
        template.process(data, out);
        assertThat(out).hasToString("String=abc");
    }
}