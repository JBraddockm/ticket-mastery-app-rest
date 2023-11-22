package org.example.repository;

import java.util.List;
import java.util.Optional;
import org.example.enums.Status;
import org.example.model.Project;
import org.example.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface ProjectRepository extends JpaRepository<Project, Long> {
    Optional<Project> findByProjectCode(String projectCode);

    List<Project> findAllByProjectStatusIsNot(Status status);

    List<Project> findAllByProjectManagerIs(User manager);

    @Transactional
    void deleteByProjectCode(String projectCode);
}
