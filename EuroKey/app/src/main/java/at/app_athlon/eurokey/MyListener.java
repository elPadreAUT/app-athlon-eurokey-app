package at.app_athlon.eurokey;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.util.Log;

/**
 * Created by bernhardkober on 01.12.16.
 */

public class MyListener implements LocationListener {

    private iCallbackInterface callback;

    public void setCallback(iCallbackInterface iCallback){
        this.callback = iCallback;
    }

    @Override
    public void onLocationChanged(Location location) {
        Log.i("LOCATION", location.getLatitude()+ ", " + location.getLongitude());

        callback.update2UI(location.getLatitude() + ":" + location.getLongitude());
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }


}
