package com.example.gasolapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.gasolapp.model.Usuario;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Login extends AppCompatActivity {

    private Button boton_inicio, boton_registro;
    EditText usuarioT, contrasena;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_usuario);
        //botones
        boton_inicio = this.findViewById(R.id.boton_iniciar_sesion);
        boton_registro = this.findViewById(R.id.boton_registro);
        //EditText
        usuarioT = this.findViewById(R.id.usuarioLogin);
        contrasena = this.findViewById(R.id.contrasenaLogin);

        //Accion del boton de inicio, comprueba los datos y si devulve una respuesta accede a la aplicacion
        boton_inicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String usuario = usuarioT.getText().toString();
                String clave = contrasena.getText().toString();
                Response.Listener<String> respuesta = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonRespuesta = new JSONObject(response);
                            boolean res = jsonRespuesta.getBoolean("success");
                            //si res es true es que la consulta devuelve unos valores con los parametros introducidos y logea al usuario
                            if (res == true){
                                String nombre = jsonRespuesta.getString("usuario");
                                Intent i = new Intent(Login.this, MenuPrincipal.class);
                                i.putExtra("usuario",nombre);
                                Login.this.startActivity(i);
                                Login.this.finish();
                            }else{//Si res es false muestra una ventana de alerta de aviso que fallo el login.
                                AlertDialog.Builder alerta = new AlertDialog.Builder(Login.this);
                                alerta.setMessage("Fallo en el Login")
                                        .setNegativeButton("Reintentar", null)
                                        .create()
                                        .show();
                            }
                        } catch (JSONException e) {
                            e.getMessage();
                        }
                    }
                };
                LoginRequest r = new LoginRequest(usuario, clave, respuesta);
                RequestQueue cola = Volley.newRequestQueue(Login.this);
                cola.add(r);
            }

        });
        //boton de registro, que envia al usuario a la ventana de registro
        boton_registro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Formulario de registro ", Toast.LENGTH_LONG).show();
                //Llamada del Intent
                Intent i = new Intent(getApplicationContext(), Registro.class);
                startActivity(i);
            }
        });
    }
}