package freemarker.bean;

import freemarker.BaseFreemarkerTest;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Date;

/**
 * Использование JavaBean в модели и шаблоне (без Map).
 */
public class JavaBeanWithoutMap extends BaseFreemarkerTest {

    @Test
    void test() throws IOException, TemplateException {
        Data data = new Data();

        Template template = cfg.getTemplate("bean/java_bean_without_map.ftl");

        Writer out = new OutputStreamWriter(System.out);
        template.process(data, out);
    }

    /**
     * Getters required.
     */
    public static class Data {
        private int integer = 1;
        private double decimal = 2.5;
        private String string = "Data value";
        private Date date = new Date();
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
