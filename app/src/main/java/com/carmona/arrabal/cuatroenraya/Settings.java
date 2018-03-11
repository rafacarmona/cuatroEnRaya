package com.carmona.arrabal.cuatroenraya;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;

public class Settings extends AppCompatActivity {
    EditText nombre;
    String nombreUsuario, nombreRecogido;
    Switch switchMusica, switchJugadorEmpieza;
    Boolean musica, musicaRecogida, jugadorEmpiezaRec, jugadorEmpieza;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        SharedPreferences prefs = getSharedPreferences("MisPreferencias",Context.MODE_PRIVATE);

        nombreRecogido = prefs.getString("nombre", "Nombre");
        musicaRecogida = prefs.getBoolean("musica", false);
        jugadorEmpiezaRec = prefs.getBoolean("jugadorEmpieza", false);

        nombre = (EditText) findViewById(R.id.editText_Nombre);
        switchMusica = (Switch) findViewById(R.id.musicaSharedPreferences);
        switchJugadorEmpieza = (Switch) findViewById(R.id.jugadorEmpiezaSharedPreferences);
        nombre.setText(nombreRecogido);
        switchMusica.setChecked(musicaRecogida);
        switchJugadorEmpieza.setChecked(jugadorEmpiezaRec);
    }

    @Override
    public void onPause() {
        super.onPause();

        nombre = (EditText) findViewById(R.id.editText_Nombre);
        nombreUsuario = nombre.getText().toString();

        switchMusica = (Switch) findViewById(R.id.musicaSharedPreferences);
        musica = switchMusica.isChecked();

        switchJugadorEmpieza = (Switch) findViewById(R.id.jugadorEmpiezaSharedPreferences);
        jugadorEmpieza = switchJugadorEmpieza.isChecked();


        SharedPreferences prefs = getSharedPreferences("MisPreferencias", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("nombre", nombreUsuario);
        editor.putBoolean("musica", musica);
        editor.putBoolean("jugadorEmpieza", jugadorEmpieza);
        editor.apply();
    }

}
