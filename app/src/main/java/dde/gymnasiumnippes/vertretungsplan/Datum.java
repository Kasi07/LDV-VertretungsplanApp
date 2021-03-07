package dde.gymnasiumnippes.vertretungsplan;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;


public class Datum {

    private int kalenderwoche;
    private String kalenderwocheJetzt;
    private String kalenderwocheNext;

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

        int kalenderwoche = c.get(GregorianCalendar.WEEK_OF_YEAR) - 1;
        return kalenderwoche;

    }

    public String KalenderwocheJetztURL() {
        // Falls die Kalenderwoche unter 10 ist, muss eine Null vorgestellt werden.
        if (getKalenderwoche() < 10) {
            kalenderwocheJetzt = "0" + getKalenderwoche();
        } else {
            kalenderwocheJetzt = Integer.toString(getKalenderwoche());
        }
        return kalenderwocheJetzt;
    }

    public String KalenderwocheNextURL() {
        if(getKalenderwoche() < 9) {
            kalenderwocheNext = "0" + (getKalenderwoche() + 1);
        } else {
            kalenderwocheNext = Integer.toString(getKalenderwoche() + 1);
        }
        return kalenderwocheNext;
    }
}
