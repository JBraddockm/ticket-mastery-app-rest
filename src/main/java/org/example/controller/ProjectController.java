package org.example.controller;

import jakarta.validation.Valid;
import java.util.List;
import org.example.dto.ProjectDTO;
import org.example.dto.UserDTO;
import org.example.enums.Status;
import org.example.exception.ProjectNotFoundException;
import org.example.exception.UserNotFoundException;
import org.example.service.ProjectService;
import org.example.service.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
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

    model.addAttribute("project", project);

    return "project/create";
  }

  @PostMapping("/create")
  public String newCreateProject(
      @Valid @ModelAttribute("project") ProjectDTO project,
      BindingResult result,
      Model model,
      RedirectAttributes redirectAttributes) {

    if (result.hasErrors()) {
      model.addAttribute(
          "isValid", "bg-green-50 border-green-500 text-green-900 dark:border-green-400 ");
      return "project/create";
    } else {
      // TODO ProjectCode has to be unique. Check to validate.
      projectService.save(project);
      redirectAttributes.addFlashAttribute("createdProject", project.getProjectCode());
    }

    return "redirect:/project/all";
  }

  @PostMapping("{projectCode}/delete")
  public String newDeleteProject(
      @PathVariable("projectCode") String projectCode, RedirectAttributes redirectAttributes) {

    ProjectDTO deletedProject =
        projectService
            .findByProjectCode(projectCode)
            .orElseThrow(() -> new ProjectNotFoundException(projectCode));

    if (deletedProject.getProjectCode().equals(projectCode)) {
      projectService.deleteByProjectCode(projectCode);
      redirectAttributes.addFlashAttribute("deletedProject", deletedProject.getProjectCode());
    }

    return "redirect:/project/all";
  }

  @GetMapping("{projectCode}/edit")
  public String newUpdateProject(@PathVariable("projectCode") String projectCode, Model model) {

    ProjectDTO project =
        projectService
            .findByProjectCode(projectCode)
            .orElseThrow(() -> new ProjectNotFoundException(projectCode));

    model.addAttribute("project", project);

    return "project/update";
  }

  @PostMapping("{projectCode}/edit")
  public String newUpdateProject(
      @Valid @ModelAttribute("project") ProjectDTO project,
      BindingResult result,
      @PathVariable("projectCode") String projectCode,
      RedirectAttributes redirectAttributes,
      Model model) {

    if (result.hasErrors()) {
      model.addAttribute(
          "isValid", "bg-green-50 border-green-500 text-green-900 dark:border-green-400 ");
      return "project/update";
    }

    ProjectDTO updatedProject =
        projectService
            .findByProjectCode(projectCode)
            .orElseThrow(() -> new ProjectNotFoundException(projectCode));

    if (project.getProjectCode().equals(projectCode)) {
      projectService.update(project);
      redirectAttributes.addFlashAttribute("updatedProject", updatedProject.getProjectCode());
    }

    return "redirect:/project/all";
  }

  @PostMapping("{projectCode}/complete")
  public String newCompleteProject(
      @PathVariable("projectCode") String projectCode, RedirectAttributes redirectAttributes) {

    ProjectDTO completedProject =
        projectService
            .findByProjectCode(projectCode)
            .orElseThrow(() -> new ProjectNotFoundException(projectCode));

    if (completedProject.getProjectCode().equals(projectCode)) {
      projectService.complete(completedProject);
      redirectAttributes.addFlashAttribute("completedProject", completedProject.getProjectCode());
    }

    return "redirect:/project/all";
  }

  @PostMapping("/manager/{projectCode}/complete")
  public String newManagerCompleteProject(
      @PathVariable("projectCode") String projectCode, RedirectAttributes redirectAttributes) {

    ProjectDTO completedProject =
        projectService
            .findByProjectCode(projectCode)
            .orElseThrow(() -> new ProjectNotFoundException(projectCode));

    projectService.complete(completedProject);
    redirectAttributes.addFlashAttribute("completedProject", completedProject.getProjectCode());

    return "redirect:/project/manager/project-status";
  }

  @GetMapping("/manager/project-status")
  public String newGetProjectByManager(
      Model model, @AuthenticationPrincipal UserDetails userDetails) {

    UserDTO manager =
        userService
            .findByUsername(userDetails.getUsername())
            .orElseThrow(
                () ->
                    new UserNotFoundException(userDetails.getUsername()));

    model.addAttribute("projects", projectService.getCountedListOfProjectDTO(manager));

    return "manager/project-status";
  }
}
