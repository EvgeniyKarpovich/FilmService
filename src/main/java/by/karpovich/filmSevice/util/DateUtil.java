package by.karpovich.filmSevice.util;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class DateUtil {

    public static String mapFromInstantToString(Instant instant) {
        DateTimeFormatter formatter =
                DateTimeFormatter.ofPattern("dd MMMM yyyy")
                        .withZone(ZoneId.of("UTC"));
        String format = formatter.format(instant);
        return format;
    }
}
