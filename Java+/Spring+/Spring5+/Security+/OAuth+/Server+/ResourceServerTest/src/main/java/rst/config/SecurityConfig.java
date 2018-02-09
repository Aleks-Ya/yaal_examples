package rst.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

@Configuration
@EnableWebSecurity
@EnableResourceServer
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends ResourceServerConfigurerAdapter {
	@Override
	public void configure(final HttpSecurity http) throws Exception {
		http.authorizeRequests().anyRequest().access("#oauth2.hasScope('myscope')");
	}

	@Override
	public void configure(final ResourceServerSecurityConfigurer resources) {
		resources.resourceId("myresource");
	}
}
