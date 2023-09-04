package sfr.application.devicescontrol.utils;

import java.net.InetAddress;
import java.net.UnknownHostException;
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

}
