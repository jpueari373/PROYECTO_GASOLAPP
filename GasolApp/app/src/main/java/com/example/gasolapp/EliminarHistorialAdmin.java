package com.example.gasolapp;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.gasolapp.model.HistorialModel;
import com.example.gasolapp.request.EliminarFavoritosRequest;
import com.example.gasolapp.request.EliminarHistorialRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class EliminarHistorialAdmin extends AppCompatActivity {
    private ArrayAdapter<String> adaptador; //Adaptador controlador de datos
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.eliminar_historial);
        Button buscar = findViewById(R.id.boton_buscar_usuario_historial);
        buscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText username = findViewById(R.id.eliminarHistorial_usuario);
                String nombreUsuario = username.getText().toString();
                String usuario = nombreUsuario;
                String url= "https://gasolapp.000webhostapp.com/mostrarHistorial.php";//url del fichero php para mostrar el historial
                //metodo que realiza la consulta a la bd, donde le pasamos el campo y la url del fichero php
                realizarConsulta(usuario,url);
            }
        });
    }
    //Este metodo realiza una consulta a la bd, mediante un fichero php, donde se le pasa el valor del usuario y carga los repostajes en la lista del historial de dicho usuario
    private void realizarConsulta(String usuario, String url) {
        //Creamos un StringRequest donde obtendremos el resultado de la consulta
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {//Este metodo obtinene el resultado de la consulta en JSON
                        Log.d("Response", response); // registro adicional para mostrar la respuesta JSON
                        //El resultado obtenido es un JSONObject desde el servidor, por lo que lo tomamos como  JSONObject y despues lo pasamos a un JSONArray
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonArray = jsonObject.getJSONArray("data");
                            //Si el array es 0 quiere decir que esa provincia no tiene registros
                            if (jsonArray.length() == 0) {
                                Toast.makeText(EliminarHistorialAdmin.this, "No se encontraron registros en el historial", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            ArrayList<HistorialModel> registros = new ArrayList<>();
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject registro = jsonArray.getJSONObject(i);
                                int field1 = registro.getInt("field1");
                                String provincia = registro.getString("provincia");
                                String municipio = registro.getString("municipio");
                                String localidad = registro.getString("localidad");
                                String direccion = registro.getString("direccion");
                                String carburante = registro.getString("carburante");
                                double precio_carburante = registro.getDouble("precio_carburante");
                                double litros_repostados = registro.getDouble("litros_repostados");
                                double coste_total = registro.getDouble("coste_total");
                                String rotulo = registro.getString("rotulo");
                                String fecha = registro.getString("fecha");
                                String usuario = registro.getString("usuario");

                                HistorialModel objetoHistorial = new HistorialModel(field1, provincia, municipio, localidad, rotulo, direccion, carburante, precio_carburante,
                                        litros_repostados, coste_total, fecha, usuario);
                                registros.add(objetoHistorial);
                            }
                            //Metodo que carga el resultado en la lista
                            cargarLista(registros);
                            Log.d("registros", registros.toString());
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(EliminarHistorialAdmin.this, "No se encontraron registros en el historial", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(EliminarHistorialAdmin.this, "Error: " + error.toString(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {//Le pasamos el campo de Provincia al fichero php para la consulta
                Map<String, String> params = new HashMap<String, String>();
                params.put("usuario", usuario);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
    //Metodo que se encarga de cargar los datos en la lista
    private void cargarLista(ArrayList<HistorialModel> registros) {
        ArrayList<String> fechasOrdenadas = obtenerFechas(registros);
        Collections.sort(fechasOrdenadas); // Ordenar las fechas en orden ascendente

        adaptador = new ArrayAdapter<String>(this, R.layout.list_item, R.id.nombre_item, fechasOrdenadas) {
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View view = super.getView(position, convertView, parent);

                TextView textView = view.findViewById(R.id.nombre_item);
                String fecha = fechasOrdenadas.get(position);

                // Mostrar solo la fecha en el elemento de la lista
                textView.setText(fecha);

                return view;
            }
        };

        ListView listaView = findViewById(R.id.lista_historial_eliminar);
        listaView.setAdapter(adaptador);
        // Agregar un listener para manejar los clicks sobre los elementos de la lista
        listaView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                // Obtener la fecha seleccionada
                String fechaSeleccionada = fechasOrdenadas.get(i);

                // Obtener los registros correspondientes a la fecha seleccionada
                ArrayList<HistorialModel> registrosFechaSeleccionada = new ArrayList<>();
                for (HistorialModel historial : registros) {
                    if (historial.getFecha().equals(fechaSeleccionada)) {
                        registrosFechaSeleccionada.add(historial);
                    }
                }

                // Mostrar el contenido completo de la fecha seleccionada
                mostrarContenidoFecha(registrosFechaSeleccionada);
            }
        });
    }


    // Método para mostrar el contenido completo de la fecha seleccionada
    private void mostrarContenidoFecha(ArrayList<HistorialModel> registrosFechaSeleccionada) {

        // Declarar variables finales para field1 y fecha
        final int[] field1 = new int[1];
        final String[] fecha = new String[1];

        // Construir el contenido completo a partir de los registros de la fecha seleccionada
        StringBuilder contenido = new StringBuilder();
        for (HistorialModel historial : registrosFechaSeleccionada) {
            contenido.append("Direccion: ").append(historial.getDireccion() +"\n")
                    .append("Rotulo: ").append(historial.getRotulo() + "\n")
                    .append("Provincia: ").append(historial.getProvincia()+"\n")
                    .append("Municipio: ").append(historial.getMunicipio()+"\n")
                    .append("Localidad: ").append(historial.getLocalidad()+"\n")
                    .append("Carburante: ").append(historial.getCarburante()+"\n")
                    .append("Precio/litro: ").append(historial.getPrecio_carburante()+" €\n")
                    .append("Litros repostados: ").append(historial.getLitros_repostados()+" L\n")
                    .append("Coste total: ").append(historial.getCoste_total() +" €");

            field1[0] = historial.getField1();
            fecha[0] = historial.getFecha();
        }

        // Mostrar una alerta con el contenido completo
        AlertDialog.Builder builder = new AlertDialog.Builder(EliminarHistorialAdmin.this);
        builder.setMessage(contenido.toString())
                .setPositiveButton("Eliminar de historial", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Response.Listener<String> respuesta = new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Log.d("RESPUESTA", response); // Agregar este Log para ver la respuesta del servidor

                                try {
                                    JSONObject jsonRespuesta = new JSONObject(response);
                                    boolean res = jsonRespuesta.getBoolean("success");
                                    if (res == true){

                                    }else{
                                        android.app.AlertDialog.Builder alerta = new android.app.AlertDialog.Builder(EliminarHistorialAdmin.this);
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
                        Toast.makeText(EliminarHistorialAdmin.this, "Eliminado de Historial", Toast.LENGTH_SHORT).show();
                        EliminarHistorialRequest ehr = new EliminarHistorialRequest(field1[0], fecha[0], respuesta);
                        RequestQueue cola = Volley.newRequestQueue(EliminarHistorialAdmin.this);
                        cola.add(ehr);
                        recreate();
                    }
                }).setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Acción a realizar al hacer clic en "volver"
                        dialog.dismiss();

                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    private ArrayList<String> obtenerFechas(ArrayList<HistorialModel> historiales) {
        ArrayList<String> fechas = new ArrayList<>();
        for (HistorialModel historial : historiales) {
            fechas.add(historial.getFecha());
            Log.d("fechas", historial.getFecha());
        }
        return fechas;
    }

}