package com.example.gasolapp.model;

public class HistorialModel {

    private int field1;
    private String provincia;
    private String municipio;
    private String localidad;
    private String rotulo;
    private String direccion;
    private String carburante;
    private double precio_carburante;
    private double litros_repostados;
    private double coste_total;
    private String usuario;
    private String fecha;

    public HistorialModel(int field1, String provincia, String municipio, String localidad, String rotulo, String direccion, String carburante, double precio_carburante, double litros_repostados, double coste_total, String fecha,String usuario) {
        this.field1 = field1;
        this.provincia = provincia;
        this.municipio = municipio;
        this.localidad = localidad;
        this.rotulo = rotulo;
        this.direccion = direccion;
        this.carburante = carburante;
        this.precio_carburante = precio_carburante;
        this.litros_repostados = litros_repostados;
        this.coste_total = coste_total;
        this.fecha = fecha;
        this.usuario = usuario;
    }

    public int getField1() {
        return field1;
    }

    public void setField1(int field1) {
        this.field1 = field1;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public String getMunicipio() {
        return municipio;
    }

    public void setMunicipio(String municipio) {
        this.municipio = municipio;
    }

    public String getLocalidad() {
        return localidad;
    }

    public void setLocalidad(String localidad) {
        this.localidad = localidad;
    }

    public String getRotulo() {
        return rotulo;
    }

    public void setRotulo(String rotulo) {
        this.rotulo = rotulo;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getCarburante() {
        return carburante;
    }

    public void setCarburante(String carburante) {
        this.carburante = carburante;
    }

    public double getPrecio_carburante() {
        return precio_carburante;
    }

    public void setPrecio_carburante(double precio_carburante) {
        this.precio_carburante = precio_carburante;
    }

    public double getLitros_repostados() {
        return litros_repostados;
    }

    public void setLitros_repostados(double litros_repostados) {
        this.litros_repostados = litros_repostados;
    }

    public double getCoste_total() {
        return coste_total;
    }

    public void setCoste_total(double coste_total) {
        this.coste_total = coste_total;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
}
