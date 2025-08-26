package ru.hogwarts.school.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Hogwarts School API",
                version = "1.0.0",
                description = "API for managing Hogwarts School students, faculties and avatars"
        )
)
public class SwaggerConfig {
}