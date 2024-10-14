package ae.rakbank.bookingservice.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI springShopOpenAPI() {
        var server = new Server();
        server.url("http://localhost:8080/rakbank/event-booking-service/rest/api");
        return new OpenAPI()
                .servers(List.of(server))
                .info(new Info().title("Rak Bank Event Management")
                        .description("Service to manage events via Rak Bank API")
                        .version("v1")
                        .license(new License().name("Apache 2.0").url("http://springdoc.org")))
                .externalDocs(new ExternalDocumentation()
                        .description("Rak Bank Event Management API Documentation"));
    }

    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("bookings")
                .pathsToMatch("/v1/bookings/**")
                .build();
    }
}
