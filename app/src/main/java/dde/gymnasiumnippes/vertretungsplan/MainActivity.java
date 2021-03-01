package dde.gymnasiumnippes.vertretungsplan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.muddzdev.styleabletoast.StyleableToast;

import org.w3c.dom.Text;

import java.net.InetAddress;
import java.util.Arrays;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private Button redirect;
    public static String[] namen = new String[]{
            "Abels Franziska ", "Abram Jeremias ", "Akin Louis ", "Aksu Gülperi ", "Al-Aydani Mohamed Akeel Ghaleb ", "Alibas Noel", "Altiner Zeynep", "Altunsuyu Atilla ", "Altunsuyu Feray-Elif ", "Alzierj Mohamed ", "Amiri Mojib ", "Amsink Henry ", "Arici Murat ", "Atmaca Tasin ", "Ay Zeynep ", "Azemi Alberina ", "Azevedo Neuschütz Bruno ", "Bacher Vilem", "Bahlmann Marie ", "Bakircioglu Dilara ", "Bakis Ekin ", "Balduin Luisa Silvia ", "Barbarotto Rinaldo ", "Bartels Elias ", "Beckers Salvatore ", "Beemelmanns Anton ", "Beemelmanns Flora Luise ", "Beier Lars ", "Bekiroglu Baha ", "Benstem Zoe Bella ", "Bernsmann Hannes ", "Bernsmann Tabea ", "Billor Leyan ", "Blankenmeier Helena Theresa ", "Bleikertz Catalina", "Blindert Ruben ", "Bode Kai ", "Bonnen Annika ", "Brock Konrad ", "Buchanan Riva Jack ", "Bucioglu Abdo John ", "Budde Ellen Lilly ", "Bünhove Cecilie Helena ", "Burauer Christian ", "Busch Celina ", "Capkin Aysu ", "Cedzich Alexander Josef ", "Celik Berkan ", "Celilkkale Ilayda", "Chimienti Luis Leonardo ", "Cimino Nunziata ", "Colmer Ben ", "Creydt Julia Sophie ", "Das Zoe ", "Demiröz Habibe Zehra", "Diehl Johannes ", "Dinc Esat Kaan ", "Direk Süeda Begum ", "Doepner Nick ", "Dong Zhixing Diny ", "Drosihn Marie Lara ", "Dunkel Jonas ", "Dyckx Charlotte Pauline", "Effer Jasmin ", "Effer Laura Agnes ", "Egerland Hannah Leonie ", "Egert Florian", "Eis Elisabeth ", "Ejiofor Kelly ", "Elallali Mohamed ", "Elder Helene Meret Raphaela ", "Enger Lisa Merit ", "Enns Finn Elea ", "Erler Mara-Luisa ", "Erol Ilayda ", "Evci Melisa Meryem ", "Everding Jakob Elias ", "Ewel Juri ", "Faber Julius Paul ", "Fielen Fabienne ", "Fodera Ben ", "Franke Lynn ", "Friedebold Arne ", "Friedrichsen Finnja", "Fuchs Lilith ", "Geiger Anna Julie ", "Geisler Finn ", "Gernert Luis ", "Götz Margarita ", "Grott Vivien ", "Grüter Nele ", "Gueye Juliette Catherine ", "Gutkäß Till ", "Hagemeister Gina Violetta ", "Hanasoglu Tasnim ", "Hauswaldt Maja ", "Hecker Tobias ", "Heimpel Leroi Kaspar ", "Hein Jonne ", "Heinicke Robert ", "Hell Marlon", "Hesse Luca Leander ", "Hisso Malin Emilia ", "Hitit Eda ", "Hohr Franziska ", "Holesch Julian Simon ", "Holesch Sarah Madeleine ", "Hölter Manou Tom ", "Holzapfel Yorick Carl ", "Holzhäuser Muhammad Ali ", "Honold Hanna-Maria ", "Hörner Marlene ", "Huber Sven ", "Hummelt Pauline ", "Illes Keiko Olivia ", "Jaanine Jinane ", "Jakobi Malte", "Jakobs Simon Jonathan ", "Jansen Juleen Mia ", "Janßen Paula", "Jefferson Daniel James ", "Jehle Luca Marie ", "Jenster Ronja Diana ", "Jörns Louis ", "Jürgensen Mats Ole ", "Kabalimu Béla ", "Kachaewa Ismail", "Kaiser Franja ", "Kalde Paula", "Kamlage Jannika Marie ", "Kappe Laurin ", "Kaps Johanna", "Karabegovic Jasmina ", "Katyal Dharna ", "Kaufmann Marlene ", "Kaya Enes Emre", "Kaya Fatih ", "Kayaöz Furkan ", "Keimer Timon", "Kellendonk Lara ", "Kern Julius ", "Ketenci Nil Su ", "Kilinc Melda Elif ", "Kinay Buket ", "Kirmanidis Adam ", "Klabunde Lina ", "Klapheck Lukas ", "Klapheck Moritz ", "Klausener Sebastian ", "Klein Sophie Leni ", "Kleinbongard Marek ", "Kleine Henriette ", "Klütsch Niklas ", "Knipp Jette ", "Kohllöffel Thea Juli ", "König Merve Josefine", "Kons Sophia ", "Körschgen Jona Yunus ", "Korsido Annahita ", "Kösker Zeynep ", "Kraft Judith ", "Kraft Lotta Elisabeth", "Kremenovic Lara", "Krofta Dylan", "Kruse Marie ", "Kürten Pascal ", "Kürten Ruby", "Kurtulus Kerem ", "Küsgen Peter ", "Kuti Milan ", "Kutsch Tillmann ", "Lachenicht Luke ", "Lamers Lenart ", "Lapke Juri ", "Latoszek Giacomo Honore ", "Layer Tim ", "Lectibi Hajar Safia ", "Lill Felix", "Lingscheid-Küppers Sarah ", "Lingscheid Max ", "López Puzberg Nico ", "Ly Madlena Yan ", "Maalal Amir ", "Malangré Laura ", "Mann Laura Anna ", "Marsch Zoe Tabea ", "Matull Leo Daniel Otto ", "Matull Lotte Amelia Luise ", "Meetz Lars ", "Mehmet Alpay ", "Meisenberg Johann ", "Mert Beyza-Nur ", "Mesch Lauritz ", "Metz Paula Marie ", "Meyer Lino Kanoa ", "Mickler Johannes ", "Minor Karla Leonie ", "Mittmann Jakob ", "Mogias Ronja ", "Moser Bravo Leo ", "Moshfeghi Melissa ", "Müller Kevin Alexander ", "Mundt Neele Mari ", "Mundt Nora Maria ", "Nath Akash ", "Ndiaye Coura Johanna ", "Neelsen Arno ", "Negi Vipul ", "Neidhard Marc", "Neumann Marla ", "Nicodemo Nino ", "Nielsen Stella Levinia ", "Nikleniewicz Marvin", "Nörling Anna Maria Helene ", "Nowatschin Raphael ", "Nuradini Bleon ", "Nuradini Sara ", "Obeidi Ismail ", "Oelze Albert Johann ", "Ouardi Ilham-Safaa ", "Özel Emre ", "Özkan Davut Kerem ", "Öztürk Can ", "Öztürk Kaya ", "Panci Lilly ", "Panek Elena ", "Patt Daniel ", "Petronio Rebecca ", "Pham Quynh Nhu ", "Pillich Emma ", "Podolski Maxim ", "Pointner Anastasia ", "Pokrass Denis ", "Polz Konrad ", "Poulakos Luzia ", "Quos Sora ", "Ramirez Donnerstag Amber ", "Reimann Vincent Roque ", "Reimers Emma Sofie ", "Ritzenhoff Antonia ", "Rogge David Miguel", "Rothenbücher Benjamin", "Rother Sophie ", "Sabou Rouh Diba ", "Saher Paulin ", "Sahin Eren ", "Salz Charlotte Saskia Leonie ", "San Berna ", "Sawatzki Alek Jan ", "Schaal Luis Alexander ", "Schall Juri ", "Schaper Mascha ", "Schaper Mika ", "Schellenberger Felix ", "Scherwud Ksenia ", "Schiller Jorma David ", "Schmidt-Jensen Sebastian ", "Schnabel Emily ", "Schnieders Tim ", "Scholz Willie ", "Schreiber Charlotte ", "Schudnagies Oskar Robert ", "Schunck Josephine ", "Schuppert Jonas ", "Schwan Emma ", "Schwark Lisa ", "Schwartz Charlotte Marie ", "Schwerdt Tim ", "Sebastian Emily Fee ", "Sen Nurgül Aleyna ", "Sezek Melek ", "Simanko Timur ", "Singh Tanisha ", "Sochor Ayse ", "Söker Maria ", "Sprenger Bernhard ", "Sprenger Dominik ", "Sprenz Lewin Munir ", "Starbatty Nil ", "Staufenbeyl Marisol ", "Stobbe Thorben ", "Stock Anna Lu ", "Syring Lilly Maewa ", "Szynkowicz Seimon Julian ", "Taner Ardan ", "Tedde Alessio ", "Tekgül Fernur ", "Tekin Esra ", "Thilo Sina ", "Tokbas Saziye-Sila ", "Trigona Esmeralda Maria ", "Tritz Yannick", "Türkmen Kaan", "Uyanik Selin ", "van den Hövel Ben ", "Vila Alvarez Guillermo ", "Vischer Maxima ", "Vogel Noah Gabriel ", "von der Wettern Jonathan ", "von Reth Leandra ", "Wagner Franca ", "Wall Morris Oskar ", "Weilandt Kasimir ", "Weinhold Lena ", "Weßler Paul ", "Wilkens Maximilian ", "Wingerath Merle", "Wittkamp Felix ", "Woelk Jana ", "Wohlenberg Karolin Ann-Luise ", "Wohlfromm Luzia ", "Wojcik Karoline ", "Yilmaz Alihan ", "Zerres Jana Maria ", "Zerweck Aaron Gerhard", "Zug Henriette Marie"
    }; // Namen aller Schueler
    public static String currentname;
    private long backPressedTime;
    private AutoCompleteTextView NamenText;
    private Toast toast2;
    private CheckBox remember1;
    private TextView name1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toast2 = new Toast(this.getApplicationContext());
        customToast("", toast2);

        NamenText = findViewById(R.id.autoCompleteTextView);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, namen);
        NamenText.setAdapter(adapter);
        redirect = findViewById(R.id.redirect);
        remember1 = findViewById(R.id.remember);
        name1 = findViewById(R.id.Name1);
        SharedPreferences pref = getSharedPreferences("Name",MODE_PRIVATE);
        name1.setText(pref.getString("Namen", null));
    }


    public void activitywechsel(View view) {

        if(name1.getText().toString().equals("") && !NamenText.getText().toString().equals("")) {
            currentname = NamenText.getText().toString();
        }
        else if (!name1.getText().toString().equals("") && !NamenText.getText().toString().equals(""))
        {
            currentname = NamenText.getText().toString();
        }
        else if (!name1.getText().toString().equals("") && NamenText.getText().toString().equals("")){
            currentname = name1.getText().toString();
        }

        List<String> list = Arrays.asList(namen);
        if (list.contains(currentname)) {
            getSharedPreferences("Name",MODE_PRIVATE).edit().putString("Namen", currentname).apply();
            Intent i = new Intent(MainActivity.this, StundenplanActivity.class);
            startActivity(i);
        } else {
            if (toast2.getView().getWindowVisibility() == View.VISIBLE) {
                toast2.cancel();
            }
            customToast("Bitte gebe einen richtigen Namen ein", toast2);
            toast2.show();
        }

    }


    @Override
    public void onBackPressed() {


        if (backPressedTime + 3000 > System.currentTimeMillis()) {
            toast2.cancel();
            super.onBackPressed();
            return;
        } else {
            customToast("Drücken Sie zum Beenden erneut zurück", toast2);
            toast2.show();
        }
        backPressedTime = System.currentTimeMillis();
    }

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

}