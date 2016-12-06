package com.example.johanadenisse.jnappbus3;

import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolylineOptions;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    double[] coordenadas;
    ArrayList<LatLng> coorList = new ArrayList<LatLng>();
    double lat = 0.0;
    double lng = 0.0;
    String[] ss;

    // IMPLEMENTAMOS UN OBJETO DE TIPO LocationListener, EL CUAL SIEMPRE ESTA ATENTO AL CUALQUIER CAMBIO DE LA LOCALIDAD PROVENIENTE DEL GPS DEL DISPOSITIVO
    android.location.LocationListener locListener = new android.location.LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            // LLAMAMOS A NUESTRO METODO PARA REFRESCAR LA UBICACION EN EL MAPA
            actualizaUbicacion(location);
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    };
    // CREAMOS UN OBJETO MARKER PARA EL MARCADOR Y DECLARAMOS DOS VARIABLES PARA LA LATITUD Y LA LONGITUD DE NUESTRA POSICION ACTUAL
    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mMap.setMyLocationEnabled(true);
        mMap.getUiSettings().setZoomControlsEnabled(true);

        // RECUPERA LAS COORDENAS Y DIBUJA LA RUTA EN EL MAPA
        //double[] coordenadas = getIntent().getDoubleArrayExtra("COORDENADAS");

        miUbicacion();
        //MostrarLineas();
        leerArchivo();
        moverSobreChihuahua();
    }

    private void moverSobreChihuahua() {
        CameraUpdate camUpd1 = CameraUpdateFactory.newLatLngZoom(new LatLng(28.6678986, -106.0796748), 12);
        mMap.moveCamera(camUpd1);
    }

    //CREAMOS UN METODO QUE NOS SERVIDAR PARA OBTENER LA LATITUD Y LONGITUD DE NUESTRA POSICION ACTUAL
    private void actualizaUbicacion(Location location) {
        if (location != null) {
            lat = location.getLatitude();
            lng = location.getLongitude();

        }
    }

    private void miUbicacion() {
        // VALIDAR PERMISOS DE GPS
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        actualizaUbicacion(location);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10000, 0, locListener);
    }

    private void MostrarLineas() {

        PolylineOptions lineas = new PolylineOptions().addAll(coorList);
        lineas.width(5);
        lineas.color(Color.RED);
        mMap.addPolyline(lineas);


        //return;
    }

    public void leerArchivo() {
        String nombre_ruta = getIntent().getStringExtra("NOMBRE_RUTA");
        try {
            BufferedReader fin = new BufferedReader(new InputStreamReader(openFileInput(nombre_ruta + ".txt")));

            String s = "";
            // int charRead;
            while ((fin.readLine()) != null) {
                // Convertimos los char a String
                String readString = fin.readLine();
                s += readString;
                // Convertir los valores del archivo en Double, para posteriormente convertilos a LatLng
                ss = s.split(",");


            }
            coordenadas = new double[ss.length];
            for (int valor = 0; valor < (ss.length - 1); valor += 2) {
                coordenadas[valor] = Double.parseDouble(ss[valor]);
                coordenadas[valor + 1] = Double.parseDouble(ss[valor + 1]);
                coorList.add(new LatLng(coordenadas[valor], coordenadas[valor + 1]));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        MostrarLineas();
    }

}
