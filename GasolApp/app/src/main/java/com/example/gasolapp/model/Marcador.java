package com.example.gasolapp.model;

public class Marcador {

    private String posicion;
    private String descripción;

    public Marcador(){

    }

    public Marcador(String posicion, String descripción) {
        this.posicion = posicion;
        this.descripción = descripción;
    }

    public String getPosicion() {
        return posicion;
    }

    public void setPosicion(String posicion) {
        this.posicion = posicion;
    }

    public String getDescripción() {
        return descripción;
    }

    public void setDescripción(String descripción) {
        this.descripción = descripción;
    }
}
