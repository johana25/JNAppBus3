package com.example.johanadenisse.jnappbus3;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

public class ListaRutas extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_rutas);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ClickLista();
    }

    private void ClickLista() {
        // SELECCIONA LA LUSTA EN PANTALLA SEGUN SU ID
        ListView lista1 = (ListView) findViewById(R.id.MiLista);
        lista1.setTextFilterEnabled(true);

        // Registra una accion para el evento Click
        lista1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle bundle = new Bundle();
                if (position == 1) {
                    double[] coordenadas = new double[2];
                    coordenadas[0] = 28.67;
                    coordenadas[1] = -106.58;

                    // Obtiene el valor de la casilla elegida
                    String itemSeleccionado = parent.getItemAtPosition(position).toString();

                    Toast.makeText(getApplicationContext(), "Mostrando Ruta de " + itemSeleccionado, Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), MapsActivity.class);

                    intent.putExtra("COORDENADAS", coordenadas);
                    bundle.putDoubleArray("COORDENADAS", coordenadas);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            }
        });
    }

}
