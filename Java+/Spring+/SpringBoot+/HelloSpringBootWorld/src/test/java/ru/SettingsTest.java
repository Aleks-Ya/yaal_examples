package ru;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

@SpringApplicationConfiguration(classes = ApplicationConfiguration.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class SettingsTest {

    @Autowired
    private PropertiesSettings properties;
    @Autowired
    private YamlSettings yaml;

    @Test
    public void properties() {
        assertThat(properties.getMessage(), equalTo("Hello world!"));
        assertThat(properties.getSystem().getStatus(), equalTo("System is OK."));
    }

    @Test
    public void yaml() {
        assertThat(yaml.getSuffix(), equalTo("Good bye!"));
    }
}