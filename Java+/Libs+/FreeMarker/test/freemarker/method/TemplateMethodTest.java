package freemarker.method;

import freemarker.BaseFreemarkerTest;
import freemarker.template.TemplateException;
import freemarker.template.TemplateMethodModelEx;
import freemarker.template.TemplateModelException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class TemplateMethodTest extends BaseFreemarkerTest {

    @BeforeAll
    public static void additionalSetUp() {
        cfg.setSharedVariable("sum", new SumTemplateMethodModel());
    }

    @Test
    void test() throws IOException, TemplateException {
        var data = Map.of();
        var template = cfg.getTemplate("method/template_method_test.ftl");
        template.process(data, out);
        assertThat(out).hasToString("Sum=5");
    }

    public static class SumTemplateMethodModel implements TemplateMethodModelEx {
        @Override
        public Object exec(List arguments) throws TemplateModelException {
            try {
                return Integer.parseInt(arguments.get(0).toString()) + Integer.parseInt(arguments.get(1).toString());
            } catch (Exception e) {
                throw new TemplateModelException("Arguments: " + arguments, e);
            }
        }
    }

}