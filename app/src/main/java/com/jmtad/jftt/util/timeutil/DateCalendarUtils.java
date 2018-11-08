package com.jmtad.jftt.util.timeutil;

import android.text.TextUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Formatter;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

public final class DateCalendarUtils {
    public static final String TAG = "DateCalendarUtils";

    private static long startDSTMs = 0;

    private static long endDSTMs = 0;

    private static String timeZone = TimeZone.getDefault().getID();

    private static final SimpleDateFormat normalDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");

    private static DateCalendarUtils gInstanc = new DateCalendarUtils();

    private final SimpleDateFormat dateFormatStandard = new SimpleDateFormat("yyyy-MM-dd " +
            "HH:mm:ss", Locale.US);

    private final SimpleDateFormat dateFormatCompact = new SimpleDateFormat("yyyyMMddHHmmss",
            Locale.US);

    private final SimpleDateFormat zoneFormatStandard = new SimpleDateFormat("yyyy-MM-dd " +
            "HH:mm:ss" + " Z", Locale.US);

    private final SimpleDateFormat zoneFormatCompact = new SimpleDateFormat("yyyyMMddHHmmss Z",
            Locale.US);

    public static final String dateFormatStringDaySelector = "MMM dd";

    private DateCalendarUtils() {

    }

    public static boolean isTimeValid(String startTime, String endTime, String format) {
        try {
            SimpleDateFormat format1 = new SimpleDateFormat(format);
            Date startDate = format1.parse(startTime);
            Date endDate = format1.parse(endTime);
            Date nowDate = new Date();
            if (nowDate.after(startDate) && nowDate.before(endDate)) {
                return true;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * @param startTime 开始时间戳
     * @param endTime   结束时间戳
     * @return
     */
    public static boolean isTimeValid(long startTime, long endTime) {
        Date nowDate = new Date();
        Date start = new Date(startTime);
        Date end = new Date(endTime);
        if (nowDate.before(end) && nowDate.after(start)) {
            return true;
        }
        return false;
    }

    /**
     * 将时间转为时分秒 。
     * 128870000 ---> 35:47:50
     *
     * @param timeMs
     * @return
     */
    public static String parseSec(int timeMs) {
        int totalSeconds = timeMs % 1000 >= 500 ? (timeMs / 1000) + 1 : timeMs / 1000;

        // 四舍五入
        int seconds = totalSeconds % 60;
        int minutes = (totalSeconds / 60) % 60;
        int hours = totalSeconds / 3600 % 24;
        StringBuilder mFormatBuilder = new StringBuilder();
        Formatter mFormatter = new Formatter(mFormatBuilder, Locale.getDefault());
        mFormatBuilder.setLength(0);
        return mFormatter.format("%02d:%02d:%02d", hours, minutes, seconds).toString();
    }

    public static String timeChange(int timeMs) {
        //int totalSeconds = timeMs % 1000 >= 500 ? (timeMs / 1000) + 1 : timeMs / 1000;

        // 四舍五入
        int seconds = timeMs % 60;
        int minutes = (timeMs / 60) % 60;
        int hours = timeMs / 3600 % 24;
        StringBuilder mFormatBuilder = new StringBuilder();
        Formatter mFormatter = new Formatter(mFormatBuilder, Locale.getDefault());
        mFormatBuilder.setLength(0);
        return mFormatter.format("%02d:%02d:%02d", hours, minutes, seconds).toString();
    }

    /**
     * @param second
     * @return mm:ss
     * eg:
     * 120:33
     */
    public static String mmss(int second) {
        String mStr = "00";
        String sStr = "00";
        int mm = second / 60;
        if (mm < 10) {
            mStr = "0" + mm;
        } else {
            mStr = mm + "";
        }
        int ss = second % 60;
        if (ss < 10) {
            sStr = "0" + ss;
        } else if (ss == 0) {
            sStr = "00";
        } else {
            sStr = ss + "";
        }
        return mStr + ":" + sStr;
    }

    public static String formatStandard(Date date) {
        synchronized (gInstanc) {
            return gInstanc.dateFormatStandard.format(date);
        }
    }

    public static Date parseCompact(String dateString) {
        if (dateString == null) {
            return new Date();
        }
        synchronized (gInstanc) {
            try {
                if (dateString.length() > 19) {
                    dateString = dateString.replace("UTC", "GMT");
                    return gInstanc.zoneFormatCompact.parse(dateString);
                } else {
                    return gInstanc.dateFormatCompact.parse(dateString);
                }
            } catch (ParseException e) {
            }
        }
        return new Date();
    }

    private static SimpleDateFormat getStandFormat() {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
        format.setTimeZone(TimeZone.getTimeZone("GMT00:00"));
        return format;
    }


    /**
     * UTC时间转换为本地时间。
     *
     * @param time yyyyMMddHHmmss。
     * @return 本地时间。
     */
    public static String converUtcToLocalDate(String time) {
        String converttime = null;
        long resultTime = 0;
        if (time == null) {
            return null;
        }
        Date date = null;
        String id = getTimeZone();
        TimeZone timezone = TimeZone.getTimeZone(id);

        if (timezone.useDaylightTime()) {
            SimpleDateFormat format = getStandFormat();
            try {
                date = format.parse(time);
                resultTime = date.getTime() + timezone.getRawOffset();
                if (isInDST(date)) {
                    resultTime = resultTime + timezone.getDSTSavings();
                    converttime = format.format(resultTime);
                } else {
                    converttime = format.format(resultTime);
                }
            } catch (Exception e) {
            }
        } else {
            converttime = converUtcToLocal(time, timezone);
        }
        return converttime;
    }

    /**
     * 本地时间转换为UTC时间。
     *
     * @param time yyyyMMddHHmmss(DST)。
     * @return yyyyMMddHHmmss。
     * @author gWX154053
     */
    public static String converLocalToUTCDate(String time) {
        String converttime = null;
        if (time == null) {
            return null;
        }
        String id = getTimeZone();
        TimeZone timezone = TimeZone.getTimeZone(id);

        converttime = converLocalToUTC(time, timezone);
        return converttime;
    }

    private static String converUtcToLocal(String srcTime, TimeZone timezone) {
        String convertTime = null;
        Date resultDate;
        long resultTime = 0;
        SimpleDateFormat format = getStandFormat();
        try {
            format.setTimeZone(TimeZone.getTimeZone("GMT00:00"));
            resultDate = format.parse(srcTime);
            resultTime = resultDate.getTime();
        } catch (Exception e) {
            return null;
        }

        format.setTimeZone(timezone);
        convertTime = format.format(resultTime);

        return convertTime;
    }

    private static String converLocalToUTC(String srcTime, TimeZone timezone) {
        String convertTime = null;
        Date resultDate;
        boolean isDST = false;

        long resultTime = 0;
        String time;
        if (srcTime.contains("DST")) {
            time = srcTime.replace("DST", "");
            isDST = true;
        } else if (isInDST(getParseDate("yyyyMMddHHmmss", srcTime))) {
            isDST = true;
            time = srcTime;
        } else {
            time = srcTime;
        }
        SimpleDateFormat format = getStandFormat();
        try {
            resultDate = format.parse(time);
            resultTime = resultDate.getTime();

            if (isDST) {
                resultTime = resultTime - timezone.getDSTSavings();
            }
            resultTime = resultTime - timezone.getRawOffset();
        } catch (ParseException e) {
        }
        convertTime = format.format(resultTime);
        return convertTime;
    }

    /**
     * 将毫秒数转为UTC时间，格式为yyyyMMddHHmmss
     *
     * @param timeMillis 毫秒数，注意为实际偏移量，不需要做时区的偏移修正
     * @return UTC时间
     */
    public static String converToUTC(long timeMillis) {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
        format.setTimeZone(TimeZone.getTimeZone("GMT00:00"));
        return format.format(timeMillis);
    }

    public static String formatDateDM(long time) {
        Calendar mCalendar = new GregorianCalendar();
        mCalendar.setTimeInMillis(time);
        mCalendar.setTimeZone(TimeZone.getTimeZone(timeZone));
        return String.format("%02d.%02d", mCalendar.get(Calendar.MONTH) + 1,
                mCalendar.get(Calendar.DAY_OF_MONTH));

    }

    /*    */

    /**
     * @param time
     * @return 10月10号
     *//*
    public static String formatDateMonthAndDay(Context context,long time)
    {
        Calendar mCalendar = new GregorianCalendar();
        mCalendar.setTimeInMillis(time);
        mCalendar.setTimeZone(TimeZone.getTimeZone(timeZone));

        String fomatString = context.getString(R.string.live_tv_month_and_day);

        return String.format(fomatString, mCalendar.get(Calendar.MONTH) + 1,
                mCalendar.get(Calendar.DAY_OF_MONTH));

    }*/
    public static String formatDate(long time, String format) {
        Calendar mCalendar = new GregorianCalendar();
        mCalendar.setTimeInMillis(time);
        mCalendar.setTimeZone(TimeZone.getTimeZone(timeZone));
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        return simpleDateFormat.format(mCalendar.getTime());
    }


    public static boolean isInDST(Date date) {
        boolean isInDst = false;
        if (date == null) {
            return false;
        }
        if ((date.getTime() >= startDSTMs) && (date.getTime() <= endDSTMs - 1)) {
            isInDst = true;
        }
        return isInDst;
    }

    public static boolean isInDST(long time) {
        boolean isInDst = false;
        if ((time >= startDSTMs) && (time <= endDSTMs - 1)) {
            isInDst = true;
        }
        return isInDst;
    }


    public static String getTimeZone() {
        return timeZone;
    }

    /**
     * 设置当前时区，只供IPTV系统使用
     * <p/>
     * <p/>
     * 本地时区 。如果为 空字串或 空指针，则清除设置，还是使用手机系统的时区设置
     *
     * @return
     */
    public static void setTimeZone(String zone) {
        if (TextUtils.isEmpty(zone)) {
            timeZone = TimeZone.getDefault().getID();
        } else if (zone.contains("GMT")) {
            timeZone = zone;
        } else {
            String zones[] = TimeZone.getAvailableIDs();
            for (String availableTimeZone : zones) {
                if (zone.equals(availableTimeZone)) {
                    timeZone = zone;
                    return;
                }
            }
            timeZone = TimeZone.getDefault().getID();
        }
    }

    private static SimpleDateFormat getSimpleFormat(String formatStr, Locale local) {
        SimpleDateFormat format = null;
        if (local == null) {
            format = new SimpleDateFormat(formatStr);
        } else {
            format = new SimpleDateFormat(formatStr, local);
        }
        format.setTimeZone(TimeZone.getTimeZone(getTimeZone()));
        return format;
    }

    public static SimpleDateFormat getSimpleFormat(String formatStr) {
        SimpleDateFormat format = new SimpleDateFormat(formatStr, Locale.US);
        format.setTimeZone(TimeZone.getTimeZone(getTimeZone()));
        return format;
    }

    public static String getFormatString(String formatStr, long time) {
        return getSimpleFormat(formatStr).format(time);
    }

    public static String getFormatString(String formatStr, Date date) {
        return getSimpleFormat(formatStr).format(date);
    }


    private static Date getParseDate(String formatStr, String time) {
        Date response = null;
        try {
            response = getSimpleFormat(formatStr).parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return response;
    }

    /**
     * 将long time 转化为 HH:mm 或者 dd.MM 格式
     *
     * @param time
     * @param ishhmmformat
     * @return
     */
    public static String timeFieldMaker(long time, boolean ishhmmformat) {
        SimpleDateFormat formatter = null;
        if (ishhmmformat) {
            formatter = getSimpleFormat("HH:mm");
        } else {
            formatter = getSimpleFormat("dd.MM");
        }
        return formatter.format(time);
    }

    public static String getDatetime(String time) {
        if (TextUtils.isEmpty(time)) {
            return "";
        }
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(Long.parseLong(time));
        SimpleDateFormat format = getSimpleFormat("d MMM, yyyy", Locale.US);
        String timeZone = getTimeZone();
        if (TextUtils.isEmpty(timeZone)) {
            timeZone = "Asia/Shanghai";
        }
        format.setTimeZone(TimeZone.getTimeZone(timeZone));
        String strTime = format.format(cal.getTime());
        return strTime;
    }

    public static String secToTime(int time) {
        String timeStr = null;
        int hour = 0;
        int minute = 0;
        int second = 0;
        if (time <= 0) {
            return "00:00";
        } else {
            minute = time / 60;
            if (minute < 60) {
                second = time % 60;
                timeStr = unitFormat(minute) + ":" + unitFormat(second);
            } else {
                hour = minute / 60;
                if (hour > 99) {
                    return "99:59:59";
                }
                minute = minute % 60;
                second = time - hour * 3600 - minute * 60;
                timeStr = unitFormat(hour) + ":" + unitFormat(minute) + ":" + unitFormat(second);
            }
        }
        return timeStr;
    }

    public static String unitFormat(int i) {
        String retStr = null;
        if (i >= 0 && i < 10) {
            retStr = "0" + Integer.toString(i);
        } else {
            retStr = "" + i;
        }
        return retStr;
    }

    /**
     * convert timeMillis to specified format
     *
     * @param time         timeMillis
     * @param formartValue format such as "yyyyMMdd"
     * @return date string
     */
    public static String formatDateTime(long time, String formartValue) {
        TimeZone timeZone = TimeZone.getDefault();
        SimpleDateFormat format = new SimpleDateFormat(formartValue, Locale.CHINA);
        format.setTimeZone(timeZone);
        String strTime = format.format(time);

        //To support daylight-saving time, when out of daylight saving one hour ago (note part
        // time zone transition adjustment time instead of 1 hour, used here timeZone
        // .getDSTSavings ()) time, add DST
        if (formartValue.contains("HH:mm") && timeZone.useDaylightTime() && timeZone.inDaylightTime
                (new Date(time)) && !timeZone.inDaylightTime(new Date(time + timeZone
                .getDSTSavings()))) {
            strTime = strTime + " DST";
        }
        return strTime;
    }

    /**
     * get TimeMillis of yyyyMMdd 00:00:00
     *
     * @param date yyyyMMdd
     * @return TimeMillis
     */
    public static long getStartTimeOfDay(String date) {
        String pattern = "yyyyMMdd";
        SimpleDateFormat format = new SimpleDateFormat(pattern, Locale.US);
        format.setTimeZone(TimeZone.getDefault());
        try {
            return format.parse(date).getTime();
        } catch (ParseException e) {
        }

        return 0L;
    }

    /**
     * get TimeMillis of yyyyMMdd 23:59:59
     *
     * @param date yyyyMMdd
     * @return TimeMillis
     */
    public static long getEndTimeOfDay(String date) {
        long startTime = getStartTimeOfDay(date);
        Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
        calendar.setTimeInMillis(startTime);
        calendar.add(Calendar.DATE, 1);
        return calendar.getTimeInMillis();
    }

    /**
     * 日期转时间戳
     *
     * @param timeString 日期
     * @return
     */
    public static long getTime(String timeString) {
        long time = 0;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date;
        try {
            date = sdf.parse(timeString);
            time = date.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return time;
    }
}
