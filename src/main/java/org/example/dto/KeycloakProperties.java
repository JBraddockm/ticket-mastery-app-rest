package org.example.dto;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Data
public class KeycloakProperties {

  @Value("${keycloak.realm}")
  private String realm;

  @Value("${keycloak.auth-server-url}")
  private String authServerUrl;

  @Value("${keycloak-client-id}")
  private String clientID;

  @Value("${keycloak-client-secret}")
  private String clientSecret;

  @Value("${keycloak.adminClientId}")
  private String adminClientId;

  @Value("${keycloak.adminClientSecret}")
  private String adminClientSecret;
}
