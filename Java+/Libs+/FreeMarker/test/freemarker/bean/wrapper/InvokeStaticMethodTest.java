package freemarker.bean.wrapper;

import freemarker.BaseFreemarkerTest;
import freemarker.ext.beans.BeansWrapperBuilder;
import freemarker.template.Configuration;
import freemarker.template.TemplateException;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class InvokeStaticMethodTest extends BaseFreemarkerTest {
    @Test
    void test() throws IOException, TemplateException {
        var builder = new BeansWrapperBuilder(Configuration.VERSION_2_3_34);
        var beansWrapper = builder.build();
        var data = Map.of("statics", beansWrapper.getStaticModels());
        var template = cfg.getTemplate("bean/wrapper/invoke_static_method.ftl");
        template.process(data, out);
        assertThat(out).hasToString("Maximum=2");
    }
}