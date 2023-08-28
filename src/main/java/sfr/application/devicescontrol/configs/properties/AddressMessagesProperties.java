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
    private String successAddNewAddressMessage;
    private String successChangeAddressMessage;
    private String addressAlreadyExistsMessage;
    private String errorSaveAddressMessage;
    private String errorChangeAddressMessage;
    private String addressObjectHasIdMessage;
    private String warningMutableObjectIdIsNullMessage;
    private String errorObjectAreLocatedAddressEntityMessage;
    private String errorDeleteAddressMessage;
    private String successDeletedAddressMessage;
}
