package sfr.application.devicescontrol.exceptions;

import lombok.Getter;
import sfr.application.devicescontrol.entities.telbook.devices_control.DeviceEntity;

@Getter
public class DeviceException extends Exception {
    private DeviceEntity device;
    public DeviceException(String message) {
        super(message);
    }

    public DeviceException(String message, DeviceEntity device) {
        super(message);
        this.device = device;
    }
}
