package xyz.juanrodriguez.www.bondistucumanboost;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

import java.util.ArrayList;
import java.util.Iterator;

public class BondiPorLinea extends MapaBondis{
    private String URL = "http://158.69.206.233:84/bondis/";
    //todo: make map a var inside the method
    private GoogleMap map;

    public void init(GoogleMap map, int l){
        this.map = map;

        requestPosition(map,l);
        requestRecorrido(map, l);

        current = true;
    }

    private void requestPosition(final GoogleMap map, final int l){
        AsyncHttpClient client = new AsyncHttpClient();

        String url = URL + "p/" + l;

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
                            add_marker(map, l, r, new LatLng(lat,lng));
                        }
                    }

                } catch (Exception e){
                    Log.e("REQUESTPOS", e.toString());
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable e, JSONObject response){
                FailureMessageBuilder.build("RequestPos", statusCode, response, e.toString());
            }
        });


    }

    private void requestRecorrido(final GoogleMap map, int l){
        AsyncHttpClient client = new AsyncHttpClient();

        String url = URL + "r/" + l;

        client.get(url, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, final JSONObject response){
                int current_color = 0;
                try{
                    for ( Iterator<String> it = response.keys();  it.hasNext();){
                        String r = it.next();
                        JSONArray pts = response.getJSONObject(r).getJSONArray("puntos");
                        draw_recorrido(map, pts, colors[current_color]);
                        current_color++;
                    }

                } catch (Exception e){
                    Log.e("REQUESTPOS", e.toString());
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable e, JSONObject response){
                FailureMessageBuilder.build("RequestRec", statusCode, response, e.toString());
            }
        });
    }

    public void fillSpinner(final Context context, final Spinner dropdown){
        AsyncHttpClient client = new AsyncHttpClient();

        String url = URL + "lineas";

        client.get(url, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, final JSONObject response){
                try{
                    JSONArray ls = response.getJSONArray("numeros");

                    ArrayList<String> ls_ad = new ArrayList<>();
                    for(int i = 0; i<ls.length(); i++){
                        ls_ad.add(ls.getString(i));
                    }

                    fillSpinner(context, dropdown, ls_ad);

                } catch (Exception e){
                    Log.e("FILLSPIN", e.toString());
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable e, JSONObject response){
                FailureMessageBuilder.build("FillSpinner", statusCode, response, e.toString());
            }
        });
    }

    public void addSpinner( LayoutInflater inflater,  ViewGroup main){
        View view_ = inflater.inflate(R.layout.spinnerlineas, null);
        main.addView(view_, 0);
    }

    private void fillSpinner(Context context, Spinner dropdown, ArrayList<String> data){
        if(dropdown == null){
            Log.d("DROPDOWNNULL", "dropdown is null");
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_dropdown_item, data);

        dropdown.setAdapter(adapter);

        dropdown.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                        delete_lines();
                        delete_markers();

                        Object item = parent.getItemAtPosition(pos);
                        int sel = Integer.parseInt(item.toString());

                        requestPosition(map, sel);
                        requestRecorrido(map, sel);
                    }
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });
    }

    @Override
    protected void destroy(ViewGroup main) {
        delete_lines();
        delete_markers();
        current = false;

        main.removeAllViews();
    }
}
