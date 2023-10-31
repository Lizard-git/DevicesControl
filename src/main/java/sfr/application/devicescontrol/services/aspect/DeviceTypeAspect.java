package sfr.application.devicescontrol.services.aspect;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import sfr.application.devicescontrol.configs.properties.DeviceTypeMessagesProperties;
import sfr.application.devicescontrol.enums.TypeMessagesHistory;
import sfr.application.devicescontrol.services.HistoryService;

@Aspect
@AllArgsConstructor
@Component
@Slf4j
public class DeviceTypeAspect {

    private final HistoryService historyService;
    private final DeviceTypeMessagesProperties deviceTypeMessagesProperties;

    @Pointcut("execution(* sfr.application.devicescontrol.services.DeviceTypeService.save(String, String)) && args(deviceType, ipAddress)")
    public void deviceTypeAddExecution(String deviceType, String ipAddress) {}

    @AfterReturning("deviceTypeAddExecution(deviceType, ipAddress)")
    public void afterDeviceTypeAddAdvice(String deviceType, String ipAddress) {
        historyService.newHistory(
                deviceTypeMessagesProperties.getSuccessAdditionMessage(),
                ipAddress,
                TypeMessagesHistory.Info,
                deviceType
        );
        log.info(
                "Successfully added a new device type. IP: " + ipAddress +
                        ", type:" + deviceType
        );
    }
    @AfterThrowing(pointcut = "deviceTypeAddExecution(deviceType, ipAddress)", throwing = "exception")
    public void afterDeviceTypeAddThrowingAdvice(String deviceType, String ipAddress, Exception exception) {
        switch (exception.getMessage()) {
            case "Error. This type already exists." -> {
                log.warn("Failed addition of a new device type. This type already exists. IP: " + ipAddress +
                        ", address:" + deviceType);
                historyService.newHistory(deviceTypeMessagesProperties.getWarningAlreadyExistsMessage(), ipAddress, TypeMessagesHistory.Warning, deviceType);
            }
            default -> {
                log.error(
                        "Failed addition of a new device type. IP: " + ipAddress +
                                ", address:" + deviceType
                );
                historyService.newHistory(deviceTypeMessagesProperties.getErrorAddMessage(), ipAddress, TypeMessagesHistory.Error, deviceType);
            }
        }
    }
}


