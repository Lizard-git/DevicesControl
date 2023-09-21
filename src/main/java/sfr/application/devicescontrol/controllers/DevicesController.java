package sfr.application.devicescontrol.controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import sfr.application.devicescontrol.dto.DeviceDTO;
import sfr.application.devicescontrol.entities.telbook.devices_control.DeviceEntity;
import sfr.application.devicescontrol.entities.telbook.prov_ter_org.UsersTelbookEntity;
import sfr.application.devicescontrol.enums.TypeEntity;
import sfr.application.devicescontrol.exceptions.DeviceException;
import sfr.application.devicescontrol.services.*;
import sfr.application.devicescontrol.utils.UtilsMethods;

import java.net.UnknownHostException;
import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping(value = "/devices")
public class DevicesController {

    private final AddressService addressService;
    private final DeviceService deviceService;
    private final UserTelbookService userTelbookService;
    private final StatusService statusService;
    private final ManufacturerService manufacturerService;
    private final DeviceTypeService deviceTypeService;
    @GetMapping(value = {"/all"})
    public String PageAllDevice() {
        return "devices/all-devices";
    }

    @GetMapping(value = {"/new"})
    public String PageNewDevice(
            @RequestParam(required = false, name = "successfully", defaultValue = "false") Boolean successfully,
            Model model
    ) {
        model.addAttribute("Error", "");
        model.addAttribute("Successfully", successfully);
        model.addAttribute("Device", new DeviceDTO());
        model.addAttribute("AllUsersTelbookByDepartmen", userTelbookService.getAll());
        return "devices/new-device";
    }

    @PostMapping(value = {"/new"})
    public String NewDevice(
            HttpServletRequest request,
            @ModelAttribute(name = "Device") @Valid DeviceDTO deviceDTO,
            BindingResult bindingResult,
            Model model
    ) throws UnknownHostException, DeviceException {
        if (bindingResult.hasErrors()) {
            model.addAttribute("Error", "");
            return "devices/new-device";
        }
        String ip = UtilsMethods.ipAddressValidator(request.getRemoteAddr());
        deviceService.save(deviceDTO, ip);
        return "redirect:/devices/new?successfully=true";
    }

    @GetMapping(value = {"/get/{id}"})
    public String PageDevice(
            @PathVariable(name = "id") DeviceEntity device,
            @RequestParam(required = false, name = "successfully", defaultValue = "false") Boolean successfully,
            Model model
    ) {
        UsersTelbookEntity usersTelbook = userTelbookService.getByDomain(device.getUserUsing());
        if (!ObjectUtils.isEmpty(device.getUserUsing()) && ObjectUtils.isEmpty(usersTelbook)) {
            model.addAttribute("OldUserUsing", device.getUserUsing());
        }
        List<UsersTelbookEntity> userByDepartment;
        if (ObjectUtils.isEmpty(device.getUserUsing()) || ObjectUtils.isEmpty(usersTelbook)) {
            userByDepartment = userTelbookService.getAll();
        } else {
            userByDepartment = userTelbookService.getAllUserByDepartment(
                    deviceService.convert(device).getUserUsing().getDepartment()
            );
        }
        model.addAttribute("Error", "");
        model.addAttribute("Successfully", successfully);
        model.addAttribute("Device", deviceService.convert(device));
        model.addAttribute("AllUsersTelbookByDepartmen", userByDepartment);
        return "devices/device";
    }

    @PostMapping(value = {"/change/{id}"})
    public String ChangeDevice(
            HttpServletRequest request,
            @PathVariable(name = "id") DeviceEntity device,
            @ModelAttribute("Device") @Valid DeviceDTO deviceDTO,
            BindingResult bindingResult,
            Model model
    ) throws UnknownHostException, DeviceException {
        if (bindingResult.hasErrors()) {
            List<UsersTelbookEntity> userByDepartment;
            if (ObjectUtils.isEmpty(device.getUserUsing())) {
                userByDepartment = userTelbookService.getAll();
            } else {
                userByDepartment = userTelbookService.getAllUserByDepartment(
                       deviceService.convert(device).getUserUsing().getDepartment()
                );
            }
            model.addAttribute("AllUsersTelbookByDepartmen", userByDepartment);
            return "devices/device";
        }
        String ip = UtilsMethods.ipAddressValidator(request.getRemoteAddr());
        deviceService.change(deviceDTO, ip);
        return "redirect:/devices/get/" + device.getId() + "?successfully=true";
    }

    @GetMapping(value = {"/delete/{id}"})
    public String DeleteDevice(
            HttpServletRequest request,
            @PathVariable(name = "id") DeviceEntity device
    ) throws UnknownHostException, DeviceException {
        String ip = UtilsMethods.ipAddressValidator(request.getRemoteAddr());
        deviceService.delete(device, ip);
        return "redirect:/devices/all";
    }

    @RequestMapping(value = {"/error"})
    public String showPageErrors(@ModelAttribute("Error") String message, Model model) {
        model.addAttribute("Error", message);
        model.addAttribute("Successfully", false);
        model.addAttribute("Device", new DeviceDTO());
        model.addAttribute("AllUsersTelbookByDepartmen", userTelbookService.getAll());
        return "devices/new-device";
    }

    @ModelAttribute
    public void ModelFilling(Model model) {
        model.addAttribute("AllAddress", addressService.getAll());
        model.addAttribute("AllTypeDevice", deviceTypeService.getAll());
        model.addAttribute("AllDepartments", userTelbookService.getAllDepartment());
        model.addAttribute("AllManufacturer", manufacturerService.getAll());
        model.addAttribute("AllStatusDevise", statusService.getAllByType(TypeEntity.device));
        model.addAttribute("AllDeviceType", deviceTypeService.getAll());
    }
}
