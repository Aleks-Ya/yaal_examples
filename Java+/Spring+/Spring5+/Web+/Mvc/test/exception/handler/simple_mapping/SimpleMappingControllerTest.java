package exception.handler.simple_mapping;

import common.BaseTest;
import common.TextView;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;
import org.springframework.web.servlet.view.BeanNameViewResolver;

import java.io.FileNotFoundException;
import java.util.Properties;

import static exception.handler.simple_mapping.SimpleMappingController.FILE_NOT_FOUND_EXCEPTION;
import static exception.handler.simple_mapping.SimpleMappingController.MISSING_SERVLET_PARAM_EXCEPTION;
import static exception.handler.simple_mapping.SimpleMappingController.NUMBER_FORMAT_EXCEPTION;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Use {@link SimpleMappingExceptionResolver}.
 */
@ContextConfiguration(classes = {SimpleMappingController.class, SimpleMappingControllerTest.Config.class})
class SimpleMappingControllerTest extends BaseTest {

    private static final int HTTP_STATUS = 409;

    @Test
    void fileNotFoundException() throws Exception {
        mvc.perform(get(FILE_NOT_FOUND_EXCEPTION))
                .andExpect(status().is(HTTP_STATUS))
                .andExpect(content().string("Hi viewFileNotFoundException"));
    }


    @Test
    void numberFormatException() throws Exception {
        mvc.perform(get(NUMBER_FORMAT_EXCEPTION))
                .andExpect(status().is(HTTP_STATUS))
                .andExpect(content().string("Hi viewNumberFormatException"));
    }

    @Test
    void missingServletRequestParameterException() throws Exception {
        mvc.perform(get(MISSING_SERVLET_PARAM_EXCEPTION))
                .andExpect(status().is(HTTP_STATUS))
                .andExpect(content().string("Hi viewDefault"));
    }

    @Configuration
    static class Config {
        private static final String VIEW_FILE_NOT_FOUND_EXCEPTION = "viewFileNotFoundException";
        private static final String VIEW_NUMBER_FORMAT_EXCEPTION = "viewNumberFormatException";
        private static final String VIEW_DEFAULT = "viewDefault";

        @Bean
        public SimpleMappingExceptionResolver simpleMappingExceptionResolver() {
            var resolver = new SimpleMappingExceptionResolver();
            var mapping = new Properties();
            mapping.setProperty(FileNotFoundException.class.getName(), VIEW_FILE_NOT_FOUND_EXCEPTION);
            mapping.setProperty(NumberFormatException.class.getName(), VIEW_NUMBER_FORMAT_EXCEPTION);
            resolver.setExceptionMappings(mapping);
            resolver.setDefaultStatusCode(HTTP_STATUS);
            resolver.setDefaultErrorView(VIEW_DEFAULT);
            return resolver;
        }

        @Bean
        ViewResolver viewResolver() {
            return new BeanNameViewResolver();
        }

        @Bean(VIEW_FILE_NOT_FOUND_EXCEPTION)
        View viewFileNotFoundException() {
            return new TextView("Hi " + VIEW_FILE_NOT_FOUND_EXCEPTION);
        }

        @Bean(VIEW_NUMBER_FORMAT_EXCEPTION)
        View viewNumberFormatException() {
            return new TextView("Hi " + VIEW_NUMBER_FORMAT_EXCEPTION);
        }

        @Bean(VIEW_DEFAULT)
        View viewDefault() {
            return new TextView("Hi " + VIEW_DEFAULT);
        }

    }
}
