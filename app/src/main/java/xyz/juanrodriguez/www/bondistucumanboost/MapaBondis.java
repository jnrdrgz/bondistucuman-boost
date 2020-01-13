package xyz.juanrodriguez.www.bondistucumanboost;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import org.json.JSONArray;

import java.util.ArrayList;

public abstract class MapaBondis {
    protected void add_marker(GoogleMap map, ArrayList<Marker> markers, int linea, String ramal, LatLng coord){
        Marker marker;

        marker = map.addMarker(new MarkerOptions()
                .position(coord)
                .title(Integer.toString(linea))
                .snippet(ramal)
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_launcher)));

        if(marker != null) {
            markers.add(marker);
        }
    }

    protected void draw_recorrido(GoogleMap map, ArrayList<Polyline> lines, JSONArray points, int color){
        ArrayList<LatLng> points_l = new ArrayList<>();
        for(int i = 0; i<points.length();i++) {
            try {
                points_l.add(new LatLng(points.getJSONArray(i).getDouble(0), points.getJSONArray(i).getDouble(1)));
            } catch (Exception e){

            }
        }

        Polyline line = map.addPolyline(new PolylineOptions()
                .addAll(points_l)
                .width(5)
                .color(color));

        lines.add(line);
    }
}
