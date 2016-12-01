package com.example.johanadenisse.jnappbus3;

import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolylineOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    // CREAMOS UN OBJETO MARKER PARA EL MARCADOR Y DECLARAMOS DOS VARIABLES PARA LA LATITUD Y LA LONGITUD DE NUESTRA POSICION ACTUAL
    private GoogleMap mMap;

    double lat = 0.0;
    double lng = 0.0;
    Button btnMostrarLineas;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        //    btnMostrarLineas = (Button) findViewById(R.id.btnMostrarLineas);

    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        miUbicacion();
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//
            return;
        }
        mMap.setMyLocationEnabled(true);
        mMap.getUiSettings().setZoomControlsEnabled(true);
        //mMap.getUiSettings().setZoomGesturesEnabled(true);

        // RECUPERA LAS COORDENAS Y DIBUJA LA RUTA EN EL MAPA
        double[] coordenadas = getIntent().getDoubleArrayExtra("COORDENADAS");
        Toast.makeText(this, "Coordenadas = " + coordenadas[0] + "    " + coordenadas[1], Toast.LENGTH_SHORT).show();
        MostrarLineas(coordenadas[0], coordenadas[1]);

    }


    //CREAMOS UN METODO QUE NOS SERVIDAR PARA OBTENER LA LATITUD Y LONGITUD DE NUESTRA POSICION ACTUAL
    private void actualizaUbicacion(Location location) {
        if (location != null) {
            lat = location.getLatitude();
            lng = location.getLongitude();
            // agregarMarcador(lat, lng);
        }
    }

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

    private void miUbicacion() {
        // VALIDAR PERMISOS DE GPS
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        actualizaUbicacion(location);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 15000, 0, locListener);
    }

    private void MostrarLineas(double Lat1, double Lng1) {
        //Dibujo con lineas

        PolylineOptions lineas = new PolylineOptions()
                .add(new LatLng(Lat1, Lng1))
                .add(new LatLng(28.68, -106.08))
                .add(new LatLng(28.69, -106.09));

        lineas.width(8);
        lineas.color(Color.RED);
        mMap.addPolyline(lineas);
    }

}
