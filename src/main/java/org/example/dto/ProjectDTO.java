package org.example.dto;

import com.fasterxml.jackson.annotation.*;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.annotation.ValidDueDate;
import org.example.enums.Status;
import org.springframework.format.annotation.DateTimeFormat;

@NoArgsConstructor
@AllArgsConstructor
@Data
@ValidDueDate(
    startDate = "projectStartDate",
    endDate = "projectEndDate",
    message = "Choose a valid due date")
@Schema(name = "Project")
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProjectDTO {

  @JsonProperty(access = JsonProperty.Access.READ_ONLY)
  @Schema(example = "1")
  private Long id;

  @NotBlank(message = "Add project name")
  @Schema(example = "Interface Projection in Spring Data JPA")
  private String projectName;

  @NotBlank(message = "Add a project code")
  @Schema(example = "PR003")
  private String projectCode;

  @NotNull(message = "Choose a manager")
  // TODO Use @JsonIncludeProperties to reduce payload.
  private UserDTO projectManager;

  @JsonIgnore
  // TODO Check out JsonBackReference.
  private List<TaskDTO> tasks = new ArrayList<>();

  @DateTimeFormat(pattern = "yyyy-MM-dd")
  @NotNull(message = "Choose a start date")
  private LocalDate projectStartDate;

  @DateTimeFormat(pattern = "yyyy-MM-dd")
  @NotNull(message = "Choose a due date")
  private LocalDate projectEndDate;

  @NotBlank(message = "Add details about the project")
  @Schema(example = "Your project details")
  private String projectDetail;

  private Status projectStatus;

  @JsonProperty(access = JsonProperty.Access.READ_ONLY)
  private int completedTaskCount;

  @JsonProperty(access = JsonProperty.Access.READ_ONLY)
  private int pendingTaskCount;

  public int getCompletedTaskCount() {
    return getProjectStatusByTask().get(true).size();
  }

  public int getPendingTaskCount() {
    return getProjectStatusByTask().get(false).size();
  }

  private Map<Boolean, List<TaskDTO>> getProjectStatusByTask() {
    return this.getTasks().stream()
        .collect(
            Collectors.partitioningBy(taskDTO -> taskDTO.getStatus().equals(Status.COMPLETED)));
  }
}
