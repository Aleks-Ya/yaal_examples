package controller.message.converter;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Treat the body as JSON using @RequestBody and auto converting to JSON.
 */
@RunWith(SpringRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = {JsonAutoController.class, JsonAutoControllerTest.Config.class})
public class JsonAutoControllerTest {
    @Autowired
    private WebApplicationContext context;

    private MockMvc mvc;

    @Before
    public void setup() {
        mvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    public void requestParam() throws Exception {
        mvc.perform(
                post(JsonAutoController.ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"name\": \"abc\"}")
        )
                .andExpect(status().isOk())
                .andExpect(content().string("\"response=abc\""));
    }

    @EnableWebMvc
    @Configuration
    static class Config extends WebMvcConfigurerAdapter {

        @Override
        public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
            HttpMessageConverter<?> httpMessageConverter = converters.stream()
                    .filter(converter -> converter instanceof MappingJackson2HttpMessageConverter)
                    .findAny().get();
            converters.remove(httpMessageConverter);
            converters.add(0, httpMessageConverter);
        }
    }
}