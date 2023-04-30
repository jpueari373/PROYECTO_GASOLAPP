package com.example.gasolapp.model;






public class Gasolineras {

    private int field1;
    private String provincia;
    private String municipio;
    private String localidad;
    private int codigo_postal;
    private String direccion;
    private double longitud;
    private double latitud;
    private double precio_gas;
    private double precio_g_3;
    private double precio_g_5;
    private double precio_g_6;
    private double precio_g_7;
    private String rotulo;
    private String horario;
    private String fecha;

    public Gasolineras(int field1, String provincia, String municipio, String localidad, int codigo_postal, String direccion, double longitud, double latitud, double precio_gas, double precio_g_3, double precio_g_5, double precio_g_6, double precio_g_7, String rotulo, String horario, String fecha) {
        this.field1 = field1;
        this.provincia = provincia;
        this.municipio = municipio;
        this.localidad = localidad;
        this.codigo_postal = codigo_postal;
        this.direccion = direccion;
        this.longitud = longitud;
        this.latitud = latitud;
        this.precio_gas = precio_gas;
        this.precio_g_3 = precio_g_3;
        this.precio_g_5 = precio_g_5;
        this.precio_g_6 = precio_g_6;
        this.precio_g_7 = precio_g_7;
        this.rotulo = rotulo;
        this.horario = horario;
        this.fecha = fecha;
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

    public int getCodigo_postal() {
        return codigo_postal;
    }

    public void setCodigo_postal(int codigo_postal) {
        this.codigo_postal = codigo_postal;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public double getLongitud() {
        return longitud;
    }

    public void setLongitud(double longitud) {
        this.longitud = longitud;
    }

    public double getLatitud() {
        return latitud;
    }

    public void setLatitud(double latitud) {
        this.latitud = latitud;
    }

    public double getPrecio_gas() {
        return precio_gas;
    }

    public void setPrecio_gas(double precio_gas) {
        this.precio_gas = precio_gas;
    }

    public double getPrecio_g_3() {
        return precio_g_3;
    }

    public void setPrecio_g_3(double precio_g_3) {
        this.precio_g_3 = precio_g_3;
    }

    public double getPrecio_g_5() {
        return precio_g_5;
    }

    public void setPrecio_g_5(double precio_g_5) {
        this.precio_g_5 = precio_g_5;
    }

    public double getPrecio_g_6() {
        return precio_g_6;
    }

    public void setPrecio_g_6(double precio_g_6) {
        this.precio_g_6 = precio_g_6;
    }

    public double getPrecio_g_7() {
        return precio_g_7;
    }

    public void setPrecio_g_7(double precio_g_7) {
        this.precio_g_7 = precio_g_7;
    }

    public String getRotulo() {
        return rotulo;
    }

    public void setRotulo(String rotulo) {
        this.rotulo = rotulo;
    }

    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
}
