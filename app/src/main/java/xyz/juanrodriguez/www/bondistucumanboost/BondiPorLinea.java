package xyz.juanrodriguez.www.bondistucumanboost;

import android.graphics.Color;
import android.util.Log;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

import java.util.ArrayList;
import java.util.Iterator;

public class BondiPorLinea extends MapaBondis{
    private String URL = "http://158.69.206.233:84/bondis/p/";
    private int[] colors = { Color.RED, Color.GREEN, Color.BLUE, Color.BLACK, Color.MAGENTA, Color.CYAN};

    public void init(GoogleMap map, ArrayList<Marker> markers){
        requestPosition(map, markers,4);
    }

    private void requestPosition(final GoogleMap map, final ArrayList<Marker> markers, final int l){
        AsyncHttpClient client = new AsyncHttpClient();

        String url = URL + l;

        client.get(url, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, final JSONObject response){
                //Log.i("REQUESTPOS", "Json: " + response.toString());
                try{
                    Log.i("REQUESTPOS", "OK");
                    for (Iterator<String> it = response.keys(); it.hasNext();){
                        String r = it.next();
                        JSONArray posiciones = response.getJSONObject(r).getJSONArray("posiciones");
                        for(int i = 0; i<posiciones.length(); i++) {
                            Double lat = posiciones.getJSONObject(i).getDouble("latitud");
                            Double lng = posiciones.getJSONObject(i).getDouble("longitud");
                            add_marker(map, markers, l, r, new LatLng(lat,lng));
                        }
                    }

                } catch (Exception e){
                    Log.e("REQUESTPOS", e.toString());
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable e, JSONObject response){
                //build_failure_message("RequestPos", statusCode, response, e.toString());
            }
        });
    }




}
