package sfr.application.devicescontrol.services.aspect;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import sfr.application.devicescontrol.configs.properties.UserMessagesProperties;
import sfr.application.devicescontrol.services.HistoryService;

@Aspect
@AllArgsConstructor
@Component
@Slf4j
public class UserAspect {
    private final HistoryService historyService;
    private final UserMessagesProperties userMessagesProperties;
}
