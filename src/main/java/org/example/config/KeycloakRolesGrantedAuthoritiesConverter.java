package org.example.config;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;
import org.example.dto.KeycloakProperties;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

@Component
public class KeycloakRolesGrantedAuthoritiesConverter
    implements Converter<Jwt, Collection<GrantedAuthority>> {

  private final KeycloakProperties keycloakProperties;

  public KeycloakRolesGrantedAuthoritiesConverter(KeycloakProperties keycloakProperties) {
    this.keycloakProperties = keycloakProperties;
  }

  @Override
  @SuppressWarnings({"unchecked"})
  public List<GrantedAuthority> convert(Jwt jwt) {
    final var resourceAccess =
        (Map<String, Object>) jwt.getClaims().getOrDefault("resource_access", Map.of());
    final var confidentialClientAccess =
        (Map<String, Object>)
            resourceAccess.getOrDefault(keycloakProperties.getClientID(), Map.of());
    final var confidentialClientRoles =
        (Collection<String>) confidentialClientAccess.getOrDefault("roles", List.of());

    final var realmAccess =
        (Map<String, Object>) jwt.getClaims().getOrDefault("realm_access", Map.of());
    final var realmRoles = (List<String>) realmAccess.getOrDefault("roles", List.of());

    return Stream.concat(confidentialClientRoles.stream(), realmRoles.stream())
        .map(SimpleGrantedAuthority::new)
        .map(GrantedAuthority.class::cast)
        .toList();
  }
}
