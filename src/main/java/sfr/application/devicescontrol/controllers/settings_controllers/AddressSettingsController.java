package sfr.application.devicescontrol.controllers.settings_controllers;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@AllArgsConstructor
@RequestMapping(value = "/settings/address")
public class AddressSettingsController {
    @GetMapping(value = {"", "/"})
    public String PageAddress() {
        return "settings/settings-address";
    }
}
