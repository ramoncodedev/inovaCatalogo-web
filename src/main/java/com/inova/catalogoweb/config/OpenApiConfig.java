package com.inova.catalogoweb.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI catalogoWebOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("CatalogoWeb API")
                        .description("API da plataforma CatalogoWeb — catálogo digital e ferramenta de gestão para comércios.")
                        .version("v0.0.1")
                        .contact(new Contact()
                                .name("Inova")
                                .email("contato@inova.com")));
    }
}