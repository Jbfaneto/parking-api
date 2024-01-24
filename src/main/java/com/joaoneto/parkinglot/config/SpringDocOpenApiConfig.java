package com.joaoneto.parkinglot.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringDocOpenApiConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
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
}
