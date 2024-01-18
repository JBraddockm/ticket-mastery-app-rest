package org.example.config;

import static org.springframework.security.config.Customizer.withDefaults;

import java.util.Collection;
import java.util.stream.Stream;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.oidc.StandardClaimNames;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableMethodSecurity
@EnableWebSecurity
public class SecurityConfig {

  @Bean
  SecurityFilterChain filterChain(
      HttpSecurity http,
      ServerProperties serverProperties,
      @Value("${permit-all:[]}") String[] permitAll)
      throws Exception {

    // Configure a resource server with JWT decoder (the customized jwtAuthenticationConverter is
    // picked by Spring Boot)
    http.oauth2ResourceServer(oauth2 -> oauth2.jwt(withDefaults()));

    // State-less session (state in access-token only)
    http.sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

    // Disable CSRF because of state-less session-management
    http.csrf(AbstractHttpConfigurer::disable);

    // Return 401 (unauthorized) instead of 302 (redirect to login) when
    // authorization is missing or invalid
    http.exceptionHandling(
        eh ->
            eh.authenticationEntryPoint(
                (request, response, authException) -> {
                  response.addHeader(
                      HttpHeaders.WWW_AUTHENTICATE, "Bearer realm=\"Restricted Content\"");
                  response.sendError(
                      HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED.getReasonPhrase());
                }));

    // If SSL enabled, disable http (https only)
    if (serverProperties.getSsl() != null && serverProperties.getSsl().isEnabled()) {
      http.requiresChannel(channel -> channel.anyRequest().requiresSecure());
    }

    // @formatter:off
    http.authorizeHttpRequests(
        requests ->
            requests
                .requestMatchers(
                    Stream.of(permitAll)
                        .map(AntPathRequestMatcher::new)
                        .toArray(AntPathRequestMatcher[]::new))
                .permitAll()
                .anyRequest()
                .authenticated());
    // @formatter:on

    return http.build();
  }

  @Bean
  JwtAuthenticationConverter jwtAuthenticationConverter(
      Converter<Jwt, Collection<GrantedAuthority>> authoritiesConverter) {
    final var jwtAuthenticationConverter = new JwtAuthenticationConverter();
    jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(authoritiesConverter);
    jwtAuthenticationConverter.setPrincipalClaimName(StandardClaimNames.PREFERRED_USERNAME);
    return jwtAuthenticationConverter;
  }
}
