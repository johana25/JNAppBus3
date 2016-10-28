package com.example.johanadenisse.jnappbus3;

import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.location.LocationListener;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    // CREAMOS UN OBJETO MARKER PARA EL MARCADOR Y DECLARAMOS DOS VARIABLES PARA LA LATITUD Y LA LONGITUD DE NUESTRA POSICION ACTUAL
    private GoogleMap mMap;
    private Marker marcador;
    double lat = 0.0;
    double lng = 0.0;

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
        miUbicacion();

    }

    // CREAMOS UN METODO QUE NOS SERVIRA PARA AGREGAR UN MARCADOR EN EL MAPA, CREAREMOS UN OBJETO LatLng, EN EL CUAL INCLUIREMOS LA LATITUD Y LONGITUD,
    // LUEGO UTILIZANDO EL ELEMENTO CameraUpdate, CENTRAREMOS LA CAMARA A LA POSICION DE NUESTRO MARKER
    private void agregarMarcador(double lat, double lng) {
        LatLng coordenadas = new LatLng(lat, lng);
        CameraUpdate miUbicacion = CameraUpdateFactory.newLatLngZoom(coordenadas, 16);

        //SI EL MARCADOR ES DIFERENTE DE NULL SE DEBERA REMOVER. SEGUIDAMENTE AGREGAMOS ALGUNAS PROPIEDADES A NUESTRO MARKER: UN TITULO Y UNA IMAGEN.
        // POR ULTIMO AGREGAMOS EL METODO animateCamera PARA MOVER LA CAMARA DESDE UNA POSICION A OTRA CON ANIMACION
        if (marcador != null) marcador.remove();
        marcador = mMap.addMarker(new MarkerOptions()
                .position(coordenadas)
                .title("Mi ubicacion actual")
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_launcher)));
        mMap.animateCamera(miUbicacion);
    }

    //CREAMOS UN METODO QUE NOS SERVIDAR PARA OBTENER LA LATITUD Y LONGITUD DE NUESTRA POSICION ACTUAL
    private void actualizaUbicacion(Location location) {
        if (location != null) {
            lat = location.getLatitude();
            lng = location.getLongitude();
            agregarMarcador(lat, lng);
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
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,15000,0, locListener);
    }
}
