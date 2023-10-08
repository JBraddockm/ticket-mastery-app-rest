package org.example.controller;

import org.example.dto.ProjectDTO;
import org.example.dto.UserDTO;
import org.example.enums.Status;
import org.example.exception.ProjectNotFoundException;
import org.example.service.ProjectService;
import org.example.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/project")
public class ProjectController {

    private final ProjectService projectService;
    private final UserService userService;

    public ProjectController(ProjectService projectService, UserService userService) {
        this.projectService = projectService;
        this.userService = userService;
    }

    @ModelAttribute("projects")
    private List<ProjectDTO> getAllProjects() {
        return projectService.findAll();
    }

    @ModelAttribute("status")
    private Status getStatusOpen() {
        return Status.OPEN;
    }

    @ModelAttribute("managers")
    private List<UserDTO> getAllManagers() {
        return userService.findAllManagers();
    }

    @GetMapping("/create")
    public String createProject(Model model, ProjectDTO projectDTO) {

        projectDTO.setProjetStatus(Status.OPEN);

        model.addAttribute("project", projectDTO);

        return "project/create";
    }

    @PostMapping("/create")
    public String createProject(@ModelAttribute ProjectDTO projectDTO, RedirectAttributes redirectAttributes) {

        projectService.save(projectDTO);

        redirectAttributes.addFlashAttribute("createdProject", projectDTO.getProjectCode());

        return "redirect:/project/create";
    }

    //TODO Deleting a project should delete associated tasks.
    @GetMapping("{projectID}/delete")
    public String deleteProject(@PathVariable("projectID") String projectID, RedirectAttributes redirectAttributes) {

        // TODO findByID should return Optional<T>.
        ProjectDTO projectDTO = Optional.ofNullable(projectService.findById(projectID))
                .orElseThrow(() -> new ProjectNotFoundException(projectID));

        projectService.deleteById(projectDTO.getProjectCode());

        redirectAttributes.addFlashAttribute("deletedProject",projectDTO.getProjectCode());

        return "redirect:/project/create";
    }

    @GetMapping("{projectID}/edit")
    public String updateProject(@PathVariable("projectID") String projectID, Model model) {

        // TODO findByID should return Optional<T>.
        ProjectDTO projectDTO = Optional.ofNullable(projectService.findById(projectID))
                .orElseThrow(() -> new ProjectNotFoundException(projectID));

        model.addAttribute("project", projectDTO);

        return "project/update";
    }

    @PostMapping("{projectID}/edit")
    public String updateProject(@ModelAttribute ProjectDTO projectDTO, @PathVariable("projectID") String projectID, RedirectAttributes redirectAttributes){

        // TODO findByID should return Optional<T>.
        ProjectDTO project = Optional.ofNullable(projectService.findById(projectID))
                .orElseThrow(() -> new ProjectNotFoundException(projectID));

        if(project.getProjectCode().equals(projectDTO.getProjectCode())){
            projectService.update(projectDTO);
            redirectAttributes.addFlashAttribute("updatedProject",project.getProjectCode());
        } else {
            redirectAttributes.addFlashAttribute("updateError", "Error Message");
        }

        return "redirect:/project/create";
    }

    @GetMapping("/{projectID}/complete")
    public String completeProject(@PathVariable("projectID") String projectID, RedirectAttributes redirectAttributes){

        // TODO findByID should return Optional<T>.
        ProjectDTO project = Optional.ofNullable(projectService.findById(projectID))
                .orElseThrow(() -> new ProjectNotFoundException(projectID));

        projectService.complete(project);

        redirectAttributes.addFlashAttribute("completedProject", project.getProjectCode());

        return "redirect:/project/create";
    }

    @GetMapping("/manager/{projectID}/complete")
    public String managerCompleteProject(@PathVariable("projectID") String projectID, RedirectAttributes redirectAttributes){

        // TODO findByID should return Optional<T>.
        ProjectDTO project = Optional.ofNullable(projectService.findById(projectID))
                .orElseThrow(() -> new ProjectNotFoundException(projectID));

        projectService.complete(project);
        redirectAttributes.addFlashAttribute("completedProject", project.getProjectCode());

        return "redirect:/project/manager/project-status";
    }

    @GetMapping("/manager/project-status")
    public String getProjectByManager(Model model){

        // TODO Log in will determine the manager after implementing Spring Security.
        UserDTO manager = userService.findById("johnkelly@example.com");

        model.addAttribute("projects", projectService.getCountedListOfProjectDTO(manager));

        return "manager/project-status";
    }

}
