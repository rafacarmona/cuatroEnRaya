package com.carmona.arrabal.cuatroenraya;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class Listar extends AppCompatActivity {

    private ListView listView;
    private Button crear;
    private ArrayList<String> partida;
    //private String urlServer = "192.168.115.241";
    private String urlServer = "192.168.1.5";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar);
        mostrarPartidas();
        crear = (Button) findViewById(R.id.crear);

        crear.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v){
                askDialogConf();
            }
        });

        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mostrarPartidas();
                    }
                });
            }
        }, 3000, 5000);
    }

    private void crearJuego(){
        final RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://"+urlServer+"/conecta4/start.php";
        StringRequest request = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    String id = "";
                    DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
                    DocumentBuilder db = dbf.newDocumentBuilder();
                    InputSource is = new InputSource();
                    is.setCharacterStream(new StringReader(response));
                    Document doc = db.parse(is);
                    NodeList nodes = doc.getElementsByTagName("game");

                    for (int i = 0; i < nodes.getLength(); i++) {
                        Element element = (Element) nodes.item(i);
                        id = element.getAttribute("id");
                    }

                    Intent intent = new Intent(Listar.this, MainActivity.class);

                    Bundle b = new Bundle();
                    b.putString("id", id);
                    b.putString("turno", "1");
                    b.putString("estado", "J");

                    intent.putExtras(b);
                    startActivity(intent);
                }catch (Exception e){}
                listar(partida);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast toast1 = Toast.makeText(getApplicationContext(), "Error al crear partida.", Toast.LENGTH_SHORT);

                toast1.show();
            }
        });

        queue.add(request);
    }
    private void mostrarPartidas(){
        partida = new ArrayList<String>();
        final RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://"+urlServer+"/conecta4/game.php";
        //String url = "http://127.0.0.1/conecta4/game.php";
        StringRequest request = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
                    DocumentBuilder db = dbf.newDocumentBuilder();
                    InputSource is = new InputSource();
                    is.setCharacterStream(new StringReader(response));
                    Document doc = db.parse(is);
                    NodeList nodes = doc.getElementsByTagName("game");

                    for (int i =  0; i<nodes.getLength(); i++){
                        Element element = (Element) nodes.item(i);
                        partida.add("Partida con ID: "+element.getAttribute("id")+"\n");
                    }
                    listar(partida);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast toast1 = Toast.makeText(getApplicationContext(), "Hubo un error al cargar la lista", Toast.LENGTH_SHORT);

                toast1.show();
            }
        });


        queue.add(request);
    }

    private void askDialogConf(){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("¿Quieres crear una partida?");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                crearJuego();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }

    public void listar(ArrayList<String> partida){

        ArrayAdapter adapter = new ArrayAdapter(this,
                android.R.layout.simple_list_item_1, partida);
        listView = (ListView) findViewById(R.id.lista);

        listView.setAdapter(adapter);
    }

    private AdapterView.OnItemClickListener MessageClickedHandler = new AdapterView.OnItemClickListener() {
        public void onItemClick(AdapterView parent, View v, int position, long id) {
            listView.setBackgroundColor(Color.YELLOW);
        }
    };

}
