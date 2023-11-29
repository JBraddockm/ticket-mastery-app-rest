package org.example.config;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
    httpSecurity
        .authorizeHttpRequests(
            auth ->
                auth.requestMatchers(PathRequest.toStaticResources().atCommonLocations())
                    .permitAll()
                    .requestMatchers("/svg/*")
                    .permitAll()
                    .requestMatchers("/user/**", "/project/**")
                    .hasRole("Admin")
                    .requestMatchers("/project/manager/**", "/task/**")
                    .hasRole("Manager")
                    .requestMatchers("/employee/**")
                    .hasRole("Employee")
                    .anyRequest()
                    .authenticated())
        .formLogin(formLoginConfigurer -> formLoginConfigurer.loginPage("/login").permitAll())
        .logout(
            formLogoutConfigurer ->
                formLogoutConfigurer.logoutUrl("/logout").logoutSuccessUrl("/login").permitAll())
        .rememberMe(Customizer.withDefaults());

    return httpSecurity.build();
  }
}
