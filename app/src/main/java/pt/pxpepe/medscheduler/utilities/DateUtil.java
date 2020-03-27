package pt.pxpepe.medscheduler.utilities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {

    private final static String DATE_TIME_FORMAT_12 = "dd/MM/yyyy hh:mm a";
    private final static String DATE_TIME_FORMAT_24 = "dd/MM/yyyy hh:mm";

    public static String date2String(Date date, Context ctx) {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat(getTimeFormat(ctx));
        return sdf.format(date);
    }

    private static String getTimeFormat(Context ctx) {
        String format = DATE_TIME_FORMAT_12;
        if (android.text.format.DateFormat.is24HourFormat(ctx)) {
            format = DATE_TIME_FORMAT_24;
        }
        return format;
    }

    public static Date string2Date(String strDate, Context ctx) {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat(getTimeFormat(ctx));
        Date date = null;
        try {
            date = sdf.parse(strDate);
        } catch (ParseException e) {
            Log.e("",e.toString());
        }
        return date;
    }

    public static Date sumDays(Date date, int days) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, days); //minus number would decrement the days
        return cal.getTime();
    }


}
