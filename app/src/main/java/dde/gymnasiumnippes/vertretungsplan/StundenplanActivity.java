package dde.gymnasiumnippes.vertretungsplan;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.muddzdev.styleabletoast.StyleableToast;

import org.apache.http.client.ClientProtocolException;

import java.io.IOException;
import java.util.Arrays;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static dde.gymnasiumnippes.vertretungsplan.MainActivity.currentname;
import static dde.gymnasiumnippes.vertretungsplan.MainActivity.namen;

public class StundenplanActivity extends AppCompatActivity {

    SharedPreferences pref;
    Schueler meinSchueler;
    Datum Datum;
    int Index = Arrays.asList(namen).indexOf(currentname);
    String kalenderwochejetzt;
    MenuItem pfeil;
    MenuItem pfeil2;
    MenuItem refresh;
    Button naechsteWoche;
    Button dieseWoche;
    Button aktualisieren;
    Toast toast1;



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

        toast1 = new Toast(this.getBaseContext());
        customToast("", toast1);

        if (Datum.getKalenderwoche() <10)
        {
            kalenderwochejetzt = "0" + Datum.getKalenderwoche();
        }
        else{
            kalenderwochejetzt = Integer.toString(Datum.getKalenderwoche());
        }

        meinSchueler = new Schueler();
        Websiteneuladen();
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        pfeil = menu.findItem(R.id.letzteWoche);
        pfeil2 = menu.findItem(R.id.naechsteWoche);
        refresh = menu.findItem(R.id.websiteAktualisieren);
        pfeil.setVisible(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        switch (item.getItemId()) {
            case R.id.naechsteWoche:
                Datum = new Datum();
                if (Datum.getKalenderwoche() <10)
                {
                    kalenderwochejetzt = "0" + Integer.toString(Datum.getKalenderwoche()+1);
                }
                else{
                    kalenderwochejetzt = Integer.toString(Datum.getKalenderwoche()+1);
                }
                meinSchueler = new Schueler();
                Websiteneuladen();

                if(toast1.getView().getWindowVisibility() == View.VISIBLE){
                    toast1.cancel();
                }
                customToast("Nächste Woche wird angezeigt", toast1);
                toast1.show();

                pfeil2.setVisible(false);
                pfeil.setVisible(true);
                return true;

            case R.id.letzteWoche:
                if (Datum.getKalenderwoche() <10)
                {
                    kalenderwochejetzt = "0" + Datum.getKalenderwoche();
                }
                else{
                    kalenderwochejetzt = Integer.toString(Datum.getKalenderwoche());
                }
                Websiteneuladen();

                if(toast1.getView().getWindowVisibility() == View.VISIBLE){
                    toast1.cancel();
                }
                customToast("Jetzige Woche wird angezeigt", toast1);
                toast1.show();

                pfeil.setVisible(false);
                pfeil2.setVisible(true);
                return true;

            case R.id.websiteAktualisieren:
                Websiteneuladen();

                if(toast1.getView().getWindowVisibility() == View.VISIBLE){
                    toast1.cancel();
                }
                customToast("Seite wird aktualisiert", toast1);
                toast1.show();

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }



    public void Websiteneuladen() {

        Uri.Builder builder = new Uri.Builder();
        builder.scheme("https")
                .authority("web.gymnasium-nippes.de")
                .appendPath("~autolog")
                .appendPath("schueler")
                .appendPath(kalenderwochejetzt)
                .appendPath("s")
                .appendPath(meinSchueler.Schuelerid[Index]);
        String url = (String) builder.toString() + ".htm";

        pref = getSharedPreferences("app", Context.MODE_PRIVATE);
        WebView webView = findViewById(R.id.webview);
        WebSettings settings = webView.getSettings();
        settings.setDefaultTextEncodingName("utf-8");
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(wvc);
        webView.loadUrl(url);
    }

    public WebViewClient wvc = new WebViewClient() {

        @SuppressWarnings("deprecation")
        public WebResourceResponse shouldInterceptRequest(WebView view, String url) {
            try {
                final String acToken = "aG9sbGFuZDpmYXJmcm9taGhvbWU=";

                OkHttpClient okHttpClient = new OkHttpClient();
                Request request = new Request.Builder().url(url).addHeader("Authorization", "Basic " + acToken)
                .addHeader("Accept-Charset", "ISO-8859-1,utf-8;q=0.7,*;q=0.3")
                        .build();

                Response response = okHttpClient.newCall(request).execute();

                return new WebResourceResponse(response.header("content-type", response.body().contentType().type()),
                        response.header("content-encoding", "utf-8"),
                        response.body().byteStream());


            } catch (ClientProtocolException e) {
                return null;
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }
    };


    public void customToast(String toastText, Toast toast){
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

    //TODO junge du kleiner Lappen lern jetzt mal wie jsoup geht damit du die html datei kriegen kannst
   /* private void getWebsite() {
       Document doc = null;
        try {
            doc = Jsoup.connect("https://web.gymnasium-nippes.de/~autolog/schueler/ ").get();


            String title = doc.title();
        } catch (IOException e) {

        }


    } */






}
