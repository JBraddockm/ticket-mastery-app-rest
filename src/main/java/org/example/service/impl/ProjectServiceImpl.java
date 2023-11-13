package org.example.service.impl;

import org.example.dto.ProjectDTO;
import org.example.dto.UserDTO;
import org.example.enums.Status;
import org.example.exception.ProjectNotFoundException;
import org.example.mapper.ProjectMapper;
import org.example.model.Project;
import org.example.repository.ProjectRepository;
import org.example.service.ProjectService;
import org.example.service.TaskService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProjectServiceImpl implements ProjectService {

    private final TaskService taskService;
    private final ProjectRepository projectRepository;
    private final ProjectMapper projectMapper;

    public ProjectServiceImpl(TaskService taskService, ProjectRepository projectRepository, ProjectMapper projectMapper) {
        this.taskService = taskService;
        this.projectRepository = projectRepository;
        this.projectMapper = projectMapper;
    }

    @Override
    public ProjectDTO save(ProjectDTO projectDTO) {

        projectDTO.setProjectStatus(Status.OPEN);

        Project project = projectMapper.convertToEntity(projectDTO);

        projectRepository.save(project);

        return projectMapper.convertToDTO(project);
    }

    public List<ProjectDTO> findAll() {
        return projectRepository.findAll().stream()
                .map(projectMapper::convertToDTO)
                .toList();
    }
    @Override
    public void update(ProjectDTO projectDTO) {

        Project project = projectRepository.findByProjectCode(projectDTO.getProjectCode())
                .orElseThrow(() -> new ProjectNotFoundException(projectDTO.getProjectCode()));

        Project updatedProject = projectMapper.convertToEntity(projectDTO);

        updatedProject.setId(project.getId());

        projectRepository.save(updatedProject);
    }
    @Override
    public void deleteByProjectCode(String projectCode) {
        projectRepository.deleteByProjectCode(projectCode);
    }

    public Optional<ProjectDTO> findByProjectCode(String projectCode) {
        return projectRepository.findByProjectCode(projectCode).map(projectMapper::convertToDTO);
    }

    @Override
    public void complete(ProjectDTO projectDTO) {
        projectRepository.findByProjectCode(projectDTO.getProjectCode())
                .ifPresent(project -> project.setProjectStatus(Status.COMPLETED));
    }

    @Override
    public List<ProjectDTO> findAllNonCompletedProjects() {
        return projectRepository.findAll().stream()
                .filter(projectDTO -> !projectDTO.getProjectStatus().equals(Status.COMPLETED))
                .map(projectMapper::convertToDTO)
                .toList();
    }

    @Override
    public List<ProjectDTO> getCountedListOfProjectDTO(UserDTO manager) {
        return null;
//        return projectRepository.findAll().stream()
//                .filter(projectDTO -> projectDTO.getProjectManager().equals(manager))
//                .map(projectDTO -> {
//
//                    // Completed Tasks
//                    int completeTaskCounts = (int) taskService.partitionTasksByStatusAndByManager(manager).get(true).stream()
//                            .filter(taskDTO -> taskDTO.getProject().equals(projectDTO))
//                            .count();
//
//                    // Unfinished Tasks
//                    int unfinishedTaskCounts = (int) taskService.partitionTasksByStatusAndByManager(manager).get(false).stream()
//                            .filter(taskDTO -> taskDTO.getProject().equals(projectDTO))
//                            .count();
//
//                    projectDTO.setCompleteTaskCounts(completeTaskCounts);
//                    projectDTO.setUnfinishedTaskCounts(unfinishedTaskCounts);
//
//                    return projectDTO;
//
//                })
//                .toList();
    }
}
