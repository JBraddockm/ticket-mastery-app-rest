package org.example.controller;

import org.example.dto.RoleDTO;
import org.example.dto.UserDTO;
import org.example.enums.Gender;
import org.example.exception.UserNotFoundException;
import org.example.service.RoleService;
import org.example.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
    public List<RoleDTO> getRoles(){
        return roleService.findAll();
    }

    @GetMapping("/create")
    public String createUser(Model model){
        model.addAttribute("user", new UserDTO());
        model.addAttribute("users", userService.findAll());
        return "user/create";
    }

    @PostMapping("/create")
    public String createUser(@ModelAttribute UserDTO userDTO, RedirectAttributes redirectAttributes){

        userService.save(userDTO);

        redirectAttributes.addFlashAttribute("createdUser",userDTO.getUserName());

        return "redirect:/user/create";
    }

    @GetMapping("{username}/delete")
    public String deleteUser(@PathVariable("username") String username, RedirectAttributes redirectAttributes){
        // TODO findByID should return Optional<T>.
        UserDTO user = Optional.ofNullable(userService.findById(username))
                .orElseThrow(() -> new UserNotFoundException(username));

        userService.deleteById(username);

        redirectAttributes.addFlashAttribute("deletedUser", user.getUserName());

        return "redirect:/user/create";
    }

    @GetMapping("{username}/edit")
    public String editUser(@PathVariable("username") String username, Model model){
        // TODO findByID should return Optional<T>.
        UserDTO user = Optional.ofNullable(userService.findById(username))
                .orElseThrow(() -> new UserNotFoundException(username));

        model.addAttribute("user", user);
        model.addAttribute("users", userService.findAll());

        return "user/update";
    }

    @PostMapping("{username}/edit")
    public String editUser(@ModelAttribute UserDTO userDTO, @PathVariable("username") String username, RedirectAttributes redirectAttributes){

        UserDTO user = Optional.ofNullable(userService.findById(username))
                .orElseThrow(() -> new UserNotFoundException(username));

        if(user.getUserName().equals(userDTO.getUserName())){
            userService.update(userDTO);
            redirectAttributes.addFlashAttribute("updatedUser",user.getUserName());
        } else {
            redirectAttributes.addFlashAttribute("updateError", "Error Message");
        }

        return "redirect:/user/create";
    }
}
