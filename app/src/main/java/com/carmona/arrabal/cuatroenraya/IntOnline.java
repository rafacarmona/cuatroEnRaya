package com.carmona.arrabal.cuatroenraya;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.ArrayList;

public class IntOnline extends AppCompatActivity {
    String urlServer;
    RequestQueue queue;
    Button botonIa;
    Button botonOnline;
    private ListView partidas;
    private ArrayList<String> partida;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //urlServer = "192.168.115.241";
        urlServer = "192.168.1.5";
        queue = VolleySingleton.getInstance(this).getRequestQueue();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_int_online);
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
       // FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        botonOnline = (Button) findViewById(R.id.botonJugarOnline);
        botonIa = (Button) findViewById(R.id.botonJugarVsIa);
        botonOnline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "http://"+urlServer+"/conecta4/start.php";
                StringRequest request = new StringRequest(url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            //Toast.makeText(getApplicationContext(), "Ha funcionado", Toast.LENGTH_LONG).show();
                            Intent inten = new Intent(IntOnline.this , Listar.class);
                            startActivity(inten);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), "Imposible conectar", Toast.LENGTH_LONG).show();
                    }
                });
                queue.add(request);
            }
        });
        botonIa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent inten = new Intent(IntOnline.this , MainActivity.class);
                startActivity(inten);
            }
        });
    }

}
