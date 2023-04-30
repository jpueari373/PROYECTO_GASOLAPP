package com.example.gasolapp;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.gasolapp.model.Gasolineras;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Registro extends AppCompatActivity {

    private Button boton_registrarse, boton_volver;
    EditText nombre_completoT, nombre_usuarioT, contrasenaT, emailT, telefonoT;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registro_usuario);

        boton_registrarse = this.findViewById(R.id.boton_registrarse);
        boton_volver = this.findViewById(R.id.boton_volver);

        nombre_completoT = this.findViewById(R.id.nombreRegistro);
        nombre_usuarioT = this.findViewById(R.id.usuarioRegistro);
        contrasenaT = this.findViewById(R.id.contrasenaRegistro);
        emailT = this.findViewById(R.id.emailRegistro);
        telefonoT = this.findViewById(R.id.telefonoRegistro);



        //boton de registrarse
        boton_registrarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nombre = nombre_completoT.getText().toString();
                String usuario = nombre_usuarioT.getText().toString();
                String contrasena = contrasenaT.getText().toString();
                String email = emailT.getText().toString();
                String telefonoString = telefonoT.getText().toString();

                if (comprobarCampos(nombre, usuario, contrasena, email, telefonoString)) {
                    Response.Listener<String> respuesta = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonRespuesta = new JSONObject(response);
                                boolean res = jsonRespuesta.getBoolean("success");
                                if (res == true){
                                    Intent i = new Intent(Registro.this, Login.class);
                                    Registro.this.startActivity(i);
                                    Registro.this.finish();
                                }else{
                                    AlertDialog.Builder alerta = new AlertDialog.Builder(Registro.this);
                                    alerta.setMessage("Error en el registro")
                                            .setNegativeButton("Reintentar", null)
                                            .create()
                                            .show();
                                }
                            } catch (JSONException e) {
                                e.getMessage();
                            }
                        }
                    };

                    String rol = "usuario";
                    RegistroRequest r = new RegistroRequest(nombre, usuario, contrasena, email, Integer.parseInt(telefonoString), rol, respuesta);
                    RequestQueue cola = Volley.newRequestQueue(Registro.this);
                    cola.add(r);
                }

            }
        });

        //boton de volver
        boton_volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Llamada del Intent
                Intent i = new Intent(getApplicationContext(), Login.class);
                startActivity(i);
            }
        });
    }
    //Metodo para comprobar si estan en blancos los campos en la ventana de registro
    public boolean comprobarCampos(String nombre, String usuario, String contrasena, String email, String telefonoString) {
        boolean res = true;

        if (nombre.isEmpty()) {
            Toast.makeText(this, "Introduce nombre y apellidos", Toast.LENGTH_SHORT).show();
            res = false;
        } else if (usuario.isEmpty()) {
            Toast.makeText(this, "Introduce nombre de usuario", Toast.LENGTH_SHORT).show();
            res = false;
        } else if (contrasena.isEmpty()) {
            Toast.makeText(this, "Introduce la contraseña", Toast.LENGTH_SHORT).show();
            res = false;
        } else if (email.isEmpty()) {
            Toast.makeText(this, "Introduce el email", Toast.LENGTH_SHORT).show();
            res = false;
        } else if (!isValidEmail(email)) {
            Toast.makeText(this, "Email inválido", Toast.LENGTH_SHORT).show();
            res = false;
        } else if (telefonoString.isEmpty()) {
            Toast.makeText(this, "Introduce el teléfono", Toast.LENGTH_SHORT).show();
            res = false;
        }

        return res;
    }

    public boolean isValidEmail(String email) {
        // Patrón de expresión regular para validar el formato del correo electrónico
        String patron = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$";
        return email.matches(patron);
    }

    public boolean isValidPhoneNumber(String phoneNumber) {
        // Patrón de expresión regular para validar el formato del número de teléfono
        String patron = "^[0-9]{10}$";
        return phoneNumber.matches(patron);
    }
}