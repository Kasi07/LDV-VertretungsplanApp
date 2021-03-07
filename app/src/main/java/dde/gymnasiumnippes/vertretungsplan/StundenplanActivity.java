package dde.gymnasiumnippes.vertretungsplan;

import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.net.InetAddress;
import java.util.Arrays;
import java.util.Scanner;

import org.jsoup.*;
import org.jsoup.nodes.Document;

import static dde.gymnasiumnippes.vertretungsplan.MainActivity.currentname;
import static dde.gymnasiumnippes.vertretungsplan.MainActivity.namen;

public class StundenplanActivity extends AppCompatActivity {

    Schueler meinSchueler;
    Datum Datum;
    int Index = Arrays.asList(namen).indexOf(currentname);
    MenuItem pfeil;
    MenuItem pfeil2;
    MenuItem refresh;
    Button naechsteWoche;
    Button dieseWoche;
    Button aktualisieren;
    Toast toast1;

    private long websiteReloadTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.stundenplan_activity);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(currentname);
        naechsteWoche = findViewById(R.id.naechsteWocheAnzeigen);
        dieseWoche = findViewById(R.id.dieseWocheAnzeigen);
        aktualisieren = findViewById(R.id.websiteAktualisieren);
        Datum = new Datum();

        // Create first toast so it can be canceled before the first toast message.
        toast1 = new Toast(this.getBaseContext());
        customToast("", toast1);

        meinSchueler = new Schueler();

        loadWebsite();
        showWebsite(1);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        pfeil = menu.findItem(R.id.jetzigeWoche);
        pfeil2 = menu.findItem(R.id.naechsteWoche);
        refresh = menu.findItem(R.id.websiteAktualisieren);
        pfeil.setVisible(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        switch (item.getItemId()) {
            case R.id.jetzigeWoche:
                showWebsite(1);

                if (toast1.getView().getWindowVisibility() == View.VISIBLE) {
                    toast1.cancel();
                }
                customToast("Jetzige Woche wird angezeigt", toast1);
                toast1.show();

                pfeil.setVisible(false);
                pfeil2.setVisible(true);
                return true;

            case R.id.naechsteWoche:
                meinSchueler = new Schueler();
                showWebsite(2);

                if (toast1.getView().getWindowVisibility() == View.VISIBLE) {
                    toast1.cancel();
                }
                customToast("Nächste Woche wird angezeigt", toast1);
                toast1.show();

                pfeil2.setVisible(false);
                pfeil.setVisible(true);
                return true;

            case R.id.websiteAktualisieren:
                loadWebsite();
                showWebsite(3);

                if (toast1.getView().getWindowVisibility() == View.VISIBLE) {
                    toast1.cancel();
                }
                customToast("Seite wird aktualisiert", toast1);
                toast1.show();

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private int lastUrl;

    public void showWebsite(int url) {
        WebView webView = findViewById(R.id.webview);
        WebSettings settings = webView.getSettings();
        settings.setDisplayZoomControls(true);
        settings.setJavaScriptEnabled(true);
        webView.getSettings().setJavaScriptEnabled(true);

        String content = loadFile(url);
        if (url != 3) {
            lastUrl = url;
        }

        //checks if this weeks Website should be shown
        if (url == 1) {
            //shows this weeks Website
            webView.loadDataWithBaseURL(null, content, "text/html", "iso-8859-1", null);
            //checks if next weeks Website should be shown
        } else if (url == 2) {
            //shows next weeks Website
            webView.loadDataWithBaseURL(null, content, "text/html", "iso-8859-1", null);
            //checks if Website should be reloaded
        } else if (url == 3) {
            //reloads current shown Website
            showWebsite(lastUrl);
        } else {
            return;
        }
    }

    private String loadFile(int type) {
        FileInputStream fis = null;
        try {
            if (type == 1) {
                fis = openFileInput("thisWeekFile.html");
            } else if (type == 2) {
                fis = openFileInput("nextWeekFile.html");
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("Data not available!");
        }

        Scanner scanner = new Scanner(fis);
        scanner.useDelimiter("\\Z");
        String content = scanner.next();
        scanner.close();

        return content;
    }

    private void loadWebsite() {
        //only execute every 60 seconds
        //TODO and if device has internet connection
        if (websiteReloadTime + 60000 <= System.currentTimeMillis() /*&& isInternetAvailable()*/) {
            //creates new Thread for downloading the Website
            Thread thread = new Thread() {
                @Override
                public void run() {
                    Document docWeekNext;
                    Document docWeekNow;

                    //URL for this Week
                    String urlWeekNow = "https://web.gymnasium-nippes.de/~autolog/schueler/"
                            + Datum.KalenderwocheJetztURL()
                            + "/s/"
                            + meinSchueler.Schuelerid[Index]
                            + ".htm";
                    //URL for next Week
                    String urlWeekNext = "https://web.gymnasium-nippes.de/~autolog/schueler/"
                            + Datum.KalenderwocheNextURL()
                            + "/s/"
                            + meinSchueler.Schuelerid[Index]
                            + ".htm";
                    final String acToken = "aG9sbGFuZDpmYXJmcm9taGhvbWU="; //Sets username:password for Website login
                    try {
                        //downloads the timetable for this week
                        docWeekNow = Jsoup.connect(urlWeekNow)
                                .header("Authorization", "Basic " + acToken)
                                .header("Accept-Charset", "ISO-8859-1,utf-8;q=0.7,*;q=0.3")
                                .get();

                        // Writes timetable to filesystem
                        FileOutputStream fileobj = openFileOutput("thisWeekFile.html", Context.MODE_PRIVATE); //opens File
                        fileobj.write(docWeekNow.toString().getBytes()); //writing to file
                        fileobj.close(); //File closed

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    try {
                        //downloads the timetable for next week
                        docWeekNext = Jsoup.connect(urlWeekNext)
                                .header("Authorization", "Basic " + acToken)
                                .header("Accept-Charset", "ISO-8859-1,utf-8;q=0.7,*;q=0.3")
                                .get();

                        // writes timetable to filesystem
                        FileOutputStream fileobj = openFileOutput("nextWeekFile.html", Context.MODE_PRIVATE); //opens File
                        fileobj.write(docWeekNext.toString().getBytes()); //writing to file
                        fileobj.close(); //File closed
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    websiteReloadTime = System.currentTimeMillis();
                }
            };
            thread.start();
        } else {
            return;
        }
    }

    public boolean isInternetAvailable() {
        try {
            InetAddress ipAddr = InetAddress.getByName("google.com");
            return !ipAddr.equals("");
        } catch (Exception e) {
            return false;
        }
    }

    // Method to create custom toasts.
    public void customToast(String toastText, Toast toast) {
        ViewGroup vGroup = findViewById((R.id.custom_toast1));

        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.custom_toast, vGroup);

        TextView tView = layout.findViewById(R.id.tView);
        tView.setText(toastText);

        // toast = new Toast(getBaseContext());
        toast.setGravity(Gravity.CENTER | Gravity.BOTTOM, 0, 50);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);
    }
}