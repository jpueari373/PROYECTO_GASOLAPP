package com.example.gasolapp.model;

public class Registrarse {
    private String nombreCompleto;
    private String username;
    private String contrasenia;
    private String email;
    private int telefono;

    public Registrarse(String nombreCompleto, String username, String contrasenia, String email, int telefono) {
        this.nombreCompleto = nombreCompleto;
        this.username = username;
        this.contrasenia = contrasenia;
        this.email = email;
        this.telefono = telefono;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getTelefono() {
        return telefono;
    }

    public void setTelefono(int telefono) {
        this.telefono = telefono;
    }
}
