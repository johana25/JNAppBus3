package com.example.johanadenisse.jnappbus3;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.multidex.MultiDex;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Principal extends AppCompatActivity {
    Button btnIniciar;
    EditText edtTxtUsuario;
    EditText edtTxtPass;
    String tipo_usuario;




    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(newBase);
        MultiDex.install(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        // VINCULACION ELEMENTOS DE INTERFAZ GRAFICA
        edtTxtUsuario = (EditText) findViewById(R.id.edtTxtUsuario);
        edtTxtPass = (EditText) findViewById(R.id.edtTxtPass);
        btnIniciar = (Button) findViewById(R.id.btnIniciarSesion);
    }

    private void verificarUsuario(String s, String s1) {

        if (s.equals("admin") && (s1.equals("admin"))) {
            tipo_usuario = "admin";
        } else {
            tipo_usuario = "user";

        }
        Intent intent = new Intent(getApplicationContext(), ListaRutas.class);
        Bundle bundle = new Bundle();
        bundle.putString("TIPO_USUARIO", tipo_usuario);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public void onClickIniciar(View v) {
        verificarUsuario(edtTxtUsuario.getText().toString(), edtTxtPass.getText().toString());
    }
}
