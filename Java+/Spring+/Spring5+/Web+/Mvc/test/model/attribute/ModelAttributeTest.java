package model.attribute;

import common.BaseTest;
import org.junit.Test;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.BeanNameViewResolver;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Using {@link org.springframework.web.bind.annotation.ModelAttribute} annotation.
 */
@ContextConfiguration(classes = {ModelAttributeController.class, WeekdayView.class, ModelAttributeTest.Config.class})
public class ModelAttributeTest extends BaseTest {

    @Test
    public void modelAttribute() throws Exception {
        mvc.perform(get(ModelAttributeController.ENDPOINT).param("yearParam", "2018"))
                .andExpect(status().isOk())
                .andExpect(content().string("31 Monday 2018"));
    }

    @Configuration
    static class Config {
        @Bean
        ViewResolver viewResolver() {
            return new BeanNameViewResolver();
        }
    }
}