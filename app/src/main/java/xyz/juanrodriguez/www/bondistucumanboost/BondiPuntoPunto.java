package xyz.juanrodriguez.www.bondistucumanboost;

import android.util.Log;
import android.view.ViewGroup;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;

public class BondiPuntoPunto extends MapaBondis {
    public GoogleMap map;
    public void init(GoogleMap map){
        this.map = map;
        current = true;

        map.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng point) {
                Log.d("DEBUG","Map clicked [" + point.latitude + " / " + point.longitude + "]");
            }
        });
    }

    @Override
    protected void destroy(ViewGroup main) {
        map.setOnMapLongClickListener(null);
    }
}
