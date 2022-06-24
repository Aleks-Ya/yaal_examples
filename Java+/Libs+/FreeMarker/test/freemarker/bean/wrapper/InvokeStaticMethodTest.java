package freemarker.bean.wrapper;

import freemarker.BaseFreemarkerTest;
import freemarker.ext.beans.BeansWrapperBuilder;
import freemarker.template.Configuration;
import freemarker.template.TemplateException;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Use static methods in templates.
 */
class InvokeStaticMethodTest extends BaseFreemarkerTest {

    @Test
    void test() throws IOException, TemplateException {
        var builder = new BeansWrapperBuilder(Configuration.VERSION_2_3_21);
        var beansWrapper = builder.build();

        Map<String, Object> data = new HashMap<>();
        data.put("statics", beansWrapper.getStaticModels());

        var template = cfg.getTemplate("bean/wrapper/invoke_static_method.ftl");

        var out = new StringWriter();
        template.process(data, out);

        assertThat(out).hasToString("Maximum=2");
    }
}