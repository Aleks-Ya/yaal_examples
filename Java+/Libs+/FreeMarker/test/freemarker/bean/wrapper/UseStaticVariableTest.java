package freemarker.bean.wrapper;

import freemarker.BaseClassTemplateLoaderTest;
import freemarker.ext.beans.BeansWrapperBuilder;
import freemarker.template.TemplateException;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class UseStaticVariableTest extends BaseClassTemplateLoaderTest {
    @Test
    void test() throws IOException, TemplateException {
        var builder = new BeansWrapperBuilder(version);
        var beansWrapper = builder.build();
        var data = Map.of("statics", beansWrapper.getStaticModels());
        var template = cfg.getTemplate("bean/wrapper/use_static_variable.ftl");
        template.process(data, out);
        assertThat(out).hasToString("Separator=" + File.separator);
    }
}