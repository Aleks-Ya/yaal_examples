package freemarker.bean;

import freemarker.BaseClassTemplateLoaderTest;
import freemarker.template.TemplateException;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class RecordTest extends BaseClassTemplateLoaderTest {

    @Test
    void recordWithoutMap() throws IOException, TemplateException {
        var data = new Data(1, 2.5, "Data value", true);
        var template = cfg.getTemplate("bean/record_without_map.ftl");
        template.process(data, out);
        assertThat(out).hasToString("""
                String: Data value
                Integer: 1
                Decimal: 2.5
                Boolean (true/false): true
                Boolean (custom): ok
                """);
    }

    @Test
    void recordInMap() throws IOException, TemplateException {
        var recordObject = new Data(1, 2.5, "Data value", true);
        var data = Map.of("data", recordObject);
        var template = cfg.getTemplate("bean/record_in_map.ftl");
        template.process(data, out);
        assertThat(out).hasToString("""
                String: Data value
                Integer: 1
                Decimal: 2.5
                Boolean (true/false): true
                Boolean (custom): ok
                """);
    }

    public record Data(
            int integer,
            double decimal,
            String string,
            boolean bool
    ) {
    }
}
