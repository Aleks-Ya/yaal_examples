package util.properties;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Properties;

import static org.assertj.core.api.Assertions.assertThat;
import static util.ResourceUtil.resourceToInputStream;

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
        var properties = new Properties();
        properties.setProperty("a", "host:1234");
        var writer = new StringWriter();
        properties.store(writer, "Comment 1");
        var actContent = writer.toString();
        assertThat(actContent).contains("a=host\\:1234");
    }

    @Test
    void storeNoComment() {
        var properties = new Properties();
        properties.setProperty("a", "host:1234");
        properties.setProperty("b", "http://abc.com");
        var writer = new StringWriter();
        properties.forEach((k, v) -> writer.write(k + "=" + v + "\n"));
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

    @Test
    void spaces() throws IOException {
        var properties = new Properties();
        var key = "a b";
        var value = "123 456";
        properties.setProperty(key, value);
        var writer = new StringWriter();
        properties.store(writer, null);
        var content = writer.toString();
        assertThat(content).containsSubsequence("a\\ b=123 456");
        var actProps = new Properties();
        actProps.load(new StringReader(content));
        assertThat(actProps).containsEntry(key, value);
    }

    @Test
    void quotes() throws IOException {
        var properties = new Properties();
        properties.load(resourceToInputStream("util/properties/quotes.properties"));
        var actUnescaped = properties.getProperty("colors_unescaped");
        var actEscaped = properties.getProperty("colors_escaped");
        var actEscapedTwice = properties.getProperty("colors_escaped_twice");
        assertThat(actUnescaped).isEqualTo("""
                ["red", "green"]""");
        assertThat(actEscaped).isEqualTo("""
                ["red", "green"]""");
        assertThat(actEscapedTwice).isEqualTo("""
                [\\"red\\", \\"green\\"]""");
    }

}