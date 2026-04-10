package com.company.recruit.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI recruitOpenApi() {
        return new OpenAPI().info(new Info()
            .title("企业招聘与求职平台 API")
            .description("面向求职者、招聘者和管理员的招聘系统后端接口文档")
            .version("v1.0.0")
            .contact(new Contact().name("Recruit Platform Team").email("dev@example.com"))
            .license(new License().name("Internal Use"))
        );
    }
}