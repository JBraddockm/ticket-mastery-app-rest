package org.example.aspect;

import java.util.Map;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

  private final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);
  private static final String TOKEN_CLAIM_NAME = "preferred_username";

  private String getAuthenticatedUsername() {
    JwtAuthenticationToken authToken =
        (JwtAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();

    Map<String, Object> attributes = authToken.getTokenAttributes();

    return (String) attributes.get(TOKEN_CLAIM_NAME);
  }

  @Pointcut(
      "execution(* org.example.controller.ProjectController.*(..)) || execution(* org.example.controller.TaskController.*(..)))")
  public void anyProjectOrTaskControllerOperation() {}

  @Before("anyProjectOrTaskControllerOperation()")
  public void loggingAspect(JoinPoint joinPoint) {
    logger.info(
        "Before -> Method: {} - Authenticated User: {}",
        joinPoint.getSignature().getName(),
        getAuthenticatedUsername());
  }

  @AfterReturning(pointcut = "anyProjectOrTaskControllerOperation()", returning = "results")
  public void anyAfterProjectOrTaskControllerAdvice(JoinPoint joinPoint, Object results) {
    logger.info(
        "After Returning -> Method: {} - Authenticated User: {} - Result: {}",
        joinPoint.getSignature().toShortString(),
        getAuthenticatedUsername(),
        results.toString());
  }

  @AfterThrowing(pointcut = "anyProjectOrTaskControllerOperation()", throwing = "exception")
  public void anyAfterThrowingProjectOrTaskControllerAdvice(
      JoinPoint joinPoint, RuntimeException exception) {
    logger.info(
        "After Throwing -> User: {} - Method: {} - Exception: {}",
        getAuthenticatedUsername(),
        joinPoint.getSignature().getName(),
        exception.getMessage());
  }
}
