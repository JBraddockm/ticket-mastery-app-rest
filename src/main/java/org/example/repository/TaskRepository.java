package org.example.repository;

import java.util.List;
import org.example.enums.Status;
import org.example.model.Task;
import org.example.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {

  List<Task> findTasksByStatusIs(Status status);

  List<Task> findTasksByStatusIsNot(Status status);

  List<Task> findAllByAssignedEmployee(User assignedEmployee);
}
