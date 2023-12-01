package org.example.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
public class AuthSuccessHandler implements AuthenticationSuccessHandler {

  private final RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

  @Override
  public void onAuthenticationSuccess(
      HttpServletRequest request, HttpServletResponse response, Authentication authentication)
      throws IOException {

    handle(request, response, authentication);
    clearAuthenticationAttributes(request);
  }

  private void handle(
      HttpServletRequest request, HttpServletResponse response, Authentication authentication)
      throws IOException {

    String targetUrl = determineTargetUrl(authentication);

    if (response.isCommitted()) {
      return;
    }

    redirectStrategy.sendRedirect(request, response, targetUrl);
  }

  private String determineTargetUrl(Authentication authentication) {

    Map<String, String> roleTargetUrlMap = new HashMap<>();

    roleTargetUrlMap.put("ROLE_Admin", "/welcome");
    roleTargetUrlMap.put("ROLE_Manager", "/project/manager/project-status");
    roleTargetUrlMap.put("ROLE_Employee", "/task/employee/pending-tasks");

    final Set<GrantedAuthority> authorities = new HashSet<>(authentication.getAuthorities());

    for (GrantedAuthority authority : authorities) {

      String authorityName = authority.getAuthority();

      if (roleTargetUrlMap.containsKey(authorityName)) {
        return roleTargetUrlMap.get(authorityName);
      }
    }

    throw new IllegalStateException();
  }

  private void clearAuthenticationAttributes(HttpServletRequest request) {

    HttpSession session = request.getSession(false);

    if (session == null) {
      return;
    }

    session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
  }
}
