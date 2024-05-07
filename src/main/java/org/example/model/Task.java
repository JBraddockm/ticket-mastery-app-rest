package org.example.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.enums.Status;

@Entity
@Table(name = "tasks")
@NoArgsConstructor
@Getter
@Setter
public class Task extends BaseEntity {

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(foreignKey = @ForeignKey(name = "task_project_id_fk"))
  private Project project;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(foreignKey = @ForeignKey(name = "task_employee_id_fk"))
  private User assignedEmployee;

  private String taskSubject;

  private String taskDetail;

  @Enumerated(EnumType.STRING)
  private Status status;

  @Column(columnDefinition = "DATE")
  private LocalDate assignedDate;
}
