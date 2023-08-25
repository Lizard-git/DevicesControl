package sfr.application.devicescontrol.controllers.rest;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import sfr.application.devicescontrol.entities.telbook.devices_control.DeviceEntity;
import sfr.application.devicescontrol.entities.telbook.devices_control.DeviceTypeEntity;
import sfr.application.devicescontrol.entities.telbook.devices_control.StatusEntity;
import sfr.application.devicescontrol.entities.telbook.prov_ter_org.UsersTelbookEntity;
import sfr.application.devicescontrol.services.DeviceService;

import java.util.List;
import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/api/v1/devices")
public class DeviceRestController {
    private final DeviceService deviceService;

    @PostMapping(value = {"/get/count/by/status/{type}"})
    public @ResponseBody Map<String, Long> GetCountDeviceByStatusForDeviceType(
            @PathVariable(name = "type") DeviceTypeEntity type
    ) {
        return deviceService.getCountByStatusForDeviceType(type);
    }

    @PostMapping(value = {"/get/all"})
    public @ResponseBody List<DeviceEntity> GetAllDevices() {
        return deviceService.getAll();
    }

}
