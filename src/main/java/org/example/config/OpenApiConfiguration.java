package org.example.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.*;
import org.example.dto.KeycloakProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// @SecurityScheme(
//    name = "security_auth",
//    type = SecuritySchemeType.OAUTH2,
//    flows =
//        @OAuthFlows(
//            authorizationCode =
//                @OAuthFlow(
//                    authorizationUrl = "${keycloak.auth-server-url}",
//                    tokenUrl =
//                        "http://localhost:8442/realms/local-dev/protocol/openid-connect/token")))
@Configuration
public class OpenApiConfiguration {
  private static final String OAUTH_SCHEME_NAME = "oAuth";
  private static final String PROTOCOL_URL_FORMAT = "%s/realms/%s/protocol/openid-connect";

  private final KeycloakProperties keycloakProperties;

  public OpenApiConfiguration(KeycloakProperties keycloakProperties) {
    this.keycloakProperties = keycloakProperties;
  }

  @Bean
  public OpenAPI customOpenApi() {
    return new OpenAPI()
        .info(getInfo())
        .components(new Components().addSecuritySchemes(OAUTH_SCHEME_NAME, createOAuthScheme()))
        .addSecurityItem(new SecurityRequirement().addList(OAUTH_SCHEME_NAME));
  }

  private Info getInfo() {
    return new Info()
        .title("TicketMastery API")
        .version("1.0")
        .description(
            "This is the API documentation for the TicketMastery ticketing application based on the OpenAPI 3.0 specification. \n\n Some useful links:\n- [The TicketMastery repository](https://github.com/JBraddockm/ticket-mastery-ticketing-app-rest)\n- [The source API definition for the TicketMastery](http://localhost:8080/api-docs.yaml)")
        .contact(
            new Contact()
                .name("JBraddock")
                .email("info@example.org")
                .url("https://github.com/jbraddockm"));
  }

  private SecurityScheme createOAuthScheme() {
    OAuthFlows flows = createOAuthFlows();

    return new SecurityScheme().type(SecurityScheme.Type.OAUTH2).flows(flows);
  }

  private OAuthFlows createOAuthFlows() {
    OAuthFlow flow = createAuthorizationCodeFlow();

    return new OAuthFlows().authorizationCode(flow);
  }

  private OAuthFlow createAuthorizationCodeFlow() {
    var protocolUrl = String.format(PROTOCOL_URL_FORMAT,
            keycloakProperties.getAuthServerUrl(), keycloakProperties.getRealm());

    return new OAuthFlow()
        .authorizationUrl(protocolUrl + "/auth")
        .tokenUrl(protocolUrl + "/token")
        .scopes(new Scopes().addString("openid", ""));
  }
}
