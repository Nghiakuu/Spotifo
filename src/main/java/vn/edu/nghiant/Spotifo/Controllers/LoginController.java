package vn.edu.nghiant.Spotifo.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import vn.edu.nghiant.Spotifo.Entities.User;
import vn.edu.nghiant.Spotifo.Services.UserService;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class LoginController {

  @Autowired
  private UserService userService;
  @GetMapping("/login")
  public String showLoginForm() {
    return "login";
  }

  @GetMapping("/register")
  public String showRegistrationForm(ModelMap model) {
    model.addAttribute("user", new User());
    return "register";
  }

  @PostMapping("/register")
  public String registerUser(@ModelAttribute("user") User users, RedirectAttributes redirectAttributes) {
    try {
      if (!users.getPassword().equals(users.getConfirmPassword())) {
        redirectAttributes.addAttribute("error", "Mật khẩu xác nhận không khớp");
        return "redirect:/register";
      }
      userService.registerUser(users);
      redirectAttributes.addAttribute("registered", true);
      return "redirect:/login";
    } catch (RuntimeException e) {
      redirectAttributes.addAttribute("error", e.getMessage());
      return "redirect:/register";
    }
  }
}