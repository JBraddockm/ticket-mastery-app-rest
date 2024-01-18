package org.example.config;

import org.example.dto.KeycloakProperties;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KeycloakConfig {
  private final KeycloakProperties keycloakProperties;

  public KeycloakConfig(KeycloakProperties keycloakProperties) {
    this.keycloakProperties = keycloakProperties;
  }

  @Bean
  Keycloak keycloak() {
    return KeycloakBuilder.builder()
        .serverUrl(keycloakProperties.getAuthServerUrl())
        .realm(keycloakProperties.getRealm())
        .grantType(OAuth2Constants.CLIENT_CREDENTIALS)
        .clientId(keycloakProperties.getAdminClientId())
        .clientSecret(keycloakProperties.getAdminClientSecret())
        .scope("openid")
        .build();
  }
}
