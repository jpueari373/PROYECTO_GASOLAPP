package com.example.gasolapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import java.util.Locale;

public class MenuPrincipal extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawerLayout;
    String usuario;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_lateral);

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
                MenuPrincipal.this.finish();
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