package ru.yaal.spring.mvc.config;

import java.io.IOException;
import java.util.List;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import ru.yaal.spring.mvc.controller.argument.RequestBodyController.UserConverter;

@Configuration
public class WebAppContext extends WebMvcConfigurerAdapter {

	@Autowired
	private UserConverter converter;

	@Override
	public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
		assert converter != null;
		converters.add(converter);
	}
	
	@Bean
	public InternalResourceViewResolver viewResolver() {
		InternalResourceViewResolver resolver = new InternalResourceViewResolver();
		resolver.setPrefix("/WEB-INF/jsp/");
		resolver.setSuffix(".jsp");
		return resolver;
	}
	
	@Bean
	public SimpleMappingExceptionResolver exceptionResolver() {
		Properties props = new Properties();
		props.put(IOException.class.getName(), "exception/io_error");

		SimpleMappingExceptionResolver resolver = new SimpleMappingExceptionResolver();
		resolver.setExceptionMappings(props);
		resolver.setDefaultErrorView("exception/unexpected_error");
		
		return resolver;
	}
}
