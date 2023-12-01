package org.example.controller;

import jakarta.validation.Valid;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import org.example.dto.ProjectDTO;
import org.example.dto.TaskDTO;
import org.example.dto.UserDTO;
import org.example.enums.Status;
import org.example.exception.TaskNotFoundException;
import org.example.exception.UserNotFoundException;
import org.example.model.Task;
import org.example.service.ProjectService;
import org.example.service.TaskService;
import org.example.service.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/task")
public class TaskController {

  private final TaskService taskService;
  private final UserService userService;
  private final ProjectService projectService;

  public TaskController(
      TaskService taskService, UserService userService, ProjectService projectService) {
    this.taskService = taskService;
    this.userService = userService;
    this.projectService = projectService;
  }

  @ModelAttribute("projects")
  public List<ProjectDTO> getAllProjects(@AuthenticationPrincipal UserDetails userDetails) {

    UserDTO manager =
        userService
            .findByUsername(userDetails.getUsername())
            .orElseThrow(() -> new UserNotFoundException(userDetails.getUsername()));
    return projectService.findAllByProjectManagerIs(manager);
  }

  @ModelAttribute("employees")
  public List<UserDTO> getAllEmployees() {
    return userService.findAllEmployees();
  }

  @GetMapping("all")
  public String viewAllTasks(Model model, @AuthenticationPrincipal UserDetails userDetails) {

    UserDTO manager =
        userService
            .findByUsername(userDetails.getUsername())
            .orElseThrow(() -> new UserNotFoundException(userDetails.getUsername()));

    // TODO Extract this to the project or task service.
    List<Task> tasksList =
        projectService.getCountedListOfProjectDTO(manager).stream()
            .map(ProjectDTO::getTasks)
            .flatMap(Set::stream)
            .toList();

    model.addAttribute("tasks", tasksList);
    return "task/list";
  }

  @GetMapping("/create")
  public String newCreateTask(Model model, TaskDTO task) {

    model.addAttribute("task", task);

    return "task/create";
  }

  @PostMapping("/create")
  public String newCreateTask(
      @Valid @ModelAttribute("task") TaskDTO task,
      BindingResult result,
      RedirectAttributes redirectAttributes,
      Model model) {

    if (result.hasErrors()) {
      model.addAttribute(
          "isValid", "bg-green-50 border-green-500 text-green-900 dark:border-green-400 ");
      return "task/create";
    } else {
      task.setAssignedDate(LocalDate.now());
      task.setStatus(Status.OPEN);

      taskService.save(task);

      // TODO Retrieve the task object from the database and get its ID. Currently, it is empty.
      redirectAttributes.addFlashAttribute("createdTask", task.getId());
    }

    return "redirect:/task/all";
  }

  // New
  @PostMapping("{taskID}/delete")
  public String newDeleteTask(
      @PathVariable("taskID") Long taskID, RedirectAttributes redirectAttributes) {

    TaskDTO taskDTO =
        taskService
            .findById(taskID)
            .orElseThrow(() -> new TaskNotFoundException(taskID.toString()));

    taskService.deleteById(taskID);

    redirectAttributes.addFlashAttribute("deletedTask", taskDTO.getId());

    return "redirect:/task/all";
  }

  @GetMapping("{taskID}/edit")
  public String newEditTask(@PathVariable("taskID") Long taskID, Model model) {

    TaskDTO updatedTask =
        taskService
            .findById(taskID)
            .orElseThrow(() -> new TaskNotFoundException(taskID.toString()));

    model.addAttribute("task", updatedTask);

    return "task/update";
  }

  @PostMapping("{taskID}/edit")
  public String newEditTask(
      @Valid @ModelAttribute("task") TaskDTO task,
      BindingResult result,
      @PathVariable("taskID") Long taskID,
      RedirectAttributes redirectAttributes,
      Model model) {

    if (result.hasErrors()) {
      model.addAttribute(
          "isValid", "bg-green-50 border-green-500 text-green-900 dark:border-green-400 ");
      return "task/update";
    }

    TaskDTO updatedTask =
        taskService
            .findById(taskID)
            .orElseThrow(() -> new TaskNotFoundException(taskID.toString()));

    if (task.getId().equals(taskID)) {
      taskService.update(task);
      redirectAttributes.addFlashAttribute("updatedTask", updatedTask.getId());
    }

    return "redirect:/task/all";
  }

  @GetMapping("/employee/pending-tasks")
  public String newEmployeePendingTasks(Model model) {
    // TODO This should only get tasks of a particular logged in employee after Spring Security.
    model.addAttribute("pendingTasks", taskService.findAllTasksByStatusIsNot(Status.COMPLETED));
    return "task/pending-tasks";
  }

  @GetMapping("/employee/{id}/edit")
  public String employeeEditTask(@PathVariable("id") Long id, Model model) {

    TaskDTO task =
        taskService.findById(id).orElseThrow(() -> new TaskNotFoundException(id.toString()));

    model.addAttribute("task", task);
    model.addAttribute("statuses", Status.values());

    return "task/status-update";
  }

  @PostMapping("/employee/{id}/edit")
  public String employeeUpdateTask(
      @Valid @ModelAttribute("task") TaskDTO task,
      BindingResult result,
      @PathVariable("id") Long id,
      RedirectAttributes redirectAttributes,
      Model model) {

    if (result.hasErrors()) {
      model.addAttribute(
          "isValid", "bg-green-50 border-green-500 text-green-900 dark:border-green-400 ");
      model.addAttribute("statuses", Status.values());
      return "task/status-update";
    }

    TaskDTO updatedTask =
        taskService.findById(id).orElseThrow(() -> new TaskNotFoundException(id.toString()));

    if (task.getId().equals(id)) {
      taskService.updateStatus(task);
      redirectAttributes.addFlashAttribute("updatedTask", updatedTask.getId());
    }

    return "redirect:/task/employee/pending-tasks";
  }

  @GetMapping("/employee/archive")
  public String employeeArchivedTasks(Model model) {
    model.addAttribute("completedTasks", taskService.findAllTasksByStatus(Status.COMPLETED));
    return "task/archive";
  }

  // task/employee/id/status
  //  @PostMapping("/employee/{id}/status")
  //  public String updateTaskStatus(
  //      @PathVariable("id") Long id, @RequestParam String status, Model model) {
  //    // Change the status.
  //
  //    taskService.findById(id).setStatus(Status.valueOf(status));
  //
  //    // Add the user selected status to the partial.
  //    model.addAttribute("status_selected", status);
  //
  //    // Add all statuses to the partial.
  //    model.addAttribute("statuses", Status.values());
  //
  //    return "fragments/partials/status-response :: status";
}
