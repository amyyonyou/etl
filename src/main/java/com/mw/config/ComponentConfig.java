package com.mw.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@ComponentScan(basePackages = { "com.mw.plugins", "com.mw.base", "com.mw.ctdb", "com.mw.scada", "com.mw.datlog", "com.mw.bi" })
@PropertySource(value = { "classpath:config.properties", "classpath:app.properties" })
@EnableScheduling
public class ComponentConfig {
	@Bean
	public static PropertySourcesPlaceholderConfigurer propertyPlaceholderConfigurer() {
		return new PropertySourcesPlaceholderConfigurer();
	}
}
