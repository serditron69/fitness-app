package com.example.fitnessapp.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Fitness App API")
                        .description("API REST para la aplicación de entrenamiento fitness. " +
                                "Permite gestionar usuarios, ejercicios, rutinas, " +
                                "registros de entrenamiento y seguimiento de progreso.")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Fitness App")
                                .email("admin@fitnessapp.com"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("https://springdoc.org")));
    }
}