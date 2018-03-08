package com.carmona.arrabal.cuatroenraya;

import android.widget.Toast;

/**
 * Created by Rafa Carmona on 16/11/2017.
 */

public class Game {
    static final int MAQUINA = 1;
    static final int JUGADOR = 2;
    static final int NFILAS = 6;
    static final int NCOLUMNAS = 7;
    //esto no sé si está bien.
    static final int VACIO = 0;
    static final String MAQGANADOR = "1111";
    static final String JUGGANADOR = "2222";
    private int turno = JUGADOR;
    public int tablero[][] = new int[NFILAS][NCOLUMNAS];
    private char estado; //J Jugado, G Ganado, T tablas.

    //Constructor.
    public Game(int jugador) {
        //public Game() {
        turno = jugador;
        for (int i = 0; i < NFILAS; i++) {
            for (int k = 0; k < NCOLUMNAS; k++) {
                tablero[i][k] = VACIO;
                this.estado = 'J';
            }
        }
    }

    public boolean compruebaColumnaVacia(int colm) {
        if (tablero[0][colm] != VACIO) return true;
        return false;
    }

    //ESTO NO FUNCIONA ME CAGO EN SUS MUERTOS ME FALLA Y ME DEVUELVE 1 MAS O MENOS EGQEIJQWEQWQW
    //Seleccionamos la ultima fila vacia
    public int filaSelect(int columna) {
        int i = NFILAS-1;
        int fila = -1;

        boolean filaSel = false;
        while(i >= 0 && !filaSel) {
            if (tablero[i][columna] == VACIO) {
                fila = i;
                filaSel = true;
            }else {
                i--;
            }
        }
        return fila+1;
    }

    public boolean tableroCompleto() {
        for (int i=0; i<NFILAS;  i++){
            for (int j=0; j<NCOLUMNAS;  j++){
                if (tablero[i][j] == VACIO){
                    return false;
                }
            }
        }
        return true;
    }

    //cambia turno.
    public void cambiarTurno() {
        this.setTurno(this.getTurno() == MAQUINA ? JUGADOR : MAQUINA);
    }

    public boolean colocarFicha(int colm) {
        for (int i = 5; i >= 0; i--) {
            if (tablero[i][colm] == VACIO) {
                tablero[i][colm] = this.getTurno();
                return true;
            }
        }
        return false;
    }

    //comprueba ganador
    public boolean jugadaGanadora(int fila, int colm){
       return compruebaColm(colm) || compruebaFila(fila) || compruebaDiagonalDer(fila, colm) || compruebaDiagonalIzq(fila, colm);
    }

    public boolean compruebaColm(int colm){
        String match = "";
        for(int i= 0;  i<NFILAS; i++){
            if(tablero[i][colm] == getTurno()) {
                match += getTurno();
                //seteamos el ganador.
                if(match.equals(MAQGANADOR) || match.equals(JUGGANADOR)) return true;
            }else{
                match = "";
            }
        }
        return false;
    }

    //hacia arriba
    public boolean compruebaDiagonalIzq(int fila, int colm){
        String match = "";
        for (int i = fila, j= colm; i < NFILAS && j < NCOLUMNAS; i++, j++){
            if(tablero[i][j] == getTurno()) {
                match += getTurno();
                if(match.equals(MAQGANADOR) || match.equals(JUGGANADOR)) return true;
            }else{
                match= "";
            }
        }
        //hacia abajo
        for (int i = fila-1, j= colm - 1; j >= 0 && i >=0; i--, j--){
            if(tablero[i][j] == getTurno()) {
                match += getTurno();
                if(match.equals(MAQGANADOR) || match.equals(JUGGANADOR)) return true;
            }else{
                match= "";
            }
        }

        return false;
    }

    //Diagonal Derecha
    public boolean compruebaDiagonalDer(int fila, int colm){
        String match = "";
        for (int i = fila, j= colm; i < NFILAS && j >= 0; i++, j--){
            if(tablero[i][j] == getTurno()) {
                match += getTurno();
                if(match.equals(MAQGANADOR) || match.equals(JUGGANADOR)) return true;
            }else{
                match= "";
            }
        }
        //hacia abajo
        for (int i = fila-1, j= colm + 1; j < NCOLUMNAS && i >=0; i--, j++){
            if(tablero[i][j] == getTurno()) {
                match += getTurno();
                if(match.equals(MAQGANADOR) || match.equals(JUGGANADOR)) return true;
            }else{
                match= "";
            }
        }

        return false;
    }

    public boolean compruebaFila(int fila){
        String match = "";
        for(int i= 0;  i<NCOLUMNAS; i++){
            if(tablero[fila][i] == getTurno()) {
                match += getTurno();
                //seteamos el ganador.
                if(match.equals(MAQGANADOR) || match.equals(JUGGANADOR)) return true;
            }else{
                match = "";
            }
        }
        return false;
    }

    /*ROTACION*/

    public String tableroToString(){
        String str = "";
        for (int i = 0; i < NFILAS; i++)
            for (int j = 0; j < NCOLUMNAS; j++)
                str += tablero[i][j];
        return str;
    }

    public void stringToTablero(String str){
        for (int i = 0,count = 0; i < NFILAS; i++) {
            for (int j = 0; j < NCOLUMNAS; j++) {
                tablero[i][j] = str.charAt(count++) - '0';
            }
        }
    }

    //getters y setters
    public void setTurno(int turno) {
        this.turno = turno;
    }

    public int getTurno() {
        return this.turno;
    }

    public char getEstado() {
        return this.estado;
    }

    public void setEstado(char estado) {
        this.estado = estado;
    }

}