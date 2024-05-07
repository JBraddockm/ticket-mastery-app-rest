package org.example.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TaskProcessDTO {

  @JsonIgnore private Long id;

  @Schema(example = "PR001")
  @NotNull(message = "Choose a project")
  private String projectCode;

  @NotNull(message = "Choose an employee")
  @Schema(example = "jamesbrook@example.com")
  private String assignedEmployee;

  @NotBlank(message = "Add a task title")
  @Schema(example = "Caching with Hibernate")
  private String taskSubject;

  @NotBlank(message = "Add task details")
  @Schema(
      example =
          "Caching in Hibernate refers to the technique of storing frequently accessed data in memory to improve the performance of an application that uses Hibernate as an Object-Relational Mapping (ORM) framework.")
  private String taskDetail;

  @Schema(example = "2024-01-01")
  @NotNull(message = "Add an assigned date")
  private LocalDate assignedDate;
}
