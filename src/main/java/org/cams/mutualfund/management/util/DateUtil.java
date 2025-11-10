package org.cams.mutualfund.management.util;

import java.time.LocalDate;
import java.time.ZoneId;

public class DateUtil {

    public static boolean isCurrentDate(LocalDate date) {
        LocalDate today = LocalDate.now(ZoneId.systemDefault());
        return date.equals(today);   
    }
}
