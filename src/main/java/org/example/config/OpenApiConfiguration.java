package org.example.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfiguration {
  @Bean
  public OpenAPI openAPI() {
    return new OpenAPI()
        .info(
            new Info()
                .title("TicketMastery API")
                .version("1.0")
                .description(
                    "This is the API documentation for the TicketMastery ticketing application based on the OpenAPI 3.0 specification. \n\n Some useful links:\n- [The TicketMastery repository](https://github.com/JBraddockm/ticket-mastery-ticketing-app-rest)\n- [The source API definition for the TicketMastery](http://localhost:8080/api-docs.yaml)")
                .contact(
                    new Contact()
                        .name("JBraddock")
                        .email("info@example.org")
                        .url("https://github.com/jbraddockm")));
  }
}
