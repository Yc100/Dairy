/**
 * Copyright (c) 2020
 */

package com.yc.config;

import com.yc.common.constant.Constant;
import io.swagger.annotations.ApiOperation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

@Configuration
@EnableSwagger2
@Profile({"test","dev"})
public class SwaggerConfig {

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                //加了ApiOperation注解的类，生成接口文档
                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                //包下的类，生成接口文档
                .apis(RequestHandlerSelectors.basePackage("com.yc"))
                .paths(PathSelectors.any())
                .build()
                .directModelSubstitute(java.util.Date.class, String.class)
                .securitySchemes(security());

    }
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
            .title("Dairy2.0 RESTful APIs")
            .description("Dairy2.0系统 RESTful APIs")
            .termsOfServiceUrl("")
            .version("2.0")
            .build();
    }
   private List<ApiKey> security() {
        return newArrayList(
            new ApiKey(Constant.TOKEN_HEADER, Constant.TOKEN_HEADER, "header")

        );
    }

}