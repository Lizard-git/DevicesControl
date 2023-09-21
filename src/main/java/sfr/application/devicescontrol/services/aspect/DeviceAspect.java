package sfr.application.devicescontrol.services.aspect;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import sfr.application.devicescontrol.configs.properties.DeviceMessagesProperties;
import sfr.application.devicescontrol.dto.DeviceDTO;
import sfr.application.devicescontrol.entities.telbook.devices_control.DeviceEntity;
import sfr.application.devicescontrol.enums.TypeMessagesHistory;
import sfr.application.devicescontrol.services.HistoryService;

@Aspect
@AllArgsConstructor
@Component
@Slf4j
public class DeviceAspect {

    private final HistoryService historyService;
    private final DeviceMessagesProperties deviceMessagesProperties;

    @Pointcut("execution(* sfr.application.devicescontrol.services.DeviceService.save(sfr.application.devicescontrol.dto.DeviceDTO, String)) && args(deviceDTO, ipAddress)")
    public void deviceAddExecution(DeviceDTO deviceDTO, String ipAddress) {}

    @AfterReturning("deviceAddExecution(deviceDTO, ipAddress)")
    public void afterDeviceAddAdvice(DeviceDTO deviceDTO, String ipAddress) {
        String deviceStr = deviceDTO.getType().getName() + " " + deviceDTO.getInventoryNumber();
        historyService.newHistory(
                deviceMessagesProperties.getSuccessAdditionMessage(),
                ipAddress,
                TypeMessagesHistory.Info,
                deviceStr
        );
        log.info(
                "Successfully adding a new device. IP: " + ipAddress +
                        ", device:" + deviceStr
        );
    }

    @AfterThrowing(pointcut = "deviceAddExecution(deviceDTO, ipAddress)", throwing = "exception")
    public void afterAddressAddThrowingAdvice(DeviceDTO deviceDTO, String ipAddress, Exception exception) {
        String message;
        String deviceStr = deviceDTO.getType().getName() + " " + deviceDTO.getInventoryNumber();
        switch (exception.getMessage()) {
            case "Device already created." -> {
                message = deviceMessagesProperties.getWarningAlreadyExistsMessage();
                log.warn("The user was unable to add a new device. This device already exists. IP: " + ipAddress +
                        ", device:" + deviceStr);
            }
            default -> {
                log.error(
                        "The user was unable to add a new device. IP: " + ipAddress +
                                ", device:" + deviceStr
                );
                message = deviceMessagesProperties.getErrorAddMessage();
            }
        }
        historyService.newHistory(message, ipAddress, TypeMessagesHistory.Error, deviceStr);
    }

    @Pointcut("execution(* sfr.application.devicescontrol.services.DeviceService.change(sfr.application.devicescontrol.dto.DeviceDTO, String)) && args(deviceDTO, ipAddress)")
    public void deviceChangeExecution(DeviceDTO deviceDTO, String ipAddress) {}

    @AfterReturning("deviceChangeExecution(deviceDTO, ipAddress)")
    public void afterDeviceChangeAdvice(DeviceDTO deviceDTO, String ipAddress) {
        String deviceStr = deviceDTO.getType().getName() + " " + deviceDTO.getInventoryNumber();
        historyService.newHistory(
                deviceMessagesProperties.getSuccessChangeMessage(),
                ipAddress,
                TypeMessagesHistory.Info,
                deviceStr
        );
        log.info(
                "Successful change of device. IP: " + ipAddress +
                        ", device:" + deviceStr
        );
    }

    @AfterThrowing(pointcut = "deviceChangeExecution(deviceDTO, ipAddress)", throwing = "exception")
    public void afterDeviceChangeThrowingAdvice(DeviceDTO deviceDTO, String ipAddress, Exception exception) {
        String message;
        String deviceStr = deviceDTO.getType().getName() + " " + deviceDTO.getInventoryNumber();
        switch (exception.getMessage()) {
            case "Device already created." -> {
                message = deviceMessagesProperties.getWarningAlreadyExistsMessage();
                log.warn("The user was unable to change device. This device already exists. IP: " + ipAddress +
                        ", device:" + deviceStr);
            }
            default -> {
                log.error(
                        "The user was unable to change the device. IP: " + ipAddress +
                                ", device:" + deviceStr
                );
                message = deviceMessagesProperties.getErrorChangeMessage();
            }
        }
        historyService.newHistory(message, ipAddress, TypeMessagesHistory.Error, deviceStr);
    }

    @Pointcut("execution(* sfr.application.devicescontrol.services.DeviceService.delete(sfr.application.devicescontrol.entities.telbook.devices_control.DeviceEntity, String)) && args(device, ipAddress)")
    public void deviceDeleteExecution(DeviceEntity device, String ipAddress) {}

    @AfterReturning("deviceDeleteExecution(device, ipAddress)")
    public void afterDeviceDeleteAdvice(DeviceEntity device, String ipAddress) {
        String deviceStr = device.getType().getName() + " " + device.getInventoryNumber();
        historyService.newHistory(
                deviceMessagesProperties.getSuccessDeletedMessage(),
                ipAddress,
                TypeMessagesHistory.Info,
                deviceStr
        );
        log.info(
                "Successful delete of device. IP: " + ipAddress +
                        ", device:" + deviceStr
        );
    }

    @AfterThrowing(pointcut = "deviceDeleteExecution(device, ipAddress)", throwing = "exception")
    public void afterAddressDeleteThrowingAdvice(DeviceEntity device, String ipAddress, Exception exception) {
        String message;
        String deviceStr = device.getType().getName() + " " + device.getInventoryNumber();
        switch (exception.getMessage()) {
            default -> {
                log.error(
                        "The user was unable to delete the device. IP: " + ipAddress +
                                ", device:" + deviceStr
                );
                message = deviceMessagesProperties.getErrorDeleteMessage();
            }
        }
        historyService.newHistory(message, ipAddress, TypeMessagesHistory.Error, deviceStr);
    }
}


