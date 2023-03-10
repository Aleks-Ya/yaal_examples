package util.properties;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Properties;

import static org.assertj.core.api.Assertions.assertThat;
import static util.ResourceUtil.resourceToInputStream;

/**
 * Using {@link java.util.Properties}.
 */
class PropertiesTest {

    @Test
    void defaultProperties() {
        var key = "a";
        var value = "1";

        var defaultProps = new Properties();
        defaultProps.setProperty(key, value);

        var actual = new Properties(defaultProps);
        assertThat(actual.getProperty(key)).isEqualTo(value);

        assertThat(actual.size()).isEqualTo(0);
        assertThat(actual).isEmpty();
    }

    @Test
    void multilineValues() throws IOException {
        var actual = new Properties();
        actual.load(resourceToInputStream("util/properties/multiline.properties"));
        assertThat(actual).containsEntry("single.line.value", "Hello, Properties!");
        assertThat(actual).containsEntry("multi.line.value", "Hello, Properties!");
    }

    @Test
    void loadOverride() throws IOException {
        var properties = new Properties();
        properties.load(resourceToInputStream("util/properties/load_override_1.properties"));
        properties.load(resourceToInputStream("util/properties/load_override_2.properties"));
        assertThat(properties).containsEntry("person.name", "Mary");
        assertThat(properties).containsEntry("person.age", "30");
    }

    @Test
    void store() throws IOException {
        var props = new Properties();
        props.setProperty("a", "host:1234");
        var writer = new StringWriter();
        props.store(writer, "Comment 1");
        var actContent = writer.toString();
        assertThat(actContent).contains("a=host\\:1234");
    }

    @Test
    void storeNoComment() {
        var props = new Properties();
        props.setProperty("a", "host:1234");
        props.setProperty("b", "http://abc.com");
        var writer = new StringWriter();
        props.forEach((k, v) -> writer.write(k + "=" + v + "\n"));
        var actContent = writer.toString();
        assertThat(actContent).isEqualTo("a=host:1234\nb=http://abc.com\n");
    }

    @Test
    void propertiesToString() {
        var properties = new Properties();
        properties.put("abc", "123");
        properties.put("xyz", "987");
        assertThat(properties).hasToString("{abc=123, xyz=987}");
    }

}