package sfr.application.devicescontrol.controllers;

import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import sfr.application.devicescontrol.entities.telbook.devices_control.UserEntity;
import sfr.application.devicescontrol.entities.telbook.prov_ter_org.UsersTelbookEntity;
import sfr.application.devicescontrol.services.UserService;
import sfr.application.devicescontrol.services.UserTelbookService;

@Controller
@AllArgsConstructor
public class HomeController {
    private final UserService userService;
    private final UserTelbookService userTelbookService;
    @GetMapping(value = {"", "/", "/home"})
    public String PageHome(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserEntity user = userService.getUserByLoginNotDeleted(auth.getName());

        UsersTelbookEntity usersTelbook = userTelbookService.getByDomainAndTubNumber(user.getDomainName(), user.getTabNumber());

        model.addAttribute("AuthUser", user);
        model.addAttribute("AuthUserTelbook", usersTelbook);
        return "index";
    }
}
