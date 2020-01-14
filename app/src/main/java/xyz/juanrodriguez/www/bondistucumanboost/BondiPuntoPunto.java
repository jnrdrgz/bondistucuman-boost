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
        //testing click map
        map.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng point) {
                Log.d("DEBUG","Map clicked [" + point.latitude + " / " + point.longitude + "]");
                //Do your stuff with LatLng here
                //Then pass LatLng to other activity
            }
        });
    }

    @Override
    protected void destroy(ViewGroup main) {
        map.setOnMapLongClickListener(null);
    }
}
