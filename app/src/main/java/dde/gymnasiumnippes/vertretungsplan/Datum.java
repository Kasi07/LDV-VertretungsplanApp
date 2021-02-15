package dde.gymnasiumnippes.vertretungsplan;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;


public class Datum {

    private int kalenderwoche;

    public Datum() {
        getKalenderwoche();
    }


    public int getKalenderwoche() {
        String inputFormat = "yyyyMMdd";

        SimpleDateFormat dateFormat = new SimpleDateFormat(inputFormat);
        Date date = new Date();
        TimeZone.getDefault();
        Calendar c = Calendar.getInstance();
        c.setTime(date);

        int kalenderwoche = c.get(GregorianCalendar.WEEK_OF_YEAR);
        return kalenderwoche-1;

    }

}
