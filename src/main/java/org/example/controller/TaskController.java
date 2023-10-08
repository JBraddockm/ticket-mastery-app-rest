package org.example.controller;

import org.example.dto.ProjectDTO;
import org.example.dto.TaskDTO;
import org.example.dto.UserDTO;
import org.example.enums.Status;
import org.example.exception.TaskNotFoundException;
import org.example.service.ProjectService;
import org.example.service.TaskService;
import org.example.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Controller
@RequestMapping("/task")
public class TaskController {

    private final TaskService taskService;
    private final UserService userService;
    private final ProjectService projectService;

    public TaskController(TaskService taskService, UserService userService, ProjectService projectService) {
        this.taskService = taskService;
        this.userService = userService;
        this.projectService = projectService;
    }

    // TODO This should normally return projects assigned to a particular manager.
    @ModelAttribute("projects")
    public List<ProjectDTO> getAllProjects(){
        return projectService.findAll();
    }

    @ModelAttribute("employees")
    public List<UserDTO> getAllEmployees(){
        return userService.findAllEmployees();
    }

    @ModelAttribute("tasks")
    public List<TaskDTO> getAllTasks(){
        return taskService.findAll();
    }

    @GetMapping("/create")
    public String createTask(Model model, TaskDTO taskDTO){

        model.addAttribute("task", taskDTO);

        return "task/create";
    }

    @PostMapping("/create")
    public String createTask(@ModelAttribute TaskDTO taskDTO, RedirectAttributes redirectAttributes){

        // TODO Create another DTO with Java Record to complete the object before saving.
        taskDTO.setAssignedDate(LocalDate.now());
        taskDTO.setStatus(Status.OPEN);
        taskDTO.setId(UUID.randomUUID().getMostSignificantBits());

        taskService.save(taskDTO);

        redirectAttributes.addFlashAttribute("createdTask", taskDTO.getId());

        return "redirect:/task/create";
    }

    @GetMapping("{taskID}/delete")
    public String deleteTask(@PathVariable("taskID") Long taskID, RedirectAttributes redirectAttributes){

        // TODO findByID should return Optional<T>.
        TaskDTO taskDTO = Optional.ofNullable(taskService.findById(taskID))
                .orElseThrow(() -> new TaskNotFoundException(taskID.toString()));

        taskService.deleteById(taskID);

        redirectAttributes.addFlashAttribute("deletedTask", taskDTO.getId());

        return "redirect:/task/create";
    }

    @GetMapping("{taskID}/edit")
    public String editTask(@PathVariable("taskID") Long taskID, Model model){

        // TODO findByID should return Optional<T>.
        TaskDTO taskDTO = Optional.ofNullable(taskService.findById(taskID))
                .orElseThrow(() -> new TaskNotFoundException(taskID.toString()));

        model.addAttribute("task", taskDTO);

        return "task/update";
    }

    @PostMapping("{taskID}/edit")
    public String editTask(@ModelAttribute TaskDTO taskDTO, @PathVariable("taskID") Long taskID, RedirectAttributes redirectAttributes){

        // TODO findByID should return Optional<T>.
        TaskDTO task = Optional.ofNullable(taskService.findById(taskID))
                .orElseThrow(() -> new TaskNotFoundException(taskID.toString()));

        if(task.getId().equals(taskDTO.getId())){
            taskService.update(taskDTO);
            redirectAttributes.addFlashAttribute("updatedTask",task.getId());
        } else {
            redirectAttributes.addFlashAttribute("updateError", "Error Message");
        }

        return "redirect:/task/create";
    }

    @GetMapping("/employee/pending-tasks")
    public String employeePendingTasks(Model model){
        //TODO This should only get tasks of a particular logged in employee after Spring Security.
        model.addAttribute("tasks", taskService.findAllTasksByStatusIsNot(Status.COMPLETED));
        return "task/pending-tasks";
    }

    @GetMapping("/employee/edit/{id}")
    public String employeeEditTask(@PathVariable("id") Long id, Model model) {

        model.addAttribute("task", taskService.findById(id));
        model.addAttribute("unFinishedTasks", taskService.findAllTasksByStatusIsNot(Status.COMPLETED));
        model.addAttribute("statuses", Status.values());

        return "task/status-update";

    }

    @PostMapping("/employee/update/{id}")
    public String employeeUpdateTask(TaskDTO taskDTO) {
        taskService.updateStatus(taskDTO);
        return "redirect:/task/employee/pending-tasks";
    }

    @GetMapping("/employee/archive")
    public String employeeArchivedTasks(Model model) {
        model.addAttribute("completedTasks", taskService.findAllTasksByStatus(Status.COMPLETED));
        return "task/archive";
    }
}
