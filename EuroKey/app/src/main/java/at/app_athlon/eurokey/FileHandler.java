package at.app_athlon.eurokey;

import android.content.Context;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

/**
 * Created by bernhardkober on 01.12.16.
 */

public class FileHandler {

    private Context cx;

    public FileHandler(Context cx){
        this.cx=cx;
    }

    private static final String DATA = "{'WC':[{'City':'Bruck a.d. Mur','Street':'Bahnweg 9','Description':'Liechtensteinfriedhof','Type':'W','Lat':'47.405224','Lng':'15.27275','points':'4'},{'City':'Bruck a.d. Mur','Street':'Dr.-Theodor-Koerner-Str. 34','Description':'Bezirkshauptmannschaft Bruck a.d.Mur','Type':'W','Lat':'47.408217','Lng':'15.264607','points':'3'},{'City':'Bruck a.d. Mur','Street':'Koloman-Wallisch-Platz 1','Description':'Stadt Bruck','Type':'W','Lat':'47.410560','Lng':'15.268727','points':'5'},{'City':'Bruck a.d. Mur','Street':'Mitterg. 11-15','Description':'Altstadtgalerie','Type':'W','Lat':'47.409930','Lng':'15.26861','points':'4'},{'City':'Bruck a.d. Mur','Street':'Murinsel 1','Description':'Stadion-Murinsel','Type':'W','Lat':'47.406078','Lng':'15.254672','points':'2'},{'City':'Bruck a.d. Mur','Street':'Murkai','Description':'Hochgarage','Type':'W','Lat':'47.407105','Lng':'15.259571','points':'3'},{'City':'Frohnleiten','Street':'Brucker Str. 2','Description':'Tech. Marketingcenter Frohnleiten GmbH','Type':'W','Lat':'47.271923','Lng':'15.328648','points':'4'},{'City':'Graz','Street':'Albert-Schweizer-G. 36a','Description':'Oeverparksee-Park','Type':'W','Lat':'47.070714','Lng':'15.439504','points':'3'},{'City':'Graz','Street':'Albrechtg. 1','Description':'Grazer Congress','Type':'W','Lat':'47.070025','Lng':'15.437509','points':'3'},{'City':'Graz','Street':'Andreas-Hofer-Platz 15','Description':'Holding AG - Stadtwerke','Type':'W','Lat':'47.068621','Lng':'15.436685','points':'2'},{'City':'Graz','Street':'Andreas-Hofer-Platz 16','Description':'GBG Hausverwaltung (Holding Graz GmbH)','Type':'W','Lat':'47.068844','Lng':'15.436631','points':'3'},{'City':'Graz','Street':'Auenbruggerplatz 1','Description':'LKH Graz-Objekt Eingangszentrum','Type':'W','Lat':'47.079319','Lng':'15.465423','points':'5'},{'City':'Graz','Street':'Babenbergerstr. 33','Description':'AMS-Steiermark','Type':'W','Lat':'47.074056','Lng':'15.419988','points':'3'}]}";
    private static final String FILENAME = "wcData";

    public void createFileWithData(){

        Log.i("Prepare FILEHANDLER", "Start File handling");
        try {
            FileOutputStream fos = cx.openFileOutput(FILENAME, Context.MODE_PRIVATE);
            fos.write(DATA.getBytes());
            fos.close();
            Log.i("Prepare FILEHANDLER", "File written DONE!");

        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }

    public String getDataFromFile() {
        try {
            FileInputStream fis = cx.openFileInput(FILENAME);

            int c;
            String tmp = "";

            while ((c = fis.read()) != -1) {
                tmp = tmp + Character.toString((char) c);
            }
            fis.close();

            Log.i("Output FILEHANDLER", "File read DONE!");

            return tmp;

        } catch (java.io.IOException e) {
            e.printStackTrace();
            return e.toString();
        }
    }

}
