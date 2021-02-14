package dde.gymnasiumnippes.vertretungsplan;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class Datum {

    private int kalenderwoche;

    public Datum() {
        getKalenderwoche();
    }


    public int getKalenderwoche() {
        String inputFormat = "yyyyMMdd";

        SimpleDateFormat dateFormat = new SimpleDateFormat(inputFormat);
        Date date = new Date();

        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int kalenderwoche = c.get(Calendar.WEEK_OF_YEAR);
       return kalenderwoche;

    }

}
