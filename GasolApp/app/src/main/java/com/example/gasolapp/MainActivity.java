package com.example.gasolapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gasolapp.model.Usuario;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Button boton;
    private List<Usuario> listaUsuarios;
    private int i = 0;
    private int posObj = 0;
    //DAO dao = new DAOImpl();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_usuario);

        boton = this.findViewById(R.id.boton_iniciar_sesion);
        listaUsuarios = new ArrayList<>();

        String nom1 = "Pepe";
        String nom2 = "Juan";
        String contra1 = "pepe1";
        String contra2 = "juan1";

        Usuario u = new Usuario(nom1, contra1);
        Usuario u2 = new Usuario(nom2, contra2);

        listaUsuarios.add(u);
        listaUsuarios.add(u2);
        //listaUsuarios = dao.listarUsuarios();

        boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Se obtiene lo escrito en los campos usuario y contraseña
                String username = ((TextView) findViewById(R.id.input_usuario)).getText().toString();
                String contrasenia = ((TextView) findViewById(R.id.input_contrasena)).getText().toString();

                //Si los campos están vacíos
                if (username.equals("") || contrasenia.equals("")) {
                    Toast.makeText(getApplicationContext(), "Ambos campos deben estar rellenos", Toast.LENGTH_LONG).show();

                    //Si el usuario y contraseña no coinciden
                    // } else if (!(listaUsuarios.get(i).getUsername().equals(username) && listaUsuarios.get(i).getContrasenia().equals(contrasenia))) {
                    //   Toast.makeText(getApplicationContext(), "USUARIO INCORRECTO", Toast.LENGTH_LONG).show();

                } else

                    //Busca entre toda la lista un usuario y contraseña coincidentes
                    for (i = 0; i < listaUsuarios.size(); i++) {
                        if (listaUsuarios.get(i).getUsername().equals(username) && listaUsuarios.get(i).getContrasenia().equals(contrasenia)) {
                            posObj = i;
                            Toast.makeText(getApplicationContext(), "USUARIO CORRECTO", Toast.LENGTH_LONG).show();

                            Bundle bundle = new Bundle();
                            bundle.putString("username", listaUsuarios.get(posObj).getUsername());

                            //Llamada del Intent
                           // Intent i = new Intent(getApplicationContext(), MenuActivity.class);
                           // i.putExtras(bundle);
                            //startActivity(i);
                        }
                    }
            }

        });

    }
}