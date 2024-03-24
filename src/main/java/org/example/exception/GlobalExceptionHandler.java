package org.example.exception;

import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.NoSuchElementException;
import org.example.dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

  // TODO Catch BadCredentialsException.class and AccessDeniedException.class after implementing
  // Spring Security.

  // TODO Enable stacktrace for development environment.

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<Object> handleMethodArgumentNotValid(
      MethodArgumentNotValidException e, HttpServletRequest request) {

    List<String> errors = e.getFieldErrors().stream().map(FieldError::getDefaultMessage).toList();

    return ErrorResponse.buildErrorResponse(
        HttpStatus.UNPROCESSABLE_ENTITY, "Validation Failed", errors, request);
  }

  @ExceptionHandler({
    ProjectNotFoundException.class,
    UserNotFoundException.class,
    RoleNotFoundException.class,
    TaskNotFoundException.class
  })
  public ResponseEntity<Object> handleEntityObjectNotFound(
      Exception e, HttpServletRequest request) {

    return ErrorResponse.buildErrorResponse(
        HttpStatus.NOT_FOUND, "Resource Not Found", List.of(e.getMessage()), request);
  }

  @ExceptionHandler(NoSuchElementException.class)
  public ResponseEntity<Object> handleItemNotFoundException(
      NoSuchElementException e, HttpServletRequest request) {

    return ErrorResponse.buildErrorResponse(
        HttpStatus.NOT_FOUND, "Resource Not Found", List.of(e.getMessage()), request);
  }

//   @ExceptionHandler(Exception.class)
  public ResponseEntity<Object> handleAllUncaughtException(
      Exception e, HttpServletRequest request) {

    return ErrorResponse.buildErrorResponse(
        HttpStatus.INTERNAL_SERVER_ERROR,
        "Internal Server Error",
        List.of(e.getMessage()),
        request);
  }
}
