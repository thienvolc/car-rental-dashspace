package dashspace.fun.car_rental_server.infrastructure.config;

import dashspace.fun.car_rental_server.infrastructure.constant.SecurityPaths;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile("!pro")
@OpenAPIDefinition(
        info = @Info(
                contact = @Contact(
                        name = "Dashspace Car Rental Management Team",
                        email = "contact@dashspace.fun",
                        url = "https://dashspace.fun"
                ),
                description = """
                        OpenAPI documentation for Dashspace Car Rental Management System
                        """,
                title = "OpenAPI specification",
                version = "1.0.0",
                license = @License(
                        name = "MIT License",
                        url = "https://opensource.org/license/mit/"
                ),
                termsOfService = "https://dashspace.fun/terms-of-service/"
        ),
        servers = {
                @Server(
                        description = "Local Development",
                        url = "http://localhost:8080"
                ),
                @Server(
                        description = "Production Environment",
                        url = "https://car-rental.dashspace.fun"
                )
        },
        security = {
                @SecurityRequirement(
                        name = "bearerAuth"
                )
        }
)
@SecurityScheme(
        name = "bearerAuth",
        description = "JWT Bearer Token Authentication",
        type = SecuritySchemeType.HTTP,
        scheme = "bearer",
        bearerFormat = "JWT",
        in = SecuritySchemeIn.HEADER
)
@Configuration
public class OpenApiConfig {

    @Bean
    public GroupedOpenApi groupPublicApi() {
        return GroupedOpenApi.builder()
                .group("public")
                .pathsToMatch(SecurityPaths.PUBLIC_PATHS)
                .build();
    }

    @Bean
    public GroupedOpenApi groupUserApi() {
        return GroupedOpenApi.builder()
                .group("user")
                .pathsToMatch(SecurityPaths.USER_PATHS)
                .build();
    }

    @Bean
    public GroupedOpenApi groupHostApi() {
        return GroupedOpenApi.builder()
                .group("host")
                .pathsToExclude(SecurityPaths.HOST_PATHS)
                .build();
    }

    @Bean
    public GroupedOpenApi groupAdminApi() {
        return GroupedOpenApi.builder()
                .group("admin")
                .pathsToExclude(SecurityPaths.ADMIN_PATHS)
                .build();
    }
}
