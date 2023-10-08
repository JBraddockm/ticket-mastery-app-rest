package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.enums.Status;

import java.time.LocalDate;
import java.util.UUID;

@NoArgsConstructor
@Data
@AllArgsConstructor
public class TaskDTO {
    private Long id;
    private ProjectDTO project;
    private UserDTO assignedEmployee;
    private String taskSubject;
    private String taskDetail;
    private Status status;
    private LocalDate assignedDate;

    public TaskDTO(ProjectDTO project, UserDTO assignedEmployee, String taskSubject, String taskDetail, Status status, LocalDate assignedDate) {
        this.project = project;
        this.assignedEmployee = assignedEmployee;
        this.taskSubject = taskSubject;
        this.taskDetail = taskDetail;
        this.status = status;
        this.assignedDate = assignedDate;
        this.id = UUID.randomUUID().getMostSignificantBits();
    }
}
