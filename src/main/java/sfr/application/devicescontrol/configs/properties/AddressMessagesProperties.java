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
    private String successAddMessage;
    private String successChangeMessage;
    private String successDeletedMessage;
    private String errorAddMessage;
    private String errorChangeMessage;
    private String errorObjectAreLocatedAddressEntityMessage;
    private String errorDeleteMessage;
    private String warningMutableObjectIdIsNullMessage;
    private String objectHasIdMessage;
    private String alreadyExistsMessage;
}
