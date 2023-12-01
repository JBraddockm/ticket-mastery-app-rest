package org.example.config;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

@Configuration
public class SecurityConfig {

  private final PersistentTokenRepository tokenRepository;
  private final AuthSuccessHandler authSuccessHandler;

  public SecurityConfig(
      PersistentTokenRepository tokenRepository, AuthSuccessHandler authSuccessHandler) {
    this.tokenRepository = tokenRepository;
    this.authSuccessHandler = authSuccessHandler;
  }

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {

    httpSecurity
        .authorizeHttpRequests(
            auth ->
                auth.requestMatchers(PathRequest.toStaticResources().atCommonLocations())
                    .permitAll()
                    .requestMatchers("/svg/**")
                    .permitAll()
                    .requestMatchers("/task/employee/**")
                    .hasRole("Employee")
                    .requestMatchers("/project/manager/**", "/task/**")
                    .hasRole("Manager")
                    .requestMatchers("/project/**", "/user/**")
                    .hasRole("Admin")
                    .anyRequest()
                    .authenticated())
        .formLogin(
            formLoginConfigurer ->
                formLoginConfigurer
                    .loginPage("/login")
                    .successHandler(authSuccessHandler)
                    .permitAll())
        .logout(
            formLogoutConfigurer ->
                formLogoutConfigurer.logoutUrl("/logout").logoutSuccessUrl("/login").permitAll())
        .rememberMe(
            remember ->
                remember
                    .tokenRepository(tokenRepository)
                    .key("ticket")
                    .tokenValiditySeconds(604800)
                    .rememberMeCookieName("ticket-mastery")
                    .useSecureCookie(true));

    return httpSecurity.build();
  }
}
