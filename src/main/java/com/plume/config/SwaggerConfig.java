package com.plume.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableWebMvc
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

    // Swagger 문서 상단 설정
    @Bean
    public OpenAPI getOpenApi() {
        return new OpenAPI()
                .components(new Components())
                .info(new Info()
                        .version(ver)
                        .description(description)
                        .title(title)
                );
    }

    // Swagger 셀렉트 박스 분류 설정 - Version 1.0
    @Bean
    public GroupedOpenApi getVersion1Api() {
        return GroupedOpenApi.builder()
                .group(groupName)
                .pathsToMatch("/api/v1/sample/**")
                .build();
    }

}
