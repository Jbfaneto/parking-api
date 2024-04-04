package com.joaoneto.parkinglot.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringDocOpenApiConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .components(
                        new Components().addSecuritySchemes("security", securityScheme())
                )
                .info(
                        new Info()
                                .title("Parking Lot Rest API")
                                .description("API To manage a parking lot")
                                .version("1.0.0")
                                .license(
                                        new License()
                                                .name("MIT License")
                                                .url("https://opensource.org/licenses/MIT")
                                )
                                .contact(
                                        new Contact()
                                                .name("João Neto")
                                                .email("jbfaneto@gmail.com")
                                )
                );
    }

    private SecurityScheme securityScheme() {
        return new SecurityScheme()
                .description("Insert a valid bearer token")
                .type(SecurityScheme.Type.HTTP)
                .in(SecurityScheme.In.HEADER)
                .scheme("bearer")
                .bearerFormat("JWT")
                .name("security");
    }
}
