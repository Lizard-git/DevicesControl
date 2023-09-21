package sfr.application.devicescontrol.controllers.settings_controllers;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import sfr.application.devicescontrol.exceptions.DeviceManufacturerException;
import sfr.application.devicescontrol.exceptions.DeviceTypeException;
import sfr.application.devicescontrol.services.DeviceTypeService;
import sfr.application.devicescontrol.services.ManufacturerService;
import sfr.application.devicescontrol.utils.UtilsMethods;

import java.net.UnknownHostException;

@Controller
@AllArgsConstructor
@RequestMapping(value = "/settings/devices")
public class DevicesSettingsController {
    private final DeviceTypeService deviceTypeService;
    private final ManufacturerService manufacturerService;
    @GetMapping(value = {"", "/"})
    public String PageDevice(
            @RequestParam(required = false, name = "successfully", defaultValue = "false") Boolean successfully,
            Model model
    ) {
        model.addAttribute("Successfully", successfully);
        return "settings/settings-devices";
    }

    @PostMapping(value = {"/type/new"})
    public String newDeviceType(
            HttpServletRequest request,
            @RequestParam(value = "name", defaultValue = "") String name,
            Model model
    ) throws UnknownHostException, DeviceTypeException {
        if (!name.isEmpty()) {
            String ip = UtilsMethods.ipAddressValidator(request.getRemoteAddr());
            deviceTypeService.save(name, ip);
            return "redirect:/settings/devices?successfully=true";
        }
        model.addAttribute("Error", "Вы не ввели тип устройства");
        model.addAttribute("Successfully", false);
        return "settings/settings-devices";
    }

    @PostMapping(value = {"/manufacturer/new"})
    public String newDeviceManufacturer(
            HttpServletRequest request,
            @RequestParam(value = "name", defaultValue = "") String name,
            Model model
    ) throws UnknownHostException, DeviceManufacturerException {
        if (!name.isEmpty()) {
            String ip = UtilsMethods.ipAddressValidator(request.getRemoteAddr());
            manufacturerService.save(name, ip);
            return "redirect:/settings/devices?successfully=true";
        }
        model.addAttribute("Error", "Вы не ввели имя производителя устройства");
        model.addAttribute("Successfully", false);
        return "settings/settings-devices";
    }

    @RequestMapping(value = {"/error"})
    public String showPageErrors(@ModelAttribute("Error") String message, Model model) {
        model.addAttribute("Error", message);
        model.addAttribute("Successfully", false);
        return "settings/settings-devices";
    }
}
