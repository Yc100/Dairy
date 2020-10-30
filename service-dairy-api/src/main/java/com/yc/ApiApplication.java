/**
 * Copyright (c) 2020
 */

package com.yc;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

/**
 * dairy-api
 *
 * @author YC
 */
@SpringBootApplication
@ComponentScan(basePackages = {"com.yc"})
public class ApiApplication extends SpringBootServletInitializer {

	@Bean
	public SpringUtil getSpringUtil(){
		return new SpringUtil();
	}
	public static void main(String[] args) {
		SpringApplication.run(ApiApplication.class, args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(ApiApplication.class);
	}
}
