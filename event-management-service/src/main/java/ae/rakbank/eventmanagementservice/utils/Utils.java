package ae.rakbank.eventmanagementservice.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Utils {

    public static String generateEventCode(String eventName, String provider) {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmm");
        String formattedDateTime = now.format(formatter);
        String eventCode = eventName.toUpperCase() + formattedDateTime + "P-" + provider.toUpperCase();

        return eventCode;
    }
}
