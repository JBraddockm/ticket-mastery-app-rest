package org.example.service.impl;

import org.example.dto.ProjectDTO;
import org.example.dto.UserDTO;
import org.example.enums.Status;
import org.example.service.ProjectService;
import org.example.service.TaskService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ProjectServiceImpl extends AbstractMapService<ProjectDTO, String> implements ProjectService {

    private final TaskService taskService;

    public ProjectServiceImpl(TaskService taskService) {
        this.taskService = taskService;
    }

    @Override
    public ProjectDTO save(ProjectDTO projectDTO) {
        return super.save(projectDTO.getProjectCode(), projectDTO);
    }

    @Override
    public void saveAll(Map<String, ProjectDTO> map) {
        super.saveAll(map);
    }

    @Override
    public List<ProjectDTO> findAll() {
        return super.findAll();
    }

    @Override
    public void update(ProjectDTO projectDTO) {
        super.update(projectDTO.getProjectCode(), projectDTO);
    }

    @Override
    public void deleteById(String projectCode) {
        super.deleteById(projectCode);
    }

    @Override
    public ProjectDTO findById(String projectCode) {
        return super.findById(projectCode);
    }

    @Override
    public void complete(ProjectDTO projectDTO) {
        //TODO Remove findByID check from here or Controller.
        super.findById(projectDTO.getProjectCode()).setProjetStatus(Status.COMPLETED);
    }

    @Override
    public List<ProjectDTO> findAllNonCompletedProjects() {
        return super.findAll().stream()
                .filter(projectDTO -> !projectDTO.getProjetStatus().equals(Status.COMPLETED))
                .toList();
    }

    @Override
    public List<ProjectDTO> getCountedListOfProjectDTO(UserDTO manager) {
        return super.findAll().stream()
                .filter(projectDTO -> projectDTO.getProjectManager().equals(manager))
                .map(projectDTO -> {

                    // Completed Tasks
                    int completeTaskCounts = (int) taskService.partitionTasksByStatusAndByManager(manager).get(true).stream()
                            .filter(taskDTO -> taskDTO.getProject().equals(projectDTO))
                            .count();

                    // Unfinished Tasks
                    int unfinishedTaskCounts = (int) taskService.partitionTasksByStatusAndByManager(manager).get(false).stream()
                            .filter(taskDTO -> taskDTO.getProject().equals(projectDTO))
                            .count();

                    projectDTO.setCompleteTaskCounts(completeTaskCounts);
                    projectDTO.setUnfinishedTaskCounts(unfinishedTaskCounts);

                    return projectDTO;

                })
                .toList();
    }
}
