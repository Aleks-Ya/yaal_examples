package view.implementation.custom;

import common.BaseTest;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.BeanNameViewResolver;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Custom implementation of {@link org.springframework.web.servlet.View}.
 */
@ContextConfiguration(classes = {CustomViewController.class, PrintTextView.class, CustomViewTest.Config.class})
class CustomViewTest extends BaseTest {

    @Test
    void requestParam() throws Exception {
        String message = "hello";
        mvc.perform(get(CustomViewController.ENDPOINT).param("message", message))
                .andExpect(status().isOk())
                .andExpect(content().string(message));
    }

    @Configuration
    static class Config {
        @Bean
        ViewResolver viewResolver() {
            return new BeanNameViewResolver();
        }
    }
}