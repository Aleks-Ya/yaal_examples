package ru.yaal.spring.mvc.config;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

@SpringBootTest(classes = ApplicationConfiguration.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class SettingsTest {

    @Autowired
    private PropertiesSettings properties;
    @Autowired
    private YamlSettings yaml;

    @Test
    public void properties() {
    	assertNotNull(properties);
        assertThat(properties.getMessage(), equalTo("Hello world!"));
        assertThat(properties.getSystem().getStatus(), equalTo("System is OK."));
    }

    @Test
    public void yaml() {
    	assertNotNull(yaml);
        assertThat(yaml.isEnabled(), equalTo(true));
        assertThat(yaml.getMessage().getPrefix(), equalTo("Hi, everybody!"));
        assertThat(yaml.getMessage().getSuffix(), equalTo("Good bye!"));
        assertThat(yaml.getMessage().getUnderwrite(), equalTo(""));
    }

}