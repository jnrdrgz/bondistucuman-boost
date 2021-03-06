package xyz.juanrodriguez.www.bondistucumanboost;

import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private DrawerBuilder drawerBuilder;

    private Spinner dropdown;
    private EditText et_partida, et_destino;
    private Button button_search;
    private BondiPorLinea bondiPorLinea = new BondiPorLinea();
    private BondiPuntoPunto bondiPuntoPunto = new BondiPuntoPunto();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        PrimaryDrawerItem b1 = new PrimaryDrawerItem().withIdentifier(1).withName("Por Linea");
        PrimaryDrawerItem b2 = new PrimaryDrawerItem().withIdentifier(2).withName("Personalizado");
        PrimaryDrawerItem b3 = new PrimaryDrawerItem().withIdentifier(3).withName("Punto a Punto");
        PrimaryDrawerItem b4 = new PrimaryDrawerItem().withIdentifier(4).withName("+Agregar Personalizado");
        drawerBuilder = new DrawerBuilder()
            .withActivity(this)
            //.withToolbar()
            .addDrawerItems(
                    b1,
                    //new DividerDrawerItem(),
                    b2,
                    b3,
                    b4
            )
        .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
            @Override
            public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                Log.i("DRAWER CLICK", Integer.toString(position));
                switch (position){
                    case 0:
                        if(bondiPuntoPunto.current) {
                            ViewGroup main = findViewById(R.id.insert_point);
                            bondiPuntoPunto.destroy(main);
                        }

                        if(!bondiPorLinea.current) {
                            bondiPorLinea.init(mMap,4);
                            Log.i("ISADDING", Integer.toString(bondiPorLinea.markers.size()));

                            LayoutInflater inflater = getLayoutInflater();
                            ViewGroup main = findViewById(R.id.insert_point);
                            bondiPorLinea.addSpinner(inflater,main);

                            dropdown = findViewById(R.id.spinner_lineas);
                            bondiPorLinea.fillSpinner(MapsActivity.this,dropdown);
                        }
                        break;
                    case 1:
                        if(bondiPorLinea.current) {
                            ViewGroup main = findViewById(R.id.insert_point);
                            bondiPorLinea.destroy(main);
                        }
                        if(bondiPuntoPunto.current) {
                            ViewGroup main = findViewById(R.id.insert_point);
                            bondiPuntoPunto.destroy(main);
                        }
                        //personalizado();
                        break;
                    case 2:
                        if(bondiPorLinea.current) {
                            ViewGroup main = findViewById(R.id.insert_point);
                            bondiPorLinea.destroy(main);
                        }
                        if(bondiPuntoPunto.current) {
                            ViewGroup main = findViewById(R.id.insert_point);
                            bondiPuntoPunto.destroy(main);
                        }

                        if(!bondiPuntoPunto.current) {
                            bondiPuntoPunto.init(mMap);
                            LayoutInflater inflater = getLayoutInflater();
                            ViewGroup main = findViewById(R.id.insert_point);
                            bondiPuntoPunto.addView(inflater,main);

                            et_partida = findViewById(R.id.partida_tv);
                            et_destino = findViewById(R.id.destino_tv);
                            button_search = findViewById(R.id.button_punto_punto);

                            bondiPuntoPunto.set_click_listeners(et_partida, et_destino, button_search);

                        }
                        break;
                }
                return false;
            }
        });
        drawerBuilder.withActivity(this).build();

    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        double san_miguel_lng= -65.2226000;
        double san_miguel_lat = -26.8241400;

        LatLng smlatlng = new LatLng(san_miguel_lat, san_miguel_lng);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(smlatlng));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(smlatlng, 12.0f));
    }
}
