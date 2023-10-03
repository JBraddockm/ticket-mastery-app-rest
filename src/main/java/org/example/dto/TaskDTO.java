package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.enums.Status;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class TaskDTO {
    private Long id;
    private ProjectDTO project;
    private UserDTO assignedEmployee;
    private String taskSubject;
    private String taskDetail;
    private Status status;
    private LocalDate assignedDate;
}
