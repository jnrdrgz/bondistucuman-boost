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

import java.util.Locale;

public class BondiPuntoPunto extends MapaBondis {
    public GoogleMap map;
    public boolean partida_selected;

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
                String ts = String.format(Locale.getDefault(), "%f.6, %f.6",point.latitude, point.longitude);
                if(partida_selected) {
                    et_partida.setText(ts);
                } else {
                    et_destino.setText(ts);
                }
            }
        });

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
