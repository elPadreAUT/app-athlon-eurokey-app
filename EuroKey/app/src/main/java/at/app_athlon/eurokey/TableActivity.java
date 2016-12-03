package at.app_athlon.eurokey;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.File;
import java.lang.reflect.Array;

public class TableActivity extends AppCompatActivity implements iCallbackInterface {

    private LocationManager lm;
    private String latLong;
    ListView list_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table);

        lm = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

        latLong = "47.405224, 15.27275";

        // internal storage build
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
        JSONArray jsonAr = jh.convertStringToJSON(demoData);

        String[][] demaDatas = new String[10][4];

        try {
            for(int i=0;i<10;i++){
                if( jsonAr.getJSONObject(i).getString("City").contains("Bruck")) {
                    demaDatas[i][0] = jsonAr.getJSONObject(i).getString("Street");
                }else{
                    demaDatas[i][0] = jsonAr.getJSONObject(i).getString("City");
                }
                demaDatas[i][1] = "4 min";
                demaDatas[i][2] = "400 m";
                demaDatas[i][3] = jsonAr.getJSONObject(i).getString("City");


            }
        } catch (JSONException e) {
            e.printStackTrace();
            Log.i("TABLEACTIVITY", e.toString());
        }

        getLocation();


        RowClass rowClassData[] = new RowClass[] {
                new RowClass(R.drawable.logo,demaDatas[0][0],"1 min    100 m"),
                new RowClass(R.drawable.logo,demaDatas[1][0],"2 min    200 m"),
                new RowClass(R.drawable.logo,demaDatas[2][0],"4 min    400 m"),
                new RowClass(R.drawable.logo,demaDatas[3][0],"7 min    700 m"),
                new RowClass(R.drawable.logo,demaDatas[4][0],"15 min    1.5 km"),
                new RowClass(R.drawable.logo,demaDatas[5][0],"16 min   1.6 km"),
                new RowClass(R.drawable.logo,demaDatas[6][0],"10 km"),
                new RowClass(R.drawable.logo,demaDatas[7][0],"50 km"),
                new RowClass(R.drawable.logo,demaDatas[8][0],"55 km"),
                new RowClass(R.drawable.logo,demaDatas[9][0],"65 km"),
        };

        Adapter adapter = new Adapter(this, R.layout.list_view_item_row, rowClassData);

        list_view = (ListView) findViewById(R.id.list_view);

        View header = (View) getLayoutInflater().inflate(R.layout.list_header_row,null);
        list_view.addHeaderView(header);
        list_view.setAdapter(adapter);

        //Onclick items in listview
        list_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                String toilette = (String) adapterView.getItemAtPosition(position).toString();
                TextView details = (TextView) findViewById(R.id.tv);
                String street = details.getText().toString();
                TextView details1 = (TextView) findViewById(R.id.tv1);
                String city = details1.getText().toString();
                Toast.makeText(view.getContext(), city+", " + street, Toast.LENGTH_SHORT).show();

                Intent s = new Intent(view.getContext(), DetailsActivity.class);

                s.putExtra("position", position);

                startActivity(s);

            }
        });


    }

    public void getLocation(){
        LocationManager lm = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        MyListener ml = new MyListener();

        ml.setCallback(this);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Log.i("LOCATION", "PERMISSIONS GRANTED");
        }

        lm.requestLocationUpdates(
                LocationManager.GPS_PROVIDER,
                400,
                1,
                ml
        );

        ml.setCallback(new iCallbackInterface() {

            @Override
            public void update2UI(String payload) {
                if(!payload.isEmpty())
                    latLong = payload;
            }
        });

    }

    @Override
    public void update2UI(String payload) {
    }

    public void jumpToMap(View view){
        Toast.makeText(this, "Button clicked", Toast.LENGTH_SHORT).show();

        Intent i=new Intent(TableActivity.this, MapsActivity.class);
        startActivity(i);
    }
}
