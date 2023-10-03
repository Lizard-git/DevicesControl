package sfr.application.devicescontrol.utils;

import sfr.application.devicescontrol.entities.telbook.devices_control.AddressEntity;
import sfr.application.devicescontrol.entities.telbook.devices_control.ManufacturerEntity;
import sfr.application.devicescontrol.entities.telbook.devices_control.StatusEntity;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UtilsMethods {
    private static final String IPADDRESS_PATTERN =
            "^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
                    "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
                    "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
                    "([01]?\\d\\d?|2[0-4]\\d|25[0-5])$";

    /**
     * Валидация ip адреса
     * @param ipAddress - страка ip адреса
     * @return ip address
     * @throws UnknownHostException - ошибка получания ip адресса
     */
    public static String ipAddressValidator(String ipAddress) throws UnknownHostException {
        Pattern pattern = Pattern.compile(IPADDRESS_PATTERN);
        Matcher matcher = pattern.matcher(ipAddress);
        if (matcher.matches()) {
            return ipAddress;
        }
        InetAddress address1 = InetAddress.getLocalHost();
        return address1.getHostAddress();
    }

    public static String formatAddress(AddressEntity address) {
        return (address != null) ? address.getSettlements().getName() + " " +
                ((address.getStreet() != null) ? address.getStreet() + " " : "") +
                address.getHouse() : "";
    }

    public static String formatUserUsing(String userUsing) {
        return (userUsing != null) ? userUsing : "";
    }

    public static String formatManufacturer(ManufacturerEntity manufacturer) {
        return (manufacturer != null) ? manufacturer.getName() : "";
    }

    public static String formatStatus(StatusEntity status) {
        return (status != null) ? status.getName() : "";
    }

    public static String formatDate(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        return (date != null) ? dateFormat.format(date) : "";
    }
}
