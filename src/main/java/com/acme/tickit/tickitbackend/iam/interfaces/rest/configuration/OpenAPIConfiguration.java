package com.acme.tickit.tickitbackend.iam.interfaces.rest.configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * OpenAPI Configuration.
 * <p>
 * This class is responsible for configuring OpenAPI/Swagger documentation.
 * It defines the JWT Bearer security scheme for authenticated endpoints.
 * </p>
 */
@Configuration
public class OpenAPIConfiguration {

    private static final String BEARER_AUTH = "bearerAuth";

    /**
     * Creates the OpenAPI configuration with JWT Bearer security scheme.
     *
     * @return The configured OpenAPI instance
     */
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Tickit Backend API")
                        .version("1.0.0")
                        .description("API documentation for Tickit Backend"))
                .components(new Components()
                        .addSecuritySchemes(BEARER_AUTH, new SecurityScheme()
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")
                                .description("JWT token authentication")));
    }
}
