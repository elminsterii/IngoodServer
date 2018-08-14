package com.fff.igs.tools;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by ElminsterII on 2018/7/11.
 */
public class TimeHelper {
    public static String makeIgActivityDateStringByUI(String strUIDate) {
        StringTool stringTool = new StringTool();

        if(!stringTool.checkStringNotNull(strUIDate))
            return "";

        String bRes = "";

        String strOriginPattern = "yyyy-MM-dd HH:mm";
        String strNewPattern = "yyyy-MM-dd HH:mm:ss";

        DateFormat dateOriginFormat = new SimpleDateFormat(strOriginPattern, Locale.getDefault());
        DateFormat dateNewFormat = new SimpleDateFormat(strNewPattern, Locale.getDefault());

        Date date = null;
        try {
            date = dateOriginFormat.parse(strUIDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        bRes = dateNewFormat.format(date);

        return bRes;
    }

    public static boolean checkBeginTimeBeforeEndTime(String strBeginTime, String strEndTime) {
        boolean bRes = false;
        StringTool stringTool = new StringTool();

        if(stringTool.checkStringNotNull(strBeginTime)
                && stringTool.checkStringNotNull(strEndTime)) {

            String strPattern = "yyyy-MM-dd HH:mm:ss";
            DateFormat dateFormat = new SimpleDateFormat(strPattern, Locale.getDefault());

            try {
                Date dateBegin = dateFormat.parse(strBeginTime);
                Date dateEnd = dateFormat.parse(strEndTime);
                bRes = dateBegin.before(dateEnd);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return bRes;
    }

    public static String gmtToLocalTime(String strGMT) {
        String strDatePattern = "yyyy-MM-dd HH:mm:ss";
        DateFormat dateFormat = new SimpleDateFormat(strDatePattern, Locale.US);
        dateFormat.setTimeZone(TimeZone.getTimeZone("Etc/UTC"));

        SimpleDateFormat outputFormat = new SimpleDateFormat(strDatePattern, Locale.getDefault());
        outputFormat.setTimeZone(TimeZone.getDefault());

        Date date = null;
        try {
            date = dateFormat.parse(strGMT);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return outputFormat.format(date);
    }

    public static String getCurTime() {
        Calendar calendar = Calendar.getInstance(Locale.getDefault());
        //@@ ROC time.
        calendar.add(Calendar.HOUR, 8);
        Date dateCurrent = calendar.getTime();

        String strDatePattern = "yyyy-MM-dd HH:mm:ss";
        DateFormat dateFormat = new SimpleDateFormat(strDatePattern, Locale.getDefault());

        return dateFormat.format(dateCurrent);
    }

    public static String getTimeByDaysBasedCurrent(int iDays) {
        Calendar calendar = Calendar.getInstance(Locale.getDefault());
        calendar.add(Calendar.DATE, iDays);
        Date date = calendar.getTime();

        String strDatePattern = "yyyy-MM-dd HH:mm:ss";
        DateFormat dateFormat = new SimpleDateFormat(strDatePattern, Locale.getDefault());

        return dateFormat.format(date);
    }
}
