package freemarker.bean;

import freemarker.BaseFreemarkerTest;
import freemarker.template.TemplateException;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.Instant;
import java.util.Date;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class JavaBeanTest extends BaseFreemarkerTest {

    @Test
    void beanInMap() throws IOException, TemplateException {
        var data = Map.of("data", new Data());
        var template = cfg.getTemplate("bean/java_bean_in_map.ftl");
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
    void beanWithoutMap() throws IOException, TemplateException {
        var data = new Data();
        var template = cfg.getTemplate("bean/java_bean_without_map.ftl");
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

    /**
     * Getters required.
     */
    public static class Data {
        private int integer = 1;
        private double decimal = 2.5;
        private String string = "Data value";
        private Date date = Date.from(Instant.parse("2007-12-03T10:15:30.00Z"));
        private boolean bool = true;

        public String getString() {
            return string;
        }

        public int getInteger() {
            return integer;
        }

        public double getDecimal() {
            return decimal;
        }

        public Date getDate() {
            return date;
        }

        public boolean isBoolean() {
            return bool;
        }
    }
}
