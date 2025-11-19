package configuration;

import org.apache.commons.configuration2.builder.fluent.Configurations;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static util.ResourceUtil.resourceToFile;

class ParsePropertiesFileTest {
    @Test
    void parse() throws ConfigurationException {
        var configs = new Configurations();
        var file = resourceToFile("configuration/config.properties");
        var config = configs.properties(file);
        var city = config.getString("city");
        var quotesUnescaped = config.getString("quotes_unescaped");
        var quotesEscaped = config.getString("quotes_escaped");
        var quotesEscapedTwice = config.getString("quotes_escaped_twice");
        var year = config.getInt("year");
        assertThat(city).isEqualTo("London");
        assertThat(quotesUnescaped).isEqualTo("""
                ["red"]""");
        assertThat(quotesEscaped).isEqualTo("""
                ["red"]""");
        assertThat(quotesEscapedTwice).isEqualTo("""
                [\\"red\\"]""");
        assertThat(year).isEqualTo(2025);
    }
}
