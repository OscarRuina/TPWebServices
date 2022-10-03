package com.unla.administrador.configuraciones.swagger;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConfiguracionSwagger {

    @Bean
    public GroupedOpenApi apiController() {
        return GroupedOpenApi.builder()
                .group("Web Services")
                .packagesToScan("com.unla.administrador")
                .build();
    }

    @Bean
    public OpenAPI springShopOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("Web Services REST/SOAP")
                        .description("Trabajo Practico Web Services")
                        .version("v0.0.1"));

    }

}
