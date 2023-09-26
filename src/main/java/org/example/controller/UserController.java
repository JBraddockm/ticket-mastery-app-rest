package org.example.controller;

import org.example.dto.UserDTO;
import org.example.enums.Gender;
import org.example.service.RoleService;
import org.example.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Arrays;

@Controller
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final RoleService roleService;

    public UserController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("/create")
    public String createUser(Model model){
        model.addAttribute("genders", Arrays.stream(Gender.values()).toList());
        model.addAttribute("roles", roleService.findAll());
        model.addAttribute("user", new UserDTO());
        model.addAttribute("users", userService.findAll());
        return "user/create";
    }

    @PostMapping("/create")
    public String createUser(@ModelAttribute UserDTO userDTO){
        userService.save(userDTO);
        return "redirect:/user/create";
    }
}
