package com.example.johanadenisse.jnappbus3;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

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
                // Obtiene el valor de la casilla elegida
                String itemSeleccionado = parent.getItemAtPosition(position).toString();

                String tipo_usuario = getIntent().getStringExtra("TIPO_USUARIO");
                if (tipo_usuario.equals("admin")) {
                    Intent intent1 = new Intent(getApplicationContext(), grabarCoordenadas.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("NOMBRE_RUTA", itemSeleccionado);
                    intent1.putExtras(bundle);
                    startActivity(intent1);
                } else {
                    Intent intent3 = new Intent(getApplicationContext(), MapsActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("NOMBRE_RUTA", itemSeleccionado);
                    intent3.putExtras(bundle);
                    startActivity(intent3);
                }


            }
        });
    }

}
