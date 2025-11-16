package freemarker.bean;

import freemarker.BaseFreemarkerTest;
import freemarker.template.TemplateException;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.Instant;
import java.util.Date;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class RecordTest extends BaseFreemarkerTest {

    @Test
    void recordWithoutMap() throws IOException, TemplateException {
        var data = new Data(1, 2.5, "Data value",
                Date.from(Instant.parse("2007-12-03T10:15:30.00Z")), true);
        var template = cfg.getTemplate("bean/record_without_map.ftl");
        template.process(data, out);
        assertThat(out).hasToString("""
                String: Data value
                Integer: 1
                Decimal: 2.5
                Boolean (true/false): true
                Boolean (custom): ok
                Date (time): 5:15:30 PM
                Date (date): Dec 3, 2007
                Date (datetime): Dec 3, 2007, 5:15:30 PM
                """);
    }

    @Test
    void recordInMap() throws IOException, TemplateException {
        var recordObject = new Data(1, 2.5, "Data value",
                Date.from(Instant.parse("2007-12-03T10:15:30.00Z")), true);
        var data = Map.of("data", recordObject);
        var template = cfg.getTemplate("bean/record_in_map.ftl");
        template.process(data, out);
        assertThat(out).hasToString("""
                String: Data value
                Integer: 1
                Decimal: 2.5
                Boolean (true/false): true
                Boolean (custom): ok
                Date (time): 5:15:30 PM
                Date (date): Dec 3, 2007
                Date (datetime): Dec 3, 2007, 5:15:30 PM
                """);
    }

    public record Data(
            int integer,
            double decimal,
            String string,
            Date date,
            boolean bool
    ) {
    }
}
