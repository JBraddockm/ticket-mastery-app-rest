package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.enums.Status;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ProjectDTO {
    private String projectName;
    private String projectCode;
    private UserDTO projectManager;
    private LocalDate projectStartDate;
    private LocalDate projectEndDate;
    private String projectDetail;
    private Status projetStatus;
}
