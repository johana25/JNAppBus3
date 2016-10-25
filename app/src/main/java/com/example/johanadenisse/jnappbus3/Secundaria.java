package com.example.johanadenisse.jnappbus3;

import android.app.Instrumentation;
import android.app.ListActivity;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import static com.example.johanadenisse.jnappbus3.R.id.ListaView1;
import static com.example.johanadenisse.jnappbus3.R.id.list;
import static com.example.johanadenisse.jnappbus3.R.id.listView;

public class Secundaria extends ListActivity {


    ListView List;
  String [] Lista = {"Ruta2", "Circunvalacion1","Circunvalacion2","Tarahumara","Sector3","Ruta3","Circuito Universitario",
            "Nombre de Dios","Tec2","20 Aniversario","Auxiliar 03","Auxiliar 06","Riberas","Jardinez","Granjas Saucito",""};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_secundaria);
        List =(ListView)findViewById(ListaView1);

        //ArrayAdapter<String> adaptador = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1);
        //List.setAdapter(adaptador);
         setListAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,Lista));

        List.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(),"Has pulsado el boton" + position, Toast.LENGTH_SHORT).show();
            }
        });



     /*FloatingActionButton Lista = (FloatingActionButton) findViewById(R.id.lista);
        Lista.setOnClickListener(new View.OnClickListener() {

            @Override
    public void onClick(View view) {
        Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
        .setAction("Action", null).show();
        }
        });*/



    }
}




