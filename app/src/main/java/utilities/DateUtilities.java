package utilities;

import android.annotation.SuppressLint;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtilities {

    public static boolean isDateInPast(Date date) {
        if (date == null)
            return true;
        Date nowDate = Calendar.getInstance().getTime();
        return date.before(nowDate);
    }

    public static int getDaysBetweenDates(Date date1, Date date2) {
        float days = ((float) (date2.getTime() - date1.getTime()) / (1000.0f * 60.0f * 60.0f * 24.0f));
        return (int) Math.ceil(days);
    }

    public static boolean isSameDay(Date date1, Date date2) {
        Calendar calendar1 = Calendar.getInstance();
        calendar1.setTime(date1);
        Calendar calendar2 = Calendar.getInstance();
        calendar2.setTime(date2);
        boolean sameYear = calendar1.get(Calendar.YEAR) == calendar2.get(Calendar.YEAR);
        boolean sameMonth = calendar1.get(Calendar.MONTH) == calendar2.get(Calendar.MONTH);
        boolean sameDay = calendar1.get(Calendar.DAY_OF_MONTH) == calendar2.get(Calendar.DAY_OF_MONTH);
        return (sameDay && sameMonth && sameYear);
    }

    @SuppressLint("SimpleDateFormat")
    public static String getDateOnly(String dateInString) {
        Date date = null;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        try {
            if (dateInString != null) date = formatter.parse(dateInString);
            else return "";
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat formatter2 = new SimpleDateFormat("dd/MM/yyyy");
        return formatter2.format(date);
    }

    @SuppressLint("SimpleDateFormat")
    public static String getHoursOnly(String dateInString) {
        Date date = null;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        try {
            date = formatter.parse(dateInString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat formatter2 = new SimpleDateFormat("hh:mm");
        assert date != null;
        return formatter2.format(date);
    }

    @SuppressLint("SimpleDateFormat")
    public static String getHoursBase12(String dateInString) {
        Date date = null;
        String extension;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        try {
            date = formatter.parse(dateInString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat formatterHours = new SimpleDateFormat("hh");
        SimpleDateFormat formatterMinutes = new SimpleDateFormat("mm");
        int minutes = Integer.parseInt(formatterMinutes.format(date));
        int hours = Integer.parseInt(formatterHours.format(date));
        if (hours >= 12) {
            extension = " AM";
            hours -= 12;
        } else extension = " PM";
        return hours + ":" + minutes + extension;
    }

    @SuppressLint("SimpleDateFormat")
    public static String getEndHours(String dateInString, int duration) {
        Date date = null;
        String extension;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        try {
            date = formatter.parse(dateInString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat formatterHours = new SimpleDateFormat("hh");
        SimpleDateFormat formatterMinutes = new SimpleDateFormat("mm");
        int minutes = Integer.parseInt(formatterMinutes.format(date));
        int hours = Integer.parseInt(formatterHours.format(date));
        int hoursAdd = 0;
        while (duration > 60) {
            duration = duration - 60;
            hoursAdd += 1;
        }
        hours += hoursAdd;
        if (hours >= 12) {
            extension = " AM";
            hours -= 12;
        } else extension = " PM";
        return hours + ":" + (minutes + duration) + extension;
    }
}
