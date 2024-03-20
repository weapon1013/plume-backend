package com.plume.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

//@EnableWebMvc
@Configuration
public class SwaggerConfig {

    @Value("${swagger.group-name}")
    private String groupName;

    @Value("${swagger.title}")
    private String title;

    @Value("${swagger.ver}")
    private String ver;

    @Value("${swagger.base-package}")
    private String basePackage;

    @Value("${swagger.description}")
    private String description;

    @Bean
    public Docket api(){
        return new Docket(DocumentationType.OAS_30)
                .groupName(groupName)
                .useDefaultResponseMessages(false)
                .select()
                .apis(RequestHandlerSelectors.basePackage(basePackage))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(new ApiInfoBuilder()
                        .title(title)
                        .description(description)
                        .version(ver)
                        .build()
                );
    }


}
