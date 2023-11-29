package org.example.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping
public class LoginController {

  @GetMapping()
  public String root() {
    return "redirect:/welcome";
  }

  @GetMapping({"/login"})
  public String login(@AuthenticationPrincipal UserDetails userDetails) {

    if (userDetails == null) {
      return "login";
    } else {
      return "redirect:/";
    }
  }

  @GetMapping("/welcome")
  public String welcome() {
    return "welcome";
  }
}
