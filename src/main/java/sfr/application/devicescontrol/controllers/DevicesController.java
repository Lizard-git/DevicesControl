package sfr.application.devicescontrol.controllers;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@AllArgsConstructor
@RequestMapping(value = "/devices")
public class DevicesController {
    @GetMapping(value = {"/all"})
    public String PageAllDevice() {
        return "devices/all-devices";
    }
}
