package org.example.controller;

import org.example.dto.ProjectDTO;
import org.example.dto.UserDTO;
import org.example.enums.Status;
import org.example.service.ProjectService;
import org.example.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

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
    private List<ProjectDTO> getAllProjects(){
        return projectService.findAll();
    }

    @ModelAttribute("status")
    private Status getAllStatus(){
        return Status.OPEN;
    }

    @ModelAttribute("managers")
    private List<UserDTO> getAllManagers(){
        return userService.findAllManagers();
    }
    @GetMapping("/create")
    public String createProject(Model model, ProjectDTO projectDTO){

        projectDTO.setProjetStatus(Status.OPEN);

        model.addAttribute("project", projectDTO);
        return "project/create";
    }

    @PostMapping("/create")
    public String createProject(@ModelAttribute ProjectDTO projectDTO, RedirectAttributes redirectAttributes){

        projectService.save(projectDTO);
        redirectAttributes.addFlashAttribute("createdProject", projectDTO.getProjectCode());

        return "redirect:/project/create";
    }
}
