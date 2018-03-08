package com.carmona.arrabal.cuatroenraya;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

public class Inicio extends AppCompatActivity {

    Button botonJugarVsIa;
    Button botonJugarOnline;
    Button botonConfiguración;
    Button botonAboutUs;
    Button botonExit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);
        botonJugarVsIa = (Button) findViewById(R.id.botonJugarVsIa);
        botonJugarOnline = (Button) findViewById(R.id.botonJugar);
        botonConfiguración = (Button) findViewById(R.id.botonSettings);
        botonAboutUs = (Button) findViewById(R.id.botonAboutUs);
        botonExit = (Button) findViewById(R.id.botonExit);

        botonJugarOnline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent inten = new Intent(Inicio.this , IntOnline.class);
                startActivity(inten);
            }
        });
        botonConfiguración.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //llamada a la funcion
                Intent inten = new Intent(Inicio.this , Settings.class);
                startActivity(inten);
            }
        });
        botonAboutUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //llamada a la funcion
                Intent inten = new Intent(Inicio.this , About.class);
                startActivity(inten);
            }
        });
        botonExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //llamada a la funcion
                finish();
                System.exit(0);
                /*Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);*/

            }
        });
    }
}
