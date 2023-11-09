package org.example.controller;

import jakarta.validation.Valid;
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
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

//@Controller
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

    @GetMapping("all")
    public String viewAllTasks(Model model){
        model.addAttribute("tasks", taskService.findAll());
        return "task/list";
    }

    
    @GetMapping("/create")
    public String newCreateTask(Model model, TaskDTO task){

        model.addAttribute("task", task);

        return "task/create";
    }

    @PostMapping("/create")
    public String newCreateTask(@Valid @ModelAttribute("task") TaskDTO task, BindingResult result, RedirectAttributes redirectAttributes, Model model){

        if(result.hasErrors()){
            model.addAttribute("isValid", "bg-green-50 border-green-500 text-green-900 dark:border-green-400 ");
            return "task/create";
        } else{
            // TODO Create another DTO with Java Record to complete the object before saving.
            task.setAssignedDate(LocalDate.now());
            task.setStatus(Status.OPEN);
            task.setId(UUID.randomUUID().getMostSignificantBits());

            taskService.save(task);

            redirectAttributes.addFlashAttribute("createdTask", task.getId());
        }

        return "redirect:/task/all";
    }
    
    // New
    @PostMapping("{taskID}/delete")
    public String newDeleteTask(@PathVariable("taskID") Long taskID, RedirectAttributes redirectAttributes){

        // TODO findByID should return Optional<T>.
        TaskDTO taskDTO = Optional.ofNullable(taskService.findById(taskID))
                .orElseThrow(() -> new TaskNotFoundException(taskID.toString()));

        taskService.deleteById(taskID);

        redirectAttributes.addFlashAttribute("deletedTask", taskDTO.getId());

        return "redirect:/task/all";
    }
    
    @GetMapping("{taskID}/edit")
    public String newEditTask(@PathVariable("taskID") Long taskID, Model model){

        // TODO findByID should return Optional<T>.
        TaskDTO updatedTask = Optional.ofNullable(taskService.findById(taskID))
                .orElseThrow(() -> new TaskNotFoundException(taskID.toString()));

        model.addAttribute("task", updatedTask);

        return "task/update";
    }

    @PostMapping("{taskID}/edit")
    public String newEditTask(@Valid @ModelAttribute("task") TaskDTO task, BindingResult result, @PathVariable("taskID") Long taskID, RedirectAttributes redirectAttributes, Model model){

        if (result.hasErrors()) {
            model.addAttribute("isValid", "bg-green-50 border-green-500 text-green-900 dark:border-green-400 ");
            return "task/update";
        }

        // TODO findByID should return Optional<T>.
        TaskDTO updatedTask = Optional.ofNullable(taskService.findById(taskID))
                .orElseThrow(() -> new TaskNotFoundException(taskID.toString()));

        if(task.getId().equals(updatedTask.getId())){
            taskService.update(task);
            redirectAttributes.addFlashAttribute("updatedTask",updatedTask.getId());
        }

        return "redirect:/task/all";
    }
    
    @GetMapping("/employee/pending-tasks")
    public String newEmployeePendingTasks(Model model){
        //TODO This should only get tasks of a particular logged in employee after Spring Security.
        model.addAttribute("pendingTasks", taskService.findAllTasksByStatusIsNot(Status.COMPLETED));
        return "task/pending-tasks";
    }
    
    @GetMapping("/employee/{id}/edit")
    public String employeeEditTask(@PathVariable("id") Long id, Model model) {

        model.addAttribute("task", taskService.findById(id));
        model.addAttribute("statuses", Status.values());

        return "task/status-update";

    }

    @PostMapping("/employee/{id}/edit")
    public String employeeUpdateTask(@Valid @ModelAttribute("task") TaskDTO task, BindingResult result, @PathVariable("id") Long id, RedirectAttributes redirectAttributes, Model model) {

        if(result.hasErrors()){
            model.addAttribute("isValid", "bg-green-50 border-green-500 text-green-900 dark:border-green-400 ");
            model.addAttribute("statuses", Status.values());
            return "task/status-update";
        }

        // TODO Check ID etc.
        taskService.updateStatus(task);
        redirectAttributes.addFlashAttribute("updatedTask",task.getId());

        return "redirect:/task/employee/pending-tasks";
    }
    
    @GetMapping("/employee/archive")
    public String employeeArchivedTasks(Model model) {
        model.addAttribute("completedTasks", taskService.findAllTasksByStatus(Status.COMPLETED));
        return "task/archive";
    }

    // task/employee/id/status
    @PostMapping("/employee/{id}/status")
    public String updateTaskStatus(@PathVariable("id") Long id, @RequestParam String status, Model model){
        // Change the status.
        taskService.findById(id).setStatus(Status.valueOf(status));

        // Add the user selected status to the partial.
        model.addAttribute("status_selected", status);

        // Add all statuses to the partial.
        model.addAttribute("statuses", Status.values());

        return "fragments/partials/status-response :: status";
    }
}
