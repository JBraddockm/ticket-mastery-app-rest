package org.example.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

import lombok.*;
import org.example.enums.Status;

@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(
    value = {"hibernateLazyInitializer"},
    ignoreUnknown = true)
@Schema(name = "Task")
public class TaskDTO {

  @Schema(example = "1")
  @JsonProperty(access = JsonProperty.Access.READ_ONLY)
  private Long id;

  @NotNull(message = "Choose a project")
  @Schema(example = "Spring Boot Data JPA")
  private ProjectDTO project;

  @NotNull(message = "Choose an employee")
  private UserDTO assignedEmployee;

  @NotBlank(message = "Enter a task title")
  @Schema(example = "Caching with Hibernate")
  private String taskSubject;

  @NotBlank(message = "Add task details")
  @Schema(
      example =
          "Caching in Hibernate refers to the technique of storing frequently accessed data in memory to improve the performance of an application that uses Hibernate as an Object-Relational Mapping (ORM) framework.")
  private String taskDetail;

  private Status status;

  @Schema(example = "2024-01-01")
  private LocalDate assignedDate;
}
