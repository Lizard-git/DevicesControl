package sfr.application.devicescontrol.services.aspect;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import sfr.application.devicescontrol.configs.properties.AddressMessagesProperties;
import sfr.application.devicescontrol.dto.AddressDto;
import sfr.application.devicescontrol.entities.telbook.devices_control.AddressEntity;
import sfr.application.devicescontrol.enums.TypeMessagesHistory;
import sfr.application.devicescontrol.services.HistoryService;

@Aspect
@AllArgsConstructor
@Component
@Slf4j
public class AddressAspect {

    private final HistoryService historyService;
    private final AddressMessagesProperties addressMessagesProperties;

    @Pointcut("execution(* sfr.application.devicescontrol.services.AddressService.save(sfr.application.devicescontrol.dto.AddressDto, String)) && args(address, ipAddress)")
    public void addressAddExecution(AddressDto address, String ipAddress) {}

    @AfterReturning("addressAddExecution(address, ipAddress)")
    public void afterAddressAddAdvice(AddressDto address, String ipAddress) {
        String addressStr = address.getSettlements().getName() + " " + address.getStreet() + " " + address.getHouse();
        historyService.newHistory(
                addressMessagesProperties.getSuccessAdditionMessage(),
                ipAddress,
                TypeMessagesHistory.Info,
                addressStr
        );
        log.info(
                "Successfully adding a new address. IP: " + ipAddress +
                        ", address:" + addressStr
        );
    }
    @AfterThrowing(pointcut = "addressAddExecution(address, ipAddress)", throwing = "exception")
    public void afterAddressAddThrowingAdvice(AddressDto address, String ipAddress, Exception exception) {
        String message;
        String addressStr = address.getSettlements().getName() + " " + address.getStreet() + " " + address.getHouse();
        switch (exception.getMessage()) {
            case "Address already exists." -> {
                message = addressMessagesProperties.getWarningAlreadyExistsMessage();
                log.warn("The user was unable to add a new address. This address already exists. IP: " + ipAddress +
                        ", address:" + addressStr);
            }
            default -> {
                log.error(
                        "The user was unable to add a new address. IP: " + ipAddress +
                                ", address:" + addressStr
                );
                message = addressMessagesProperties.getErrorAddMessage();
            }
        }
        historyService.newHistory(message, ipAddress, TypeMessagesHistory.Error, addressStr);
    }

    @Pointcut("execution(* sfr.application.devicescontrol.services.AddressService.change(sfr.application.devicescontrol.dto.AddressDto, String)) && args(address, ipAddress)")
    public void AddressChangeExecution(AddressDto address, String ipAddress) {}

    @AfterReturning("AddressChangeExecution(address, ipAddress)")
    public void afterAddressChangeAdvice(AddressDto address, String ipAddress) {
        String addressStr = address.getSettlements().getName() + " " + address.getStreet() + " " + address.getHouse();
        historyService.newHistory(
                addressMessagesProperties.getSuccessChangeMessage(),
                ipAddress,
                TypeMessagesHistory.Info,
                addressStr
        );
        log.info(
                "Successful change of address. IP: " + ipAddress +
                        ", address:" + addressStr
        );
    }
    @AfterThrowing(pointcut = "AddressChangeExecution(address, ipAddress)", throwing = "exception")
    public void afterAddressChangeThrowingAdvice(AddressDto address, String ipAddress, Exception exception) {
        String message;
        String addressStr = address.getSettlements().getName() + " " + address.getStreet() + " " + address.getHouse();
        switch (exception.getMessage()) {
            case "Address already exists." -> {
                message = addressMessagesProperties.getWarningAlreadyExistsMessage();
                log.warn("The user was unable to add a new address. This address already exists. IP: " + ipAddress +
                        ", address:" + addressStr);
            }
            case "Attempting to modify an entity that is not in the database." -> {
                message = addressMessagesProperties.getErrorChangeNonExistentEntry();
                log.error("The user was unable to change the address. An attempt to change an entity that does not exist. IP: " + ipAddress +
                        ", address:" + addressStr);
            }
            default -> {
                log.error(
                        "The user was unable to change the address. IP: " + ipAddress +
                                ", address:" + addressStr
                );
                message = addressMessagesProperties.getErrorChangeMessage();
            }
        }
        historyService.newHistory(message, ipAddress, TypeMessagesHistory.Error, addressStr);
    }

    @Pointcut("execution(* sfr.application.devicescontrol.services.AddressService.delete(sfr.application.devicescontrol.entities.telbook.devices_control.AddressEntity, String)) && args(address, ipAddress)")
    public void AddressDeleteExecution(AddressEntity address, String ipAddress) {}

    @AfterReturning("AddressDeleteExecution(address, ipAddress)")
    public void afterAddressDeleteAdvice(AddressEntity address, String ipAddress) {
        String addressStr = address.getSettlements().getName() + " " + address.getStreet() + " " + address.getHouse();
        historyService.newHistory(
                addressMessagesProperties.getSuccessDeletedMessage(),
                ipAddress,
                TypeMessagesHistory.Info,
                addressStr
        );
        log.info(
                "Successful delete of address. IP: " + ipAddress +
                        ", address:" + addressStr
        );
    }

    @AfterThrowing(pointcut = "AddressDeleteExecution(address, ipAddress)", throwing = "exception")
    public void afterAddressDeleteThrowingAdvice(AddressEntity address, String ipAddress, Exception exception) {
        String message;
        String addressStr = address.getSettlements().getName() + " " + address.getStreet() + " " + address.getHouse();
        switch (exception.getMessage()) {
            case "ailed to delete address. The entity is registered at the address." -> {
                message = addressMessagesProperties.getErrorSomethingIsIndicatedMessage();
                log.error("The user was unable to delete the address. An attempt to delete an address at which something is registered. IP: " + ipAddress +
                        ", address:" + addressStr);
            }
            default -> {
                log.error(
                        "The user was unable to delete the address. IP: " + ipAddress +
                                ", address:" + addressStr
                );
                message = addressMessagesProperties.getErrorDeleteMessage();
            }
        }
        historyService.newHistory(message, ipAddress, TypeMessagesHistory.Error, addressStr);
    }
}


