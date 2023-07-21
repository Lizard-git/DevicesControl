package sfr.application.devicescontrol.controllers;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@AllArgsConstructor
@RequestMapping(value = "/settings")
public class SettingsController {
    @GetMapping(value = {"/users"})
    public String PageAllDevice() {
        return "settings/settings-user";
    }
}
