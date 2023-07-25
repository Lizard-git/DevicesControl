package sfr.application.devicescontrol.controllers.settings_controllers;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@AllArgsConstructor
@RequestMapping(value = "/settings/devices")
public class DevicesSettingsController {
    @GetMapping(value = {"", "/"})
    public String PageDevice() {
        return "settings/settings-devices";
    }
}
