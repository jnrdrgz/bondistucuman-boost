package xyz.juanrodriguez.www.bondistucumanboost;

import android.content.Context;
import android.text.Editable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Iterator;
import java.util.Locale;

import cz.msebera.android.httpclient.Header;

public class BondiPuntoPunto extends MapaBondis {
    public GoogleMap map;
    public boolean partida_selected;
    private String URL = "http://158.69.206.233:84/";

    LatLng ll_partida = null;
    LatLng ll_destino = null;

    public void init(GoogleMap map){
        this.map = map;
        current = true;
        partida_selected = true;
    }

    @Override
    protected void destroy(ViewGroup main) {
        map.setOnMapLongClickListener(null);
    }

    public void addView(LayoutInflater inflater, ViewGroup main){
        View view_ = inflater.inflate(R.layout.punto_punto, null);
        main.addView(view_, 0);
    }

    public void set_click_listeners(final EditText et_partida, final EditText et_destino, Button search){
        et_partida.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!partida_selected)
                    partida_selected = true;
            }
        });

        et_destino.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(partida_selected)
                    partida_selected = false;
            }
        });

        map.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng point) {
                Log.d("DEBUG","Map clicked [" + point.latitude + " / " + point.longitude + "]");
                String ts = String.format(Locale.getDefault(), "%f.6,%f.6",point.latitude, point.longitude);

                LatLng ll = new LatLng(point.latitude, point.longitude);
                if(partida_selected) {
                    et_partida.setText(ts);
                    map.addMarker(new MarkerOptions().position(ll)
                            .title("Partida"));
                    ll_partida = ll;

                } else {
                    et_destino.setText(ts);
                    map.addMarker(new MarkerOptions().position(ll)
                            .title("Destino"));
                    ll_destino = ll;
                }


            }
        });

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AsyncHttpClient client = new AsyncHttpClient();
                client.setTimeout(20000);

                String url_l = URL + "bondis/cual_tomo/";
                final String url = url_l + String.format("?plat=%s&plng=%s&dlat=%s&dlng=%s", Double.toString(ll_partida.latitude), Double.toString(ll_partida.longitude), Double.toString(ll_destino.latitude), Double.toString(ll_destino.longitude));

                client.get(url, new JsonHttpResponseHandler(){
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, final JSONObject response){
                        try{
                            Log.d("fghj", url);
                        } catch (Exception e){
                            Log.e("REQUESTALL", e.toString());
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable e, JSONObject response){
                        FailureMessageBuilder.build("RequestRec", statusCode, response, e.toString());
                    }
                });
            }
        });
    }
}
