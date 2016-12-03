package at.app_athlon.eurokey;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.File;

public class DetailsActivity extends AppCompatActivity implements OnMapReadyCallback {

    private int position;
    private GoogleMap mMap;
    private JSONArray jsonAr;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    String[][] demaDatas = new String[10][5];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        Bundle extras = getIntent().getExtras();

        if (extras == null) {

        } else {
            position = extras.getInt("position");
        }

        FileHandler fh = new FileHandler(getApplicationContext());

        //check if exists
        File file = getBaseContext().getFileStreamPath("wcData");

        //if(!file.exists()) {
        //Log.i("TABLEACTIVITY", "FILE does NOT exist");
        //  create internal storage with demo data
        fh.createFileWithData();
        //}

        //read the internal storage
        String demoData = fh.getDataFromFile();

        JSONHandler jh = new JSONHandler(getApplicationContext());

        //Get JSON Data from internal storage
        jsonAr = jh.convertStringToJSON(demoData);


        int[] ratings = new int[10];

        try {
            for (int i = 0; i < 10; i++) {
                demaDatas[i][0] = jsonAr.getJSONObject(i).getString("City");
                demaDatas[i][1] = jsonAr.getJSONObject(i).getString("Street");
                demaDatas[i][3] = jsonAr.getJSONObject(i).getString("Description");
                ratings[i] = jsonAr.getJSONObject(i).getInt("points");
                demaDatas[i][4] = jsonAr.getJSONObject(i).getString("Lat")+","+jsonAr.getJSONObject(i).getString("Lng");
            }

            demaDatas[0][2] = "1 min    100 m";
            demaDatas[1][2] = "2 min    200 m";
            demaDatas[2][2] = "4 min    400 m";
            demaDatas[3][2] = "7 min    700 m";
            demaDatas[4][2] = "15 min   1.5 km";
            demaDatas[5][2] = "16 min   1.6 km";
            demaDatas[6][2] = "10 km";
            demaDatas[7][2] = "50 km";
            demaDatas[8][2] = "55 km";
            demaDatas[9][2] = "65 km";

        } catch (JSONException e) {
            e.printStackTrace();
            Log.i("TABLEACTIVITY", e.toString());
        }

        TextView details1 = (TextView) findViewById(R.id.details2);
        details1.setText(demaDatas[position - 1][0]);

        TextView details3 = (TextView) findViewById(R.id.details3);
        details3.setText(demaDatas[position - 1][2]);

        TextView details2 = (TextView) findViewById(R.id.details);
        details2.setText(demaDatas[position - 1][1] + "     |       " + demaDatas[position - 1][3]);

        RatingBar ratbar = (RatingBar) findViewById(R.id.ratingBar);
        ratbar.setIsIndicator(true);
        ratbar.setRating(ratings[position -1]);

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    public void startNav(View v){
        Intent i2 = new Intent(Intent.ACTION_VIEW, Uri.parse("http://maps.google.com/maps?daddr="+demaDatas[position-1][4]));
        startActivity(i2);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        LatLng pos = new LatLng(0, 0);
        String desc = "";
        String snip = "";

        try {
            double lat = jsonAr.getJSONObject(position - 1).getDouble("Lat");
            double lng = jsonAr.getJSONObject(position -1).getDouble("Lng");
            pos = new LatLng(lat,lng);
            desc = jsonAr.getJSONObject(position -1).getString("City") + ", ";
            desc += jsonAr.getJSONObject(position -1).getString("Street");
            snip = jsonAr.getJSONObject(position -1).getString("Description");

            mMap.addMarker(new MarkerOptions().position(pos).title(desc).snippet(snip));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(pos,15));

        } catch (JSONException e){
            e.printStackTrace();
        }
    }
}
