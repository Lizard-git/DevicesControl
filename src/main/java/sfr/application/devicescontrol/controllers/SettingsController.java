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
    public String PageUser() {
        return "settings/settings-user";
    }

    @GetMapping(value = {"/address"})
    public String PageAddress() {
        return "settings/settings-address";
    }

    @GetMapping(value = {"/devices"})
    public String PageDevice() {
        return "settings/settings-devices";
    }

    @GetMapping(value = {"/consumable"})
    public String PageConsumable() {
        return "settings/settings-consumable";
    }
}
