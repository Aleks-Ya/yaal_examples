package bean.partial;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({Person.class, City.class})
class Config {
}
