package at.app_athlon.eurokey;

import android.*;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.File;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, iCallbackInterface {

    private GoogleMap mMap;
    private JSONArray jsonAr;
    private LocationManager lm;
    private String latLong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps2);

        lm = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

        latLong = "47.405224, 15.27275";

        getLocation();

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

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
        jsonAr = jh.convertStringToJSON(demoData);

        Log.i("---------------------", latLong);

    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        getLocation();

        Log.i("GOOGLE MAP", "MAP IS READY FOR LOAD");
        for (int i = 0; i < jsonAr.length(); i++) {
            try {
                double lat = jsonAr.getJSONObject(i).getDouble("Lat");
                double lng = jsonAr.getJSONObject(i).getDouble("Lng");
                LatLng pos = new LatLng(lat, lng);
                String desc = jsonAr.getJSONObject(i).getString("City") + ", ";
                desc += jsonAr.getJSONObject(i).getString("Street");
                String snip = jsonAr.getJSONObject(i).getString("Description");
                mMap.addMarker(new MarkerOptions().position(pos).title(desc).snippet(snip));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        Log.i("TEST LOG GPS", "ICH WAR DA UND DU NICHT");
        Log.i("TEST LOG GPS LAT LONG", latLong);

        String[] parts = latLong.split(":");
       // String[] parts2 = parts[0].split(".");
        //String[] parts3 = parts[1].split(".");

        //Double lat = Double.parseDouble(parts[0]);
        //Double log = Double.parseDouble(parts[1]);

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(47.405224, 15.27275);
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        //mMap.moveCamera(CameraUpdateFactory.zoomTo(1));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney,8));

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mMap.setMyLocationEnabled(true);
    }

    @Override
    public void update2UI(String payload) {

    }

    public void getLocation(){
        // LocationManager lm = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        MyListener ml = new MyListener();

        ml.setCallback(this);

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
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
                Log.i("$$$$$$$$$$$$$$$$$$$", latLong);
            }
        });

    }
}
