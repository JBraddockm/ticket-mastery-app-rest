package org.example.controller;

import jakarta.validation.Valid;
import org.example.dto.ProjectDTO;
import org.example.dto.UserDTO;
import org.example.enums.Status;
import org.example.exception.ProjectNotFoundException;
import org.example.service.ProjectService;
import org.example.service.UserService;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

//@Controller
@RequestMapping("/project")
public class ProjectController {

    private final ProjectService projectService;
    private final UserService userService;

    public ProjectController(ProjectService projectService, UserService userService) {
        this.projectService = projectService;
        this.userService = userService;
    }

    @ModelAttribute("status")
    private Status getStatusOpen() {
        return Status.OPEN;
    }

    @ModelAttribute("managers")
    private List<UserDTO> getAllManagers() {
        return userService.findAllManagers();
    }

    @GetMapping("all")
    private String viewAllProjects(Model model) {
        model.addAttribute("projects", projectService.findAll());
        return "project/list";
    }

    @GetMapping("/create")
    public String newCreateProject(Model model, ProjectDTO project) {

        project.setProjectStatus(Status.OPEN);
        model.addAttribute("project", project);

        return "project/create";
    }

    @PostMapping("/create")
    public String newCreateProject(@Valid @ModelAttribute("project") ProjectDTO project, BindingResult result, Model model, RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            model.addAttribute("isValid", "bg-green-50 border-green-500 text-green-900 dark:border-green-400 ");
            return "project/create";
        } else {
            projectService.save(project);
            redirectAttributes.addFlashAttribute("createdProject", project.getProjectCode());
        }

        return "redirect:/project/all";
    }

    @PostMapping("{projectCode}/delete")
    public String newDeleteProject(@PathVariable("projectCode") String projectCode, RedirectAttributes redirectAttributes){

        // TODO findByID should return Optional<T>.
        ProjectDTO deletedProject = Optional.ofNullable(projectService.findById(projectCode))
                .orElseThrow(() -> new ProjectNotFoundException(projectCode));

        if (deletedProject.getProjectCode().equals(projectCode)) {
            projectService.deleteById(projectCode);
            redirectAttributes.addFlashAttribute("deletedProject", deletedProject.getProjectCode());
        }

        return "redirect:/project/all";
    }

    @GetMapping("{projectCode}/edit")
    public String newUpdateProject(@PathVariable("projectCode") String projectCode, Model model) {

        // TODO findByID should return Optional<T>.
        ProjectDTO project = Optional.ofNullable(projectService.findById(projectCode))
                .orElseThrow(() -> new ProjectNotFoundException(projectCode));

        model.addAttribute("project", project);

        return "project/update";
    }

    @PostMapping("{projectCode}/edit")
    public String newUpdateProject(@Valid @ModelAttribute("project") ProjectDTO project, BindingResult result, @PathVariable("projectCode") String projectCode, RedirectAttributes redirectAttributes, Model model) {

        if (result.hasErrors()) {
            model.addAttribute("isValid", "bg-green-50 border-green-500 text-green-900 dark:border-green-400 ");
            return "project/update";
        }

        // TODO findByID should return Optional<T>.
        ProjectDTO updatedProject = Optional.ofNullable(projectService.findById(projectCode))
                .orElseThrow(() -> new ProjectNotFoundException(projectCode));

        if (updatedProject.getProjectCode().equals(project.getProjectCode())) {
            projectService.update(project);
            redirectAttributes.addFlashAttribute("updatedProject", project.getProjectCode());
        }

        return "redirect:/project/all";
    }

    @PostMapping("{projectCode}/complete")
    public String newCompleteProject(@PathVariable("projectCode") String projectCode, RedirectAttributes redirectAttributes){

        // TODO findByID should return Optional<T>.
        ProjectDTO completedProject = Optional.ofNullable(projectService.findById(projectCode))
                .orElseThrow(() -> new ProjectNotFoundException(projectCode));

        if (completedProject.getProjectCode().equals(projectCode)) {
            projectService.complete(completedProject);
            redirectAttributes.addFlashAttribute("completedProject", completedProject.getProjectCode());
        }

        return "redirect:/project/all";

    }

    @PostMapping("/manager/{projectCode}/complete")
    public String newManagerCompleteProject(@PathVariable("projectCode") String projectCode, RedirectAttributes redirectAttributes) {

        // TODO findByID should return Optional<T>.
        ProjectDTO completedProject = Optional.ofNullable(projectService.findById(projectCode))
                .orElseThrow(() -> new ProjectNotFoundException(projectCode));

        projectService.complete(completedProject);
        redirectAttributes.addFlashAttribute("completedProject", completedProject.getProjectCode());

        return "redirect:/project/manager/project-status";
    }

    @GetMapping("/manager/project-status")
    public String newGetProjectByManager(Model model) {

        // TODO Log in will determine the manager after implementing Spring Security.
        UserDTO manager = userService.findByUserName("johnkelly@example.com").orElseThrow();

        model.addAttribute("projects", projectService.getCountedListOfProjectDTO(manager));

        return "manager/project-status";
    }

}
