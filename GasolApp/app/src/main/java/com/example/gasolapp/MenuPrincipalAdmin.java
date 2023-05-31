package com.example.gasolapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

import java.util.Locale;

public class MenuPrincipalAdmin extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawerLayout;
    String usuario;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_lateral_admin);

        //Recuperar datos del bundle transmitido
        Bundle bundle = this.getIntent().getExtras();
        //Localizarmos el TextView donde colocaremos el nombre del usuario
        TextView bienvenido = findViewById(R.id.usuario_menuPre);

        if(bundle != null){
            String usuario = bundle.getString("usuario");
            Toast.makeText(this, "BIENVENIDO " + usuario.toUpperCase(Locale.ROOT), Toast.LENGTH_LONG).show();
            bienvenido.setText("Bienvenido: " + usuario);
        }
        usuario = bundle.getString("usuario");
        Toolbar toolbar = this.findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = this.findViewById(R.id.drawer_layout);
        NavigationView navigationView = this.findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open_nav, R.string.close_nav);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        if(savedInstanceState == null){
            //getSupportFragmentManager().beginTransaction().replace(R.id.contenedor, new MenuActivity()).commit();
            navigationView.setCheckedItem(R.id.lista);
        }
        Button gasolineras = findViewById(R.id.gasolineras_menuPre);
        Button mapa = findViewById(R.id.mapa_menuPre);
        Button favoritos = findViewById(R.id.favoritos_menuPre);
        Button graficas = findViewById(R.id.graficas_menuPre);
        Button historial = findViewById(R.id.historial_menuPre);
        Button administrar = findViewById(R.id.administrar);
        //Accion del boton de mapa
        mapa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Llamada del Intent
                Intent i = new Intent(getApplicationContext(), MapsActivity.class);
                startActivity(i);
            }
        });
        //Accion del boton de gasolineras
        gasolineras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Llamada del Intent
                Intent i = new Intent(getApplicationContext(), ListaGasolineras.class);
                i.putExtra("usuario",usuario);
                startActivity(i);
            }
        });
        //Accion del boton de favoritos
        favoritos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Llamada del Intent
                Intent i = new Intent(getApplicationContext(), ListaFavoritos.class);
                i.putExtra("usuario",usuario);
                startActivity(i);
            }
        });

        //Accion del boton de historial
        historial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Llamada del Intent
                Intent i = new Intent(getApplicationContext(), ListaHistorial.class);
                i.putExtra("usuario",usuario);
                startActivity(i);
            }
        });

        //Accion del boton de graficos
        graficas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Llamada del Intent
                Intent i = new Intent(getApplicationContext(), Graficos.class);
                i.putExtra("usuario",usuario);
                startActivity(i);
            }
        });

        // Accion del boton de administrar
        administrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Inflar el diseño personalizado
                LayoutInflater inflater = getLayoutInflater();
                View dialogView = inflater.inflate(R.layout.admin_dialog, null);

                // Obtener una referencia a los elementos del diseño personalizado
                TextView textView = dialogView.findViewById(R.id.custom_dialog_text);

                // Crear el diálogo personalizado
                AlertDialog.Builder builder = new AlertDialog.Builder(MenuPrincipalAdmin.this);
                builder.setView(dialogView);

                AlertDialog alertDialog = builder.create();
                alertDialog.show();

                Button eliminarUsuario = dialogView.findViewById(R.id.eliminarUsuario);
                Button eliminarFavoritos = dialogView.findViewById(R.id.eliminarFavoritos);
                Button eliminarHistorial = dialogView.findViewById(R.id.eliminarHistorial);
                // Accion del boton de eliminarUsuario
                eliminarUsuario.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // Llamada del Intent
                        Intent i = new Intent(getApplicationContext(), ListaUsuarios.class);
                        startActivity(i);
                        alertDialog.dismiss(); // Cerrar el diálogo después de iniciar la actividad
                    }
                });
                eliminarFavoritos.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // Llamada del Intent
                        Intent i = new Intent(getApplicationContext(), EliminarFavoritosAdmin.class);
                        startActivity(i);
                        alertDialog.dismiss(); // Cerrar el diálogo después de iniciar la actividad
                    }
                });
                eliminarHistorial.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // Llamada del Intent
                        Intent i = new Intent(getApplicationContext(), EliminarHistorialAdmin.class);
                        startActivity(i);
                        alertDialog.dismiss(); // Cerrar el diálogo después de iniciar la actividad
                    }
                });

                // Configurar otros botones y acciones aquí
            }
        });
    }


    public void setSupportActionBar(Toolbar toolbar) {
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Intent i = new Intent();
        switch (item.getItemId()){
            case R.id.lista:
                i = new Intent(this, ListaGasolineras.class);
                startActivity(i);
                break;
            case R.id.mapa:
                i = new Intent(this, MapsActivity.class);
                startActivity(i);
                break;
            case R.id.salir:
                i = new Intent(this, Login.class);
                startActivity(i);
                MenuPrincipalAdmin.this.finish();
                break;
            case R.id.favoritos:
                i = new Intent(getApplicationContext(), ListaFavoritos.class);
                i.putExtra("usuario",usuario);
                startActivity(i);
                break;
            case R.id.historial:
                i = new Intent(getApplicationContext(), ListaHistorial.class);
                i.putExtra("usuario",usuario);
                startActivity(i);
                break;
            case R.id.graficas:
                i = new Intent(getApplicationContext(), Graficos.class);
                i.putExtra("usuario",usuario);
                startActivity(i);
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }else {
            super.onBackPressed();
        }
    }
}



/* TextView mensaje = findViewById(R.id.mensaje);
        Intent i = this.getIntent();
        String nombre = i.getStringExtra("usuario");
        mensaje.setText("Bienvenido: " + nombre);

 */