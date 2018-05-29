package com.sacombank.consumers.utils;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by DUY on 9/13/2017.
 */

public class DateUtil {

    public static long getDayBetweenTwoDate(String startDate, String endDate, String format) {
        SimpleDateFormat dates = new SimpleDateFormat(format);
        Date dateStart = new Date();
        Date dateEnd = new Date();
        try {
            dateStart = dates.parse(startDate);
            dateEnd = dates.parse(endDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        long difference = Math.abs(dateStart.getTime() - dateEnd.getTime());
        long differenceDates = difference / (24 * 60 * 60 * 1000);

        Log.e("HERE", "HERE: " + differenceDates);

        return differenceDates;
    }

    public static String getDate(long milliSeconds, String dateFormat) {
        // Create a DateFormatter object for displaying date in specified format.
        SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);

        // Create a calendar object that will convert the date and time value in milliseconds to date.
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliSeconds);
        return formatter.format(calendar.getTime());
    }

    public static String getDateHistory(String date) {
        String convertDate = "";
        String yyyy = "20";
        if (date.length() >= 6) {
            yyyy += date.substring(0, 2);
            convertDate += date.substring(4, 6).concat("/").concat(date.substring(2, 4)).concat("/").concat(yyyy);
        }
        return convertDate;
    }

    public static String getDateBalance(String date) {
        String convertDate = "";
        if (date.length() >= 8) {
            convertDate += date.substring(6, 8).concat("/").concat(date.substring(4, 6)).concat("/").concat(date.substring(0, 4));
        }
        return convertDate;
    }

    public static String getCurrentDateTime(String format){
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        return formatter.format(new Date());
    }
}
