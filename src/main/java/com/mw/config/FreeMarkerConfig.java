package com.mw.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
import org.springframework.web.servlet.view.freemarker.FreeMarkerViewResolver;

@Configuration
public class FreeMarkerConfig {
	
	@Bean
	public ViewResolver viewResolver() {
	    FreeMarkerViewResolver freeMarkerViewResolver = new FreeMarkerViewResolver();
	    freeMarkerViewResolver.setCache(true);
	    freeMarkerViewResolver.setPrefix("");
	    freeMarkerViewResolver.setSuffix(".ftl");
	    return freeMarkerViewResolver;
	}

	@Bean
	public FreeMarkerConfigurer freeMarkerConfigurer() {
	    FreeMarkerConfigurer freeMarkerConfigurer = new FreeMarkerConfigurer();
	    freeMarkerConfigurer.setTemplateLoaderPath("classpath:ftl");
	    return freeMarkerConfigurer;
	}
	
}
