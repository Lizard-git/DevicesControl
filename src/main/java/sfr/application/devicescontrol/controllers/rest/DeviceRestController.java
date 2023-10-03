package sfr.application.devicescontrol.controllers.rest;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import sfr.application.devicescontrol.entities.telbook.devices_control.DeviceEntity;
import sfr.application.devicescontrol.entities.telbook.devices_control.DeviceTypeEntity;
import sfr.application.devicescontrol.entities.telbook.devices_control.HistoryDeviceEntity;
import sfr.application.devicescontrol.enums.TypeEntity;
import sfr.application.devicescontrol.services.DeviceService;
import sfr.application.devicescontrol.services.HistoryDevicesService;
import sfr.application.devicescontrol.services.StatusService;

import java.util.List;
import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/api/v1/devices")
public class DeviceRestController {
    private final DeviceService deviceService;
    private final StatusService statusService;
    private final HistoryDevicesService historyDevicesService;
    @PostMapping(value = {"/get/count/by/status/{type}"})
    public @ResponseBody Map<String, Long> GetCountDeviceByStatusForDeviceType(
            @PathVariable(name = "type") DeviceTypeEntity type
    ) {
        return deviceService.getCountByStatusForDeviceType(type, statusService.getAllByType(TypeEntity.device));
    }

    @PostMapping(value = {"/get/all"})
    public @ResponseBody List<DeviceEntity> GetAllDevices() {
        return deviceService.getAll();
    }

    @PostMapping(value = {"/get/history/{id_device}"})
    public @ResponseBody List<HistoryDeviceEntity> historyDevice(@PathVariable(name = "id_device") DeviceEntity device)
    {
        return historyDevicesService.getAllByDevice(device);
    }

}
