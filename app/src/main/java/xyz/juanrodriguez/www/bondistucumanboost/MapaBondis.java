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
    public ArrayList<Marker> markers = new ArrayList<>();
    public ArrayList<Polyline> lines = new ArrayList<>();

    protected void add_marker(GoogleMap map, int linea, String ramal, LatLng coord){
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

    protected void draw_recorrido(GoogleMap map, JSONArray points, int color){
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

    private void delete_markers(ArrayList<Marker> markers){
        for(Marker m : markers){
            m.remove();
        }
    }

    private void delete_lines(ArrayList<Polyline> lines){
        for(Polyline l : lines){
            l.remove();
        }
    }
}
