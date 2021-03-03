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


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetAddress;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Scanner;

import org.apache.commons.io.FileUtils;
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
        toast1 = new Toast(getApplicationContext());
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

        WebsiteLaden();
        WebsiteZeigen(1);
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
                WebsiteZeigen(1);

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
                WebsiteZeigen(2);

                if (toast1.getView().getWindowVisibility() == View.VISIBLE) {
                    toast1.cancel();
                }
                customToast("Nächste Woche wird angezeigt", toast1);
                toast1.show();

                pfeil2.setVisible(false);
                pfeil.setVisible(true);
                return true;

            case R.id.websiteAktualisieren:
                WebsiteLaden();
                WebsiteZeigen(3);

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

    public void WebsiteZeigen(int url) {
        WebView webView = findViewById(R.id.webview);
        WebSettings settings = webView.getSettings();
        settings.setDisplayZoomControls(true);
        settings.setJavaScriptEnabled(true);
        webView.getSettings().setJavaScriptEnabled(true);

        String content = loadFile(url);

        if (url != 3) {lastUrl = url;}

        if (url == 1) {
            webView.loadDataWithBaseURL(null, content, "text/html", "iso-8859-1", null);
        } else if (url == 2) {
            webView.loadDataWithBaseURL(null, content, "text/html", "iso-8859-1", null);
        } else if (url == 3) {
            WebsiteZeigen(lastUrl);
        } else {
            return;
        }
    }

    FileInputStream fis = null;
    Scanner scanner;

    private String loadFile(int type) {
        try {
            if (type == 1) {
                fis = openFileInput("thisWeekFile.html");
            } else if (type == 2) {
                fis = openFileInput("nextWeekFile.html");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        scanner = new Scanner(fis);
        scanner.useDelimiter("\\Z");
        String content = scanner.next();
        scanner.close();

        return content;
    }

    private void WebsiteLaden() {
        if (websiteReloadTime + 60000 <= System.currentTimeMillis() && isInternetAvailable()) {
            Thread thread = new Thread() {
                @Override
                public void run() {

                    // URL for the current Week
                    String urlWeekNow = "https://web.gymnasium-nippes.de/~autolog/schueler/"
                            + Datum.KalenderwocheJetztURL()
                            + "/s/"
                            + meinSchueler.Schuelerid[Index]
                            + ".htm";
                    // URL for the next Week
                    String urlWeekNext = "https://web.gymnasium-nippes.de/~autolog/schueler/"
                            + Datum.KalenderwocheNextURL()
                            + "/s/"
                            + meinSchueler.Schuelerid[Index]
                            + ".htm";

                    final String acToken = "aG9sbGFuZDpmYXJmcm9taGhvbWU=";
                    Document docWeekNext;


                    Document docWeekNow;
                    try {
                        docWeekNow = Jsoup.connect(urlWeekNow)
                                .header("Authorization", "Basic " + acToken)
                                .header("Accept-Charset", "ISO-8859-1,utf-8;q=0.7,*;q=0.3")
                                .get();

                        FileOutputStream fileobj = openFileOutput("thisWeekFile.html", Context.MODE_PRIVATE);
                        fileobj.write(docWeekNow.toString().getBytes()); //writing to file
                        fileobj.close(); //File closed

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    try {
                        docWeekNext = Jsoup.connect(urlWeekNext)
                                .header("Authorization", "Basic " + acToken)
                                .header("Accept-Charset", "ISO-8859-1,utf-8;q=0.7,*;q=0.3")
                                .get();

                        FileOutputStream fileobj = openFileOutput("nextWeekFile.html", Context.MODE_PRIVATE);
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