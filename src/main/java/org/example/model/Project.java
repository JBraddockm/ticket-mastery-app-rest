package org.example.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.enums.Status;

import java.time.LocalDate;

@Entity
@Table(
        name = "projects",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "project_code_unique",
                        columnNames = "project_code"
                )
        }
)
@NoArgsConstructor
@Getter
@Setter
public class Project extends BaseEntity{

    private String projectName;

    @Column(
            name = "project_code"
    )
    private String projectCode;

    @ManyToOne(
            fetch = FetchType.LAZY
    )
    @JoinColumn(
            name = "manager_id",
            foreignKey = @ForeignKey(
                    name = "project_manager_id_fk"
            )
    )
    private User projectManager;

    private LocalDate projectStartDate;

    private LocalDate projectEndDate;

    private String projectDetail;

    @Enumerated(EnumType.STRING)
    private Status projectStatus;

}
