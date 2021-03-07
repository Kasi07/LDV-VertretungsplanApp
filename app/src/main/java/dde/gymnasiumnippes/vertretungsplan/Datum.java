package dde.gymnasiumnippes.vertretungsplan;

import java.util.Calendar;


public class Datum {

    private int kalenderWoche;
    private String kalenderWocheJetzt;
    private String kalenderWocheNext;

    public Datum() {
        getKalenderWoche();
    }


    public int getKalenderWoche() {
        Calendar calendar = Calendar.getInstance();
        kalenderWoche = calendar.get(Calendar. WEEK_OF_YEAR);
        return kalenderWoche;
    }

    public String KalenderwocheJetztURL() {
        //In case the number of the week is under 10 it will be added a 0 before
        if (getKalenderWoche() < 10) {
            kalenderWocheJetzt = "0" + getKalenderWoche();
        } else {
            kalenderWocheJetzt = Integer.toString(getKalenderWoche());
        }
        return kalenderWocheJetzt;
    }

    public String KalenderwocheNextURL() {
        if(getKalenderWoche() < 9) {
            kalenderWocheNext = "0" + (getKalenderWoche() + 1);
        } else {
            kalenderWocheNext = Integer.toString(getKalenderWoche() + 1);
        }
        return kalenderWocheNext;
    }
}
