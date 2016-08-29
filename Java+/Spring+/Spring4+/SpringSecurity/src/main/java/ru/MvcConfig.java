package ru;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@Configuration
public class MvcConfig {
	private static final int IMAGES_ORDER = 1;
	private static final int JSP_ORDER = 2;

	@Bean
	public InternalResourceViewResolver imagesResolver() {
		InternalResourceViewResolver resolver = new InternalResourceViewResolver();
		resolver.setOrder(IMAGES_ORDER);
		resolver.setPrefix("/WEB-INF/image/");
		// resolver.setSuffix(".jsp");
		return resolver;
	}

	@Bean
	public InternalResourceViewResolver jspResolver() {
		InternalResourceViewResolver resolver = new InternalResourceViewResolver();
		resolver.setOrder(JSP_ORDER);
		resolver.setPrefix("/WEB-INF/jsp/");
		resolver.setSuffix(".jsp");
		return resolver;
	}

}
