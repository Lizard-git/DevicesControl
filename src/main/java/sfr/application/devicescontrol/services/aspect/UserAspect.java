package sfr.application.devicescontrol.services.aspect;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import sfr.application.devicescontrol.configs.properties.UserMessagesProperties;
import sfr.application.devicescontrol.dto.UserDto;
import sfr.application.devicescontrol.entities.telbook.devices_control.UserEntity;
import sfr.application.devicescontrol.enums.TypeMessagesHistory;
import sfr.application.devicescontrol.services.HistoryService;

@Aspect
@AllArgsConstructor
@Component
@Slf4j
public class UserAspect {
    private final HistoryService historyService;
    private final UserMessagesProperties userMessagesProperties;

    @Pointcut("execution(* sfr.application.devicescontrol.services.UserService.save(sfr.application.devicescontrol.dto.UserDto, String)) && args(userDto, ipAddress)")
    public void userAddExecution(UserDto userDto, String ipAddress) {}

    @AfterReturning("userAddExecution(userDto, ipAddress)")
    public void afterUserAddAdvice(UserDto userDto, String ipAddress) {
        historyService.newHistory(
                userMessagesProperties.getSuccessAdditionMessage(),
                ipAddress,
                TypeMessagesHistory.Info,
                userDto.getLogin()
        );
        log.info("Successfully adding a new user. IP: " + ipAddress +
                ", user:" + userDto.getLogin());
    }

    @AfterThrowing(pointcut = "userAddExecution(userDto, ipAddress)", throwing = "exception")
    public void afterUserAddThrowingAdvice(UserDto userDto, String ipAddress, Exception exception) {
        String message;
        switch (exception.getMessage()) {
            case "User already created." -> {
                message = userMessagesProperties.getWarningAlreadyExistsMessage();
                log.warn("The user was unable to add a new user. This user already exists. IP: " + ipAddress +
                        ", user:" + userDto.getLogin());
            }
            default -> {
                log.error("The user was unable to add a new user. IP: " + ipAddress +
                        ", user:" + userDto.getLogin());
                message = userMessagesProperties.getErrorAddMessage();
            }
        }
        historyService.newHistory(message, ipAddress, TypeMessagesHistory.Error, userDto.getLogin());
    }

    @Pointcut("execution(* sfr.application.devicescontrol.services.UserService.change(sfr.application.devicescontrol.dto.UserDto, String)) && args(userDto, ipAddress)")
    public void userChangeExecution(UserDto userDto, String ipAddress) {}

    @AfterReturning("userChangeExecution(userDto, ipAddress)")
    public void afterUserChangeAdvice(UserDto userDto, String ipAddress) {
        historyService.newHistory(
                userMessagesProperties.getSuccessChangeMessage(),
                ipAddress,
                TypeMessagesHistory.Info,
                userDto.getLogin()
        );
        log.info("The user successfully changed the user. IP: " + ipAddress +
                ", user:" + userDto.getLogin());
    }

    @AfterThrowing(pointcut = "userChangeExecution(userDto, ipAddress)", throwing = "exception")
    public void afterUserChangeThrowingAdvice(UserDto userDto, String ipAddress, Exception exception) {
        String message;
        switch (exception.getMessage()) {
            case "User already created." -> {
                message = userMessagesProperties.getWarningAlreadyExistsMessage();
                log.warn("The user was unable to change a new user. This user already exists. IP: " + ipAddress +
                        ", user:" + userDto.getLogin());
            }
            default -> {
                log.error("The user was unable to change a new user. IP: " + ipAddress +
                        ", user:" + userDto.getLogin());
                message = userMessagesProperties.getErrorAddMessage();
            }
        }
        historyService.newHistory(message, ipAddress, TypeMessagesHistory.Error, userDto.getLogin());
    }

    @Pointcut("execution(* sfr.application.devicescontrol.services.UserService.remove(sfr.application.devicescontrol.entities.telbook.devices_control.UserEntity, String)) && args(user, ipAddress)")
    public void userRemoveExecution(UserEntity user, String ipAddress) {}

    @AfterReturning("userRemoveExecution(user, ipAddress)")
    public void afterUserRemoveAdvice(UserEntity user, String ipAddress) {
        historyService.newHistory(
                userMessagesProperties.getSuccessRemovedMessage(),
                ipAddress,
                TypeMessagesHistory.Info,
                user.getLogin()
        );
        log.info("The user has successfully marked the user as deleted. (with this status the user will no longer be able to log in)." +
                " IP: " + ipAddress +
                ", user:" + user.getLogin());
    }

    @AfterThrowing(pointcut = "userRemoveExecution(user, ipAddress)", throwing = "exception")
    public void afterUserRemoveThrowingAdvice(UserEntity user, String ipAddress, Exception exception) {
        String message;
        switch (exception.getMessage()) {
            default -> {
                log.error("The user tried to specify the user status as deleted, but an error occurred. " +
                        "(with this status the user will no longer be able to log in) IP: " + ipAddress +
                        ", user:" + user.getLogin());
                message = userMessagesProperties.getErrorRemovedMessage();
            }
        }
        historyService.newHistory(message, ipAddress, TypeMessagesHistory.Error, user.getLogin());
    }

    @Pointcut("execution(* sfr.application.devicescontrol.services.UserService.delete(sfr.application.devicescontrol.entities.telbook.devices_control.UserEntity, String)) && args(user, ipAddress)")
    public void userDeleteExecution(UserEntity user, String ipAddress) {}

    @AfterReturning("userDeleteExecution(user, ipAddress)")
    public void afterDeleteRemoveAdvice(UserEntity user, String ipAddress) {
        historyService.newHistory(
                userMessagesProperties.getSuccessDeletedMessage(),
                ipAddress,
                TypeMessagesHistory.Info,
                user.getLogin()
        );
        log.info("The user successfully deleted the user." +
                " IP: " + ipAddress +
                ", user:" + user.getLogin());
    }

    @AfterThrowing(pointcut = "userDeleteExecution(user, ipAddress)", throwing = "exception")
    public void afterUserDeleteThrowingAdvice(UserEntity user, String ipAddress, Exception exception) {
        String message;
        switch (exception.getMessage()) {
            default -> {
                log.error("An error occurred while deleting a user. IP: " + ipAddress +
                        ", user:" + user.getLogin());
                message = userMessagesProperties.getErrorDeleteMessage();
            }
        }
        historyService.newHistory(message, ipAddress, TypeMessagesHistory.Error, user.getLogin());
    }
}
