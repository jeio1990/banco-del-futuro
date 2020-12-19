package com.appgate.risk.swagger;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfiguration {

    private static final String API_VERSION = "1.0";
    private static final String API_TITLE = "Risk map bank API";
    private static final String API_DESCRIPTION = "A REST API to calculate risk map about service provider";

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2).select()
                .apis(RequestHandlerSelectors.basePackage("com.appgate.risk.controller")).paths(PathSelectors.any())
                .build().apiInfo(getApiInfo()).useDefaultResponseMessages(false);
    }

    private ApiInfo getApiInfo() {
        return new ApiInfoBuilder().title(API_TITLE).version(API_VERSION).description(API_DESCRIPTION).build();
    }
}
