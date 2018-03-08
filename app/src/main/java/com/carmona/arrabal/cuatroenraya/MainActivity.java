package com.carmona.arrabal.cuatroenraya;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AlertDialogLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Surface;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    //variable que almacena los botones.
    Game juego;
    Boolean musica;
    private final int ids[][] = {
            {R.id.f0c0Button, R.id.f0c1Button,R.id.f0c2Button,R.id.f0c3Button,R.id.f0c4Button,R.id.f0c5Button, R.id.f0c6Button},
            {R.id.f1c0Button, R.id.f1c1Button,R.id.f1c2Button,R.id.f1c3Button,R.id.f1c4Button,R.id.f1c5Button, R.id.f1c6Button},
            {R.id.f2c0Button, R.id.f2c1Button,R.id.f2c2Button,R.id.f2c3Button,R.id.f2c4Button,R.id.f2c5Button, R.id.f2c6Button},
            {R.id.f3c0Button, R.id.f3c1Button,R.id.f3c2Button,R.id.f3c3Button,R.id.f3c4Button,R.id.f3c5Button, R.id.f3c6Button},
            {R.id.f4c0Button, R.id.f4c1Button,R.id.f4c2Button,R.id.f4c3Button,R.id.f4c4Button,R.id.f4c5Button, R.id.f4c6Button},
            {R.id.f5c0Button, R.id.f5c1Button,R.id.f5c2Button,R.id.f5c3Button,R.id.f5c4Button,R.id.f5c5Button, R.id.f5c6Button}
    };
    int columnaMaquina;
    int filaMaquina;
    int jugadorEmpieza;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        columnaMaquina = 0;
        filaMaquina = 0;
        SharedPreferences prefs  = getSharedPreferences("MisPreferencias", Context.MODE_PRIVATE);
        super.onCreate(savedInstanceState);
        jugadorEmpieza = prefs.getBoolean("jugadorEmpieza", false) ? Game.JUGADOR : Game.MAQUINA;
        juego = new Game(jugadorEmpieza);
        if(getRotation(getApplicationContext()).equals("vertical")){
            setContentView(R.layout.activity_main);
        }else{
            setContentView(R.layout.activity_main);
        }

        musica = prefs.getBoolean("musica", false);

    }

    /* Musica */
    public void onResume(){
        super.onResume();
        if(musica) {
            Music.play(this, R.raw.cancion);
        }
    }

    public void onPause(){
        super.onPause();
        Music.stop(this);
    }

    public void onClick(View v){
        int id = v.getId();
        //ImageButton boton = (ImageButton) v;
        int column = coorJuego(id);
        switch (juego.getEstado()){
            case 'G':
                Toast.makeText(this,"Partida terminada",Toast.LENGTH_SHORT);
                break;
            case 'T':
                Toast.makeText(this,"Partida empatada",Toast.LENGTH_SHORT);
                break;
            default:
                procesaJugada(column);
        }
    }

    public void jugadaIA(){
        if (columnaMaquina == 0) {
            columnaMaquina = (int) (Math.random() * juego.NCOLUMNAS-1);
        }
        if(filaMaquina == 0) filaMaquina = (int) (Math.random() * juego.NFILAS);
        if(columnaMaquina+1 < juego.NCOLUMNAS){
            if(!procesaJugada(columnaMaquina+1)){
                do{//generamos un número aleatorio hasta que de con una columna acertada
                    columnaMaquina = (int) (Math.random() * juego.NCOLUMNAS);
                    filaMaquina = (int) (Math.random() * juego.NFILAS);
                }while(!procesaJugada(columnaMaquina));
            }
        }
    }

    private boolean procesaJugada(int col){
        if(juego.compruebaColumnaVacia(col)){
            if(juego.getTurno() == juego.JUGADOR) Toast.makeText(this,"Columna completa",Toast.LENGTH_SHORT).show();
            return false;//devolvemos false si la columna está completa.
        }else {
            juego.colocarFicha(col);
            dibujarFicha(col);
            int fila;
            if(juego.getTurno() == juego.MAQUINA) {
                fila = filaMaquina;
            }else{
                fila = juego.filaSelect(col);
            }
            if(juego.jugadaGanadora(fila, col)){
                juego.setEstado('G');
                String mensajeGanador = (juego.getTurno() == juego.MAQUINA) ? "Ha ganado la máquina!" : "Has ganado tú!";
                mensajeGanador(mensajeGanador, "El juego se reiniciará!");
            }else{
                juego.cambiarTurno();
                if(juego.getTurno() == Game.MAQUINA){
                    jugadaIA();
                }
            }
            if(juego.tableroCompleto()){
                juego.setEstado('T');
                mensajeGanador("Hay un empate!", "El juego se reiniciará");
                //Toast
            }
        }
        return true;
    }

    private void dibujarFicha(int columna){
        int id = juego.filaSelect(columna);
        ImageButton img = (ImageButton) findViewById(ids[id][columna]);
        if(juego.getTurno() == Game.JUGADOR){
            if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
                img.setImageResource(R.drawable.shape_ring_green);
            }else{
                img.setImageResource(R.drawable.shape_ring_green);
            }
        }else{
            if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
                img.setImageResource(R.drawable.shape_ring_red);
            }else{
                img.setImageResource(R.drawable.shape_ring_red);
            }
        }
    }

    public int coorJuego(int id){
        int columna = 0;
        for (int i = 0; i < Game.NFILAS; i++){
            for (int j = 0; j < Game.NCOLUMNAS; j++){
                if(ids[i][j] == id){
                    columna = j;
                }
            }
        }
        return columna;
    }

    public void mensajeGanador(String msg1, String msg2){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        // set title
        alertDialogBuilder.setTitle(msg1);
        // set dialog message
        alertDialogBuilder
                .setMessage(msg2)
                .setCancelable(false)
                .setPositiveButton("Si",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        Intent intent = getIntent();
                        finish();
                        startActivity(intent);
                    }
                })

                .setNegativeButton("No",new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog,int id) {
                        //terminamos con la actividad
                        finish();
                    }
                });

        AlertDialog alerta = alertDialogBuilder.create();

        alerta.show();

    }

    public void dibujarTablero(){
        for (int i = 0; i < Game.NFILAS; i++) {
            for (int k = 0; k < Game.NCOLUMNAS; k++) {
                ImageButton img = (ImageButton) findViewById(ids[i][k]);
                if(juego.tablero[i][k] == juego.JUGADOR){
                    if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
                        img.setImageResource(R.drawable.shape_ring_green);
                    }else{
                        img.setImageResource(R.drawable.shape_ring_green);
                    }

                }else if(juego.tablero[i][k] == juego.MAQUINA){
                    if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
                        img.setImageResource(R.drawable.shape_ring_red);
                    }else{
                        img.setImageResource(R.drawable.shape_ring_red);
                    }
                }
            }
        }
    }
    /*Rotacion*/

    public String getRotation(Context context){
        final int rotation = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getOrientation();
        switch (rotation){
            case Surface.ROTATION_0:
            case Surface.ROTATION_180:
                return "vertical";
            default:
                return "horizontal";
        }
    }

    public void onSaveInstanceState(Bundle outState){
        outState.putString("TABLERO", juego.tableroToString());
        outState.putInt("TURNO",juego.getTurno());
        outState.putChar("ESTADO",juego.getEstado());
        super.onSaveInstanceState(outState);
    }

    public void onRestoreInstanceState(Bundle savedInstance){
        super.onRestoreInstanceState(savedInstance);
        juego.stringToTablero(savedInstance.getString("TABLERO"));
        juego.setEstado(savedInstance.getChar("ESTADO"));
        juego.setTurno(savedInstance.getInt("TURNO"));
        dibujarTablero();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        getMenuInflater().inflate(R.menu.menu_ingame, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch(id){
            case R.id.aboutUs:
                Intent aboutUs = new Intent(MainActivity.this , About.class);
                startActivity(aboutUs);
                return true;
            case R.id.settings:
                Intent settings = new Intent(MainActivity.this , Settings.class);
                startActivity(settings);
                return true;
            default: return super.onOptionsItemSelected(item);
        }
    }

}

