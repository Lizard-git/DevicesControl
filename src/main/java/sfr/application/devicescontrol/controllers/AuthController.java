package sfr.application.devicescontrol.controllers;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import sfr.application.devicescontrol.repositories.telbook.device_control.UserRepository;

@Controller
@AllArgsConstructor
public class AuthController {
    private final UserRepository userRepository;

    @RequestMapping("/login")
    public String login(
            @RequestParam(value = "error", required = false) String error,
            @RequestParam(value = "logout", required = false) String logout,
            Model model
    ) {
        model.addAttribute("error", error != null);
        model.addAttribute("logout", logout != null);
        model.addAttribute("AllUsers", userRepository.findAllByIsDeletedNull());
        return "login";
    }
}
