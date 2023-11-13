package org.example.service;

import org.example.dto.ProjectDTO;
import org.example.dto.UserDTO;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
public interface ProjectService{
    Optional<ProjectDTO> findByProjectCode(String projectCode);
    void deleteByProjectCode (String projectCode);
    ProjectDTO save(ProjectDTO projectDTO);
    void update(ProjectDTO projectDTO);
    void complete(ProjectDTO projectDTO);
    List<ProjectDTO> findAll();
    List<ProjectDTO> findAllNonCompletedProjects();
    List<ProjectDTO> getCountedListOfProjectDTO(UserDTO manager);
}
