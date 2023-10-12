package org.example.controller;

import jakarta.validation.Valid;
import org.example.dto.RoleDTO;
import org.example.dto.UserDTO;
import org.example.enums.Gender;
import org.example.exception.UserNotFoundException;
import org.example.service.RoleService;
import org.example.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final RoleService roleService;

    public UserController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @ModelAttribute("genders")
    public List<Gender> getGenders() {
        return Arrays.stream(Gender.values()).toList();
    }

    @ModelAttribute("roles")
    public List<RoleDTO> getRoles() {
        return roleService.findAll();
    }

    // New
    @GetMapping("/all")
    public String viewAllUsers(Model model) {
        model.addAttribute("users", userService.findAll());
        return "user/new-list";
    }

    @GetMapping("/new-create")
    public String newCreateUser(UserDTO user, Model model) {

        model.addAttribute("user", user);

        return "user/new-create";
    }

    @PostMapping("/new-create")
    public String newCreateUser(@Valid @ModelAttribute("user") UserDTO user, BindingResult result, RedirectAttributes redirectAttributes, Model model) {

        if (result.hasErrors()) {
            model.addAttribute("isValid", "bg-green-50 border-green-500 text-green-900 dark:border-green-400 ");
            return "user/new-create";
        } else {
            userService.save(user);
            redirectAttributes.addFlashAttribute("createdUser",user.getUserName());
        }

        return "redirect:/user/all";
    }

    @PostMapping("{username}/new-delete")
    public String newDeleteUser(@PathVariable("username") String username, RedirectAttributes redirectAttributes) {

        // TODO findByID should return Optional<T>.
        UserDTO user = Optional.ofNullable(userService.findById(username))
                .orElseThrow(() -> new UserNotFoundException(username));

        userService.deleteById(username);

        redirectAttributes.addFlashAttribute("deletedUserName", user.getUserName());

        return "redirect:/user/all";
    }

    @GetMapping("{username}/new-edit")
    public String newEditUser(@PathVariable("username") String username, Model model) {
        // TODO findByID should return Optional<T>.
        UserDTO user = Optional.ofNullable(userService.findById(username))
                .orElseThrow(() -> new UserNotFoundException(username));

        model.addAttribute("user", user);

        return "user/new-update";
    }

    @PostMapping("{username}/new-edit")
    public String newEditUser(@Valid @ModelAttribute("user") UserDTO user, BindingResult result, @PathVariable("username") String username, Model model, RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            model.addAttribute("isValid", "bg-green-50 border-green-500 text-green-900 dark:border-green-400 ");
            return "user/new-update";
        }

        UserDTO updatedUser = Optional.ofNullable(userService.findById(username))
                .orElseThrow(() -> new UserNotFoundException(username));


        if (user.getUserName().equals(updatedUser.getUserName())) {
            userService.update(updatedUser);
            redirectAttributes.addFlashAttribute("updatedUserName", updatedUser.getUserName());
        } else {
            redirectAttributes.addFlashAttribute("updateError", "Error Message");
        }

        return "redirect:/user/all";
    }
}
