package sfr.application.devicescontrol.controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import sfr.application.devicescontrol.dto.AddressDto;
import sfr.application.devicescontrol.dto.DeviceDTO;
import sfr.application.devicescontrol.entities.telbook.devices_control.DeviceEntity;
import sfr.application.devicescontrol.entities.telbook.devices_control.UserEntity;
import sfr.application.devicescontrol.entities.telbook.prov_ter_org.UsersTelbookEntity;
import sfr.application.devicescontrol.enums.TypeEntity;
import sfr.application.devicescontrol.exceptions.DeviceException;
import sfr.application.devicescontrol.services.*;

import java.net.UnknownHostException;
import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping(value = "/devices")
public class DevicesController {
    private final HistoryService historyService;
    private final UserService userService;
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
    public String PageNewDevice(@RequestParam(
                                    required = false,
                                    name = "successfully",
                                    defaultValue = "false"
                                ) Boolean successfully,
                                Model model) {
        model.addAttribute("Successfully", successfully);
        model.addAttribute("Device", new DeviceDTO());
        model.addAttribute("AllUsersTelbookByDepartmen", userTelbookService.getAll());
        return "devices/new-device";
    }

    @PostMapping(value = {"/new"})
    public String NewDevice(HttpServletRequest request,
                            @ModelAttribute(name = "Device") @Valid DeviceDTO deviceDTO,
                            BindingResult bindingResult,
                            Model model) throws UnknownHostException, DeviceException {
        String clientIp = request.getRemoteAddr();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserEntity user = userService.getUserByLoginNotDeleted(auth.getName());
        if (bindingResult.hasErrors()) {
            return "devices/new-device";
        }
        deviceService.save(deviceDTO);
        historyService.newHistoryInfo(
                "Сохранил новое устройство с инвентарным номером: " + deviceDTO.getInventoryNumber(),
                clientIp,
                user
        );
        return "redirect:/devices/new?successfully=true";
    }

    @GetMapping(value = {"/get/{id}"})
    public String PageDevice(@PathVariable(name = "id") DeviceEntity device,
                             @RequestParam(
                                     required = false,
                                     name = "successfully",
                                     defaultValue = "false"
                             ) Boolean successfully,
                             Model model) {
        List<UsersTelbookEntity> userByDepartment;
        if (ObjectUtils.isEmpty(device.getUserUsing())) {
            userByDepartment = userTelbookService.getAll();
        } else {
            userByDepartment = userTelbookService.getAllUserByDepartment(
                    deviceService.convert(device).getUserUsing().getDepartment()
            );
        }
        model.addAttribute("Successfully", successfully);
        model.addAttribute("Device", deviceService.convert(device));
        model.addAttribute("AllUsersTelbookByDepartmen", userByDepartment);
        return "devices/device";
    }

    @PostMapping(value = {"/change/{id}"})
    public String AddNewDevice(HttpServletRequest request,
                               @PathVariable(name = "id") DeviceEntity device,
                               @ModelAttribute("Device") @Valid DeviceDTO deviceDTO,
                               BindingResult bindingResult,
                               Model model) throws UnknownHostException, DeviceException {
        String clientIp = request.getRemoteAddr();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserEntity user = userService.getUserByLoginNotDeleted(auth.getName());
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
        deviceService.change(deviceDTO);
        historyService.newHistoryInfo(
                "Изменил устройство с ID: " + device.getId(),
                clientIp,
                user
        );


        return "redirect:/devices/get/" + device.getId() + "?successfully=true";
    }

    @ExceptionHandler(DeviceException.class)
    public String DeviceException(DeviceException e, Model model) {
        String messageUI;
        switch (e.getMessage()) {
            case "Device save error !" -> messageUI = "Ошибка сохранения!";
            case "Device change error !" -> messageUI = "Ошибка изменения!";
            case "Device already created !" -> messageUI = "Ошибка ! такое устройство уже существует в программе !";
            case "Error deleted" -> messageUI = "Не удалось удалить устройство!";
            default -> messageUI = "Непредвиденная ошибка! Обратитесь к разработчикам!";
        }
        model.addAttribute("Error", messageUI);
        model.addAttribute("Successfully", false);
        model.addAttribute("AllAddress", addressService.getAllAddress());
        model.addAttribute("AllTypeDevice", deviceService.getAllTypesDevices());
        model.addAttribute("AllDepartments", userTelbookService.getAllDepartment());
        model.addAttribute("AllManufacturer", manufacturerService.getAllByType(TypeEntity.device));
        model.addAttribute("AllUsersTelbook", userTelbookService.getAll());
        model.addAttribute("AllStatusDevise", statusService.getAllByType(TypeEntity.device));

        return "devises/device";
    }

    @ExceptionHandler(UnknownHostException.class)
    public String UnknownHostException() {
        return "errors/error-ip";
    }

    @ModelAttribute
    public void ModelFilling(Model model) {
        model.addAttribute("Error", "");
        model.addAttribute("AllAddress", addressService.getAllAddress());
        model.addAttribute("AllTypeDevice", deviceService.getAllTypesDevices());
        model.addAttribute("AllDepartments", userTelbookService.getAllDepartment());
        model.addAttribute("AllManufacturer", manufacturerService.getAllByType(TypeEntity.device));
        model.addAttribute("AllStatusDevise", statusService.getAllByType(TypeEntity.device));
        model.addAttribute("AllDeviceType", deviceTypeService.getAll());
    }
}
