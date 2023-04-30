package com.example.gasolapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class CalculadoraRepostaje extends AppCompatActivity {

    private ArrayAdapter<String> adaptador; // Adaptador controlador de datos
    String usuario;
    String carburante="";
    double total=0.0;
    double precioLitro = 0.0;
    double precio_carburante = 0.0;
    double litros = 0.0;
    private RadioGroup radioGroup;
    private RadioButton rd_sp95, rd_sp98, rd_a, rd_aplus, rd_b;
    private EditText cantidadLitros;
    private TextView totalRepostado,precio_litroT, sp95_precio, sp98_precio, a_precio, aplus_precio, b_precio, nombre, direccionG, datos_provincia, datos_municipio;
    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.guardar_historial_calculadora);
        Bundle bundle = this.getIntent().getExtras();
        int field1 = bundle.getInt("field1");
        String nombreUsuario = bundle.getString("usuario");
        double sp95 = bundle.getDouble("sp95");
        double sp98 = bundle.getDouble("sp98");
        double a = bundle.getDouble("a");
        double aplus = bundle.getDouble("aplus");
        double b = bundle.getDouble("b");
        String direccion = bundle.getString("direccion");
        String rotulo = bundle.getString("rotulo");
        String provincia = bundle.getString("provincia");
        String localidad = bundle.getString("localidad");
        String municipio = bundle.getString("municipio");
        usuario = nombreUsuario;
        Button calcular = findViewById(R.id.calcular_repostado);
        cantidadLitros = findViewById(R.id.cantidad_litros);
        totalRepostado = findViewById(R.id.total_repostado);
        precio_litroT = findViewById(R.id.precio_litro);
        radioGroup = findViewById(R.id.radio_group);
        rd_sp95 = findViewById(R.id.rd_sp95);
        rd_sp98 = findViewById(R.id.rd_sp98);
        rd_a = findViewById(R.id.rd_a);
        rd_aplus = findViewById(R.id.rd_aplus);
        rd_b = findViewById(R.id.rd_b);
        sp95_precio = findViewById(R.id.sp95_precio);
        if (sp95 != 0){
            sp95_precio.setText("SP95: " + sp95 + " €");
            sp95_precio.setBackgroundColor(Color.GREEN);
        }else{
            sp95_precio.setText("SP95: " + sp95 + " €");
            sp95_precio.setBackgroundColor(Color.RED);
        }
        sp98_precio = findViewById(R.id.sp98_precio);
        if (sp98 != 0){
            sp98_precio.setText("SP98: " + sp98 + " €");
            sp98_precio.setBackgroundColor(Color.GREEN);
        }else{
            sp98_precio.setText("SP98: " + sp98 + " €");
            sp98_precio.setBackgroundColor(Color.RED);
        }
        a_precio = findViewById(R.id.a_precio);
        if (a != 0){
            a_precio.setText("A: " + a + " €");
            a_precio.setBackgroundColor(Color.GREEN);
        }else{
            a_precio.setText("A: " + a + " €");
            a_precio.setBackgroundColor(Color.RED);
        }
        aplus_precio = findViewById(R.id.aplus_precio);
        if (aplus != 0){
            aplus_precio.setText("A+: "+ aplus + " €");
            aplus_precio.setBackgroundColor(Color.GREEN);
        }else{
            aplus_precio.setText("A+: "+ aplus + " €");
            aplus_precio.setBackgroundColor(Color.RED);
        }
        b_precio = findViewById(R.id.b_precio);
        if (b != 0){
            b_precio.setText("B: " + b + " €");
            b_precio.setBackgroundColor(Color.GREEN);
        }else{
            b_precio.setText("B: " + b + " €");
            b_precio.setBackgroundColor(Color.RED);
        }
        nombre = findViewById(R.id.datos_nombre);
        nombre.setText("Nombre: " + rotulo);
        direccionG = findViewById(R.id.datos_direccion);
        direccionG.setText("Dirección: " + direccion);
        datos_provincia = findViewById(R.id.datos_provincia);
        datos_provincia.setText("Provincia: " + provincia);
        datos_municipio = findViewById(R.id.datos_municipio);
        datos_municipio.setText("Municipio: " + municipio);
        calcular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                // Obtener el precio del combustible seleccionado
                if (rd_sp95.isChecked()) {
                    precioLitro = sp95;
                    precio_litroT.setText("Precio: " + precioLitro + " €");
                    carburante = "sp95";
                    precio_carburante = precioLitro;
                } else if (rd_sp98.isChecked()) {
                    precioLitro = sp98;
                    precio_litroT.setText("Precio: " + precioLitro + " €");
                    carburante = "sp98";
                    precio_carburante = precioLitro;
                } else if (rd_a.isChecked()) {
                    precioLitro = a;
                    precio_litroT.setText("Precio: " + precioLitro + " €");
                    carburante = "A";
                    precio_carburante = precioLitro;
                }else if (rd_aplus.isChecked()) {
                    precioLitro = aplus;
                    precio_litroT.setText("Precio: " + precioLitro + " €");
                    carburante = "A+";
                    precio_carburante = precioLitro;
                }else if (rd_b.isChecked()) {
                    precioLitro = b;
                    precio_litroT.setText("Precio: " + precioLitro + " €");
                    carburante = "B";
                    precio_carburante = precioLitro;
                }

                if (cantidadLitros.getText().length() == 0) {
                    android.app.AlertDialog.Builder alerta = new android.app.AlertDialog.Builder(CalculadoraRepostaje.this);
                    alerta.setMessage("Inserta la cantidad de litros")
                            .setNegativeButton("Volver", null)
                            .create()
                            .show();
                }else {
                    litros = Double.parseDouble(cantidadLitros.getText().toString());
                    total = litros * precioLitro;
                    String totalRepostadoString = String.format("%.2f", total);
                    // Mostrar el total en el TextView
                    totalRepostado.setText("Total: " + totalRepostadoString + " €");
                }


            }
        });
        Button volver = findViewById(R.id.volver);
        Button guardar = findViewById(R.id.guardar_historial);
        //Accion boton de volver
        volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CalculadoraRepostaje.this.finish();
            }
        });
        //Accion boton de guardar en historial
        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Response.Listener<String> respuesta = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("RESPUESTA", response); // Agregar este Log para ver la respuesta del servidor

                        try {
                            JSONObject jsonRespuesta = new JSONObject(response);
                            boolean res = jsonRespuesta.getBoolean("success");
                            if (res == true){

                            }else{
                                android.app.AlertDialog.Builder alerta = new android.app.AlertDialog.Builder(CalculadoraRepostaje.this);
                                alerta.setMessage("Error al guardar repostaje")
                                        .setNegativeButton("Reintentar", null)
                                        .create()
                                        .show();
                            }
                        } catch (JSONException e) {
                            e.getMessage();
                        }
                    }
                };
                // Obtén la fecha y hora actual
                Calendar calendar = Calendar.getInstance();
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                // Convierte la fecha y hora en formato de texto
                String currentDateTime = dateFormat.format(calendar.getTime());
                String fecha = currentDateTime;
                if (total != 0){
                    HistorialRequest hr = new HistorialRequest(field1, provincia, municipio, localidad, rotulo, direccion, carburante, precio_carburante, litros,total,fecha,usuario,respuesta);
                    RequestQueue cola = Volley.newRequestQueue(CalculadoraRepostaje.this);
                    cola.add(hr);
                    Toast.makeText(CalculadoraRepostaje.this, "Repostaje guardado correctamente", Toast.LENGTH_SHORT).show();
                    CalculadoraRepostaje.this.finish();
                }else {
                    android.app.AlertDialog.Builder alerta = new android.app.AlertDialog.Builder(CalculadoraRepostaje.this);
                    alerta.setMessage("Por favor calcula el total")
                            .setNegativeButton("Volver", null)
                            .create()
                            .show();
                }

            }
        });
    }
}
