package sfr.application.devicescontrol.configs.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Getter
@Setter
@Configuration
@PropertySource(value = "classpath:messages.properties", encoding = "UTF-8")
@ConfigurationProperties(prefix = "devicetype")
public class DeviceTypeMessagesProperties {
    private String alreadyExistsMessage;
    private String errorAddMessage;
    private String successAddMessage;
}
