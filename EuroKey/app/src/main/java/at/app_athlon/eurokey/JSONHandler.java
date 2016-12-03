package at.app_athlon.eurokey;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by bernhardkober on 01.12.16.
 */

public class JSONHandler {

    Context cx;

    public JSONHandler(Context cx){
        this.cx=cx;
    }
//JSONObject[]
    public  JSONArray convertStringToJSON(String data){

        try {
            JSONObject jsOb = new JSONObject(data);
            JSONArray jsAr = jsOb.getJSONArray("WC");

            //JSONObject description = jsAr.getJSONObject(0);//.getString("Street");

            return jsAr;

        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

    }

}
