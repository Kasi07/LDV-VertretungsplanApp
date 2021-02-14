package dde.gymnasiumnippes.vertretungsplan;

public class Schueler {
    public String[] Schuelerid= new String[316];

    public Schueler() {
        Schuelerid();
    }

    public void Schuelerid() {
        for (int i = 0; i < 316; i++) {
            String b = Integer.toString(i + 1);
            if (i + 1 < 10) {
                b = "00" + Integer.toString(i + 1);
            }
            if (i + 1 < 100 && i + 1 > 9) {
                b = "0" + Integer.toString(i + 1);
            }
            Schuelerid[i] = "s00" + b;
        }
    }
}