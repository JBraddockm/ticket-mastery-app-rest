package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.enums.Status;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ProjectDTO {
    private String projectName;
    private String projectCode;
    private UserDTO projectManager;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate projectStartDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate projectEndDate;
    private String projectDetail;
    private Status projetStatus;

    private int completeTaskCounts;
    private int unfinishedTaskCounts;

    public ProjectDTO(
            String projectName,
            String projectCode,
            UserDTO projectManager,
            LocalDate projectStartDate,
            LocalDate projectEndDate,
            String projectDetail,
            Status projetStatus) {
        this.projectName = projectName;
        this.projectCode = projectCode;
        this.projectManager = projectManager;
        this.projectStartDate = projectStartDate;
        this.projectEndDate = projectEndDate;
        this.projectDetail = projectDetail;
        this.projetStatus = projetStatus;
    }
}
