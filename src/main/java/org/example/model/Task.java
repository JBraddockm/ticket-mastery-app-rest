package org.example.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.enums.Status;

import java.time.LocalDate;

@Entity
@Table(name = "tasks")
@NoArgsConstructor
@Data
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
