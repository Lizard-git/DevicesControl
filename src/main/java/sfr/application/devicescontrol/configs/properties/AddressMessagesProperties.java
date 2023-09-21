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
@ConfigurationProperties(prefix = "address")
public class AddressMessagesProperties {
    //standard
    private String successAdditionMessage;
    private String errorAddMessage;
    private String successChangeMessage;
    private String errorChangeMessage;
    private String successDeletedMessage;
    private String errorDeleteMessage;
    private String warningAlreadyExistsMessage;

    //custom
    private String errorSomethingIsIndicatedMessage;
    private String errorChangeNonExistentEntry;

}
