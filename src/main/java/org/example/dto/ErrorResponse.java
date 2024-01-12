package org.example.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.servlet.http.HttpServletRequest;
import java.time.Instant;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorResponse {
  public static ResponseEntity<Object> buildErrorResponse(
      HttpStatus httpStatus, String message, List<String> errors, HttpServletRequest request) {

    Map<String, Object> map = new LinkedHashMap<>();

    map.put("status", httpStatus.value());

    map.put("timestamp", Instant.now());

    map.put("message", message);

    if (errors.size() == 1) {
      map.put("error", errors.get(0));
    } else {
      map.put("error", errors);
    }

    map.put("path", request.getRequestURI());

    return new ResponseEntity<>(map, httpStatus);
  }
}
