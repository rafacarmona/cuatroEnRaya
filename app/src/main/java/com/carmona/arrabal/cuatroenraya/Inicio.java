package com.carmona.arrabal.cuatroenraya;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class Inicio extends AppCompatActivity {

    ImageButton boton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);

        boton = (ImageButton) findViewById(R.id.imagen);
        boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //llamada a la funcion
                    Intent inten = new Intent(Inicio.this , MainActivity.class);

                    startActivity(inten);


            }
        });
    }
}
