package com.example.johanadenisse.jnappbus3;

import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

public class grabarCoordenadas extends AppCompatActivity {
    final int READ_BLOCK_SIZE = 100;
    double lat = 0.0;
    double lng = 0.0;
    double lat1 = lat;
    double lng1 = lng;

    Button btnGrabar;
    Button btnDetener;
    TextView txtVw;
    ArrayList<LatLng> coorList = new ArrayList<LatLng>();
    private MiTareaAsincrona tarea1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grabar_coordenadas);

        // VINCULACION CON LA UI
        txtVw = (TextView) findViewById(R.id.txtVwItems);
        btnDetener = (Button) findViewById(R.id.btnDetener);
        btnGrabar = (Button) findViewById(R.id.btnGrabarCoordenadas);


    }

    public void onClickGrabar(View v) {
        tarea1 = new MiTareaAsincrona();
        tarea1.execute();
    }

    public void onClickDetener(View v) {
        if (tarea1 != null)
            tarea1.cancel(true);
        //leerArchivo();

    }

    public void guardarArchivos(double lat, double lng) throws IOException {
        String nombre_ruta = getIntent().getStringExtra("NOMBRE_RUTA");
        try {
            OutputStreamWriter fout = new OutputStreamWriter(openFileOutput(nombre_ruta + ".txt", Context.MODE_APPEND));
            fout.append("\n" + lat + "," + lng + ",");
            fout.close();
        } catch (Exception ex) {
            Log.e("Archivos", "Error al escribir en el archivo");
        }
    }


    private class MiTareaAsincrona extends AsyncTask<Void, Integer, Boolean> {
        android.location.LocationListener locListener = new android.location.LocationListener() {
            @Override
            public void onLocationChanged(Location location) {

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

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            miUbicacion();
            Toast.makeText(grabarCoordenadas.this, "Comenzando Tarea!!!", Toast.LENGTH_SHORT).show();

        }

        private void miUbicacion() {
            // VALIDAR PERMISOS DE GPS
            if (ActivityCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                return;
            }
            LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            actualizaUbicacion(location);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 0, locListener);
            //Toast.makeText(this,"Latidud: "+lat+"\bLongitud: "+lng,Toast.LENGTH_LONG).show();
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


        }

        private void actualizaUbicacion(Location location) {
            if (location != null) {
                lat = location.getLatitude();
                lng = location.getLongitude();
            }
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            Toast.makeText(grabarCoordenadas.this, "Terminado", Toast.LENGTH_SHORT).show();

        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            int progreso = values[0].intValue();

            if ((lat != 0) && (lng != 0)) {
                txtVw.setText("\nlat=" + lat + "\nlng=" + lng + "\n \n" + progreso);
                try {
                    guardarArchivos(lat, lng);
                } catch (IOException e) {
                    e.printStackTrace();

                }
            }

        }

        @Override
        protected void onCancelled(Boolean aBoolean) {
            super.onCancelled(aBoolean);
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            int contador = 0;
            while (true) {
                miUbicacion();
                if ((lat1 != lat) && (lng1 != lng)) {
                    contador += 1;
                    publishProgress(contador);
                    lat1 = lat;
                    lng1 = lng;
                }
                if (isCancelled())
                    break;


            }
            return true;
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
            Toast.makeText(grabarCoordenadas.this, "Tarea Cancelada!!!", Toast.LENGTH_LONG).show();
        }
    }

}



