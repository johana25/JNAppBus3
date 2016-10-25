package com.example.johanadenisse.jnappbus3;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;

public class Principal extends AppCompatActivity {
    Button btn1, btn2;
    EditText edtxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        btn1 =(Button)findViewById(R.id.btn1);
        btn2 =(Button)findViewById(R.id.btn2);
        edtxt = (EditText) findViewById(R.id.edtxt);



        /*Intent intent = new Intent (this, Secundaria.class);
        intent.putExtra("Lista");
        startActivity(intent);*/
            btn2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(),GPS.class);
                    startActivity(intent);
                }
            });
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(getApplicationContext(),Secundaria.class);
                startActivity(intent1);
            }
        });


    }

    //@Override
    //public void onClick(View v) {
      //  switch (v.getId()){
        //    case R.id.btn1:
          //      Intent intent = new Intent(this,Secundaria.class);
            //    startActivity(intent);
              //  break;
            //default:
              //  break;
        //}
        //switch (v.getId()){
          //  case R.id.btn2:
            //    Intent intent = new Intent(this,GPS.class);
              //  startActivity(intent);
                //break;
            //default:
              //  break;
       // }
    //}

}
