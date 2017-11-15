package controller.multipart;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

/**
 * @author Aleksey Yablokov
 */
@Configuration
public class Config {

    @Bean
    CommonsMultipartResolver commonsMultipartResolver(){
        return new CommonsMultipartResolver();
    }
}
