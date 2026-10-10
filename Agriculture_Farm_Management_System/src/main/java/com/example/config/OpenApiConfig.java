package com.example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {

        return new OpenAPI()
                .info(new Info()
                        .title("Agriculture Farm Management System")
                        .version("1.0")
                        .description(
                                "REST APIs for managing farmers, farms, fields, crops, activities, expenses and harvests"));
    }
}