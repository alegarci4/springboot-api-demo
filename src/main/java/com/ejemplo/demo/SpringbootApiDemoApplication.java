package com.ejemplo.demo;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SpringbootApiDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootApiDemoApplication.class, args);
    }

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Workshop Spring Boot API")
                        .version("1.0.0")
                        .description("API REST de ejemplo con buenas practicas - Progra 3")
                        .contact(new Contact()
                                .name("Estudiante")
                                .email("estudiante@universidad.edu")));
    }
}