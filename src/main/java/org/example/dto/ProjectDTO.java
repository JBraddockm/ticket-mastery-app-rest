package org.example.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.annotation.ValidDueDate;
import org.example.enums.Status;
import org.example.model.Task;
import org.springframework.format.annotation.DateTimeFormat;

@NoArgsConstructor
@AllArgsConstructor
@Data
@ValidDueDate(
        startDate = "projectStartDate",
        endDate = "projectEndDate",
        message = "Choose a valid due date"
)
public class ProjectDTO {

    Long id;

    @NotBlank(
            message = "Add project name"
    )
    private String projectName;

    @NotBlank(
            message = "Add a project code"
    )
    private String projectCode;

    @NotNull(
            message = "Choose a manager"
    )
    private UserDTO projectManager;

    private Set<Task> tasks = new HashSet<>();

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "Choose a start date")
    private LocalDate projectStartDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "Choose a due date")
    private LocalDate projectEndDate;

    @NotBlank(
            message = "Add details about the project"
    )
    private String projectDetail;

    private Status projectStatus;

    private int completeTaskCounts;
    private int unfinishedTaskCounts;

}
