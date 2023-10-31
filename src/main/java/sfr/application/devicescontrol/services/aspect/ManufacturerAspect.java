package sfr.application.devicescontrol.services.aspect;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import sfr.application.devicescontrol.configs.properties.ManufacturerMessagesProperties;
import sfr.application.devicescontrol.enums.TypeMessagesHistory;
import sfr.application.devicescontrol.services.HistoryService;

@Aspect
@AllArgsConstructor
@Component
@Slf4j
public class ManufacturerAspect {

    private final HistoryService historyService;
    private final ManufacturerMessagesProperties manufacturerMessagesProperties;

    // manufacturer
    @Pointcut("execution(* sfr.application.devicescontrol.services.ManufacturerService.save(String, String)) && args(name, ipAddress)")
    public void manufacturerAddExecution(String name, String ipAddress) {}

    @AfterReturning("manufacturerAddExecution(name, ipAddress)")
    public void afterManufacturerAddAdvice(String name, String ipAddress) {
        historyService.newHistory(
                manufacturerMessagesProperties.getSuccessAdditionMessage(),
                ipAddress,
                TypeMessagesHistory.Info,
                name
        );
        log.info("User add a new manufacturer. ip: " + ipAddress + ", name manufacturer:" + name);
    }

    @AfterThrowing(pointcut = "manufacturerAddExecution(name, ipAddress)", throwing = "exception")
    public void afterThrowingManufacturerAddAdvice(String name, String ipAddress, Exception exception) {
        switch (exception.getMessage()) {
            case "Manufacturer already exists." -> {
                log.warn(
                        "The user was unable to add a new manufacturer. This manufacturer already exists. IP: " + ipAddress +
                                ", manufacturer:" + name
                );
                historyService.newHistory(manufacturerMessagesProperties.getWarningAlreadyExistsMessage(), ipAddress, TypeMessagesHistory.Warning, name);
            }
            default -> {
                log.info("The user was unable to add a new manufacturer. IP: " + ipAddress + ", name manufacturer:" + name);
                historyService.newHistory(manufacturerMessagesProperties.getErrorAddMessage(), ipAddress, TypeMessagesHistory.Error, name);
            }
        }

    }
}


