package com.jmtad.jftt.util.timeutil;

import android.text.TextUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

/**
 */
public class TimeFormatUtil {
    private TimeFormatUtil() {
    }

    public static String formatProgramTime(String startTime, String endTime) {
        StringBuilder builder = new StringBuilder(32);
        builder.append(DateCalendarUtils.getDatetime(startTime));
        builder.append(" ");
        builder.append(getFormatTime(startTime, "HH:mm"));
        builder.append("-");
        builder.append(getFormatTime(endTime, "HH:mm"));
        return builder.toString();
    }

    public static String getFormatTime(String time, String strFormat) {
        if (TextUtils.isEmpty(time)) {
            return "";
        }

        SimpleDateFormat format = new SimpleDateFormat(strFormat, Locale.US);
        String timeZone = DateCalendarUtils.getTimeZone();
        if (TextUtils.isEmpty(timeZone)) {
            timeZone = "Asia/Shanghai";
        }

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(Long.parseLong(time));
        format.setTimeZone(TimeZone.getTimeZone(timeZone));
        String strTime = format.format(calendar.getTime());
        int day = calendar.get(Calendar.DATE);
        return strTime;
    }
}
