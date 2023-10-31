package sfr.application.devicescontrol.controllers.settings_controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import sfr.application.devicescontrol.dto.SpecificationsTypeDto;
import sfr.application.devicescontrol.entities.telbook.devices_control.SpecificationsTypeEntity;
import sfr.application.devicescontrol.exceptions.DeviceManufacturerException;
import sfr.application.devicescontrol.exceptions.DeviceTypeException;
import sfr.application.devicescontrol.exceptions.SpecificationsTypeException;
import sfr.application.devicescontrol.services.DeviceTypeService;
import sfr.application.devicescontrol.services.ManufacturerService;
import sfr.application.devicescontrol.services.SpecificationsTypeService;

import java.net.UnknownHostException;

import static sfr.application.devicescontrol.utils.UtilsMethods.ipAddressValidator;

@Controller
@AllArgsConstructor
@RequestMapping(value = "/settings/devices")
public class DevicesSettingsController {
    private final DeviceTypeService deviceTypeService;
    private final ManufacturerService manufacturerService;
    private final SpecificationsTypeService specificationsTypeService;

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
            String ip = ipAddressValidator(request.getRemoteAddr());
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
            String ip = ipAddressValidator(request.getRemoteAddr());
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

    @PostMapping(value = {"/specifications/new"})
    public String addSpecificationType(
            HttpServletRequest request,
            @ModelAttribute("NewSpecification") @Valid SpecificationsTypeDto specificationsTypeDto,
            BindingResult bindingResult
    ) throws UnknownHostException, SpecificationsTypeException {
        if (bindingResult.hasErrors()) {
            return "settings/settings-devices";
        }
        String ip = ipAddressValidator(request.getRemoteAddr());
        specificationsTypeService.save(specificationsTypeDto, ip);
        return "redirect:/settings/devices?successfully=true";
    }

    @PostMapping(value = {"/specification/delete/{id}"})
    public String deleteSpecificationType(
            HttpServletRequest request,
            @PathVariable(name = "id") SpecificationsTypeEntity specificationsType
    ) throws UnknownHostException, SpecificationsTypeException {
        String ip = ipAddressValidator(request.getRemoteAddr());
        specificationsTypeService.delete(specificationsType, ip);
        return "redirect:/settings/devices?successfully=true";
    }

    @ModelAttribute
    public void ModelFilling(Model model) {
        model.addAttribute("NewSpecification", new SpecificationsTypeDto());
        model.addAttribute("AllSpecificationsType", specificationsTypeService.getAll());
        model.addAttribute("AllTypesDevices", deviceTypeService.getAll());
    }
}
