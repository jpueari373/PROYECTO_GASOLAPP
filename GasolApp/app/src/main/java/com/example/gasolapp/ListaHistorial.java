package com.example.gasolapp;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

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
import com.example.gasolapp.model.HistorialModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ListaHistorial extends AppCompatActivity {
    private ArrayAdapter<String> adaptador; //Adaptador controlador de datos
    String usuario;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.historial);
        Bundle bundle = this.getIntent().getExtras();
        String nombreUsuario = bundle.getString("usuario");
        usuario = nombreUsuario;

        //Accion del boton de buscar por provincia, donde totatmos la url del fichero php que hace la consulta a la bd
        // y se lo pasamos al metodo


        String url= "https://gasolapp.000webhostapp.com/mostrarHistorial.php";
        //metodo que realiza la consulta a la bd, donde le pasamos el campo y la url del fichero php
        realizarConsulta(usuario,url);

    }
    //Este metodo realiza una consulta a la bd, mediante un fichero php, donde se le pasa el valor de la provincia y devuelve las gasolineras con esa provincia
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
                                Toast.makeText(ListaHistorial.this, "No se encontraron registros en el historial", Toast.LENGTH_SHORT).show();
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
                            Toast.makeText(ListaHistorial.this, "No se encontraron registros en el historial", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ListaHistorial.this, "Error: " + error.toString(), Toast.LENGTH_SHORT).show();
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
        adaptador = new ArrayAdapter<String>(this, R.layout.list_item, R.id.nombre_item, obtenerDirecciones(registros));
        ListView listaView = findViewById(R.id.historial_lista);
        listaView.setAdapter(adaptador);
        // Agregar un listener para manejar los clicks sobre los elementos de la lista
        listaView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                // Obtener la dirección seleccionada
                int field1 = registros.get(i).getField1();
                String provincia = registros.get(i).getProvincia();
                String municipio = registros.get(i).getMunicipio();
                String localidad = registros.get(i).getLocalidad();
                String rotulo = registros.get(i).getRotulo();
                String direccion = registros.get(i).getDireccion();
                String carburante = registros.get(i).getCarburante();
                double precio_carburante = registros.get(i).getPrecio_carburante();
                double litros_repostados = registros.get(i).getLitros_repostados();
                double coste_total = registros.get(i).getCoste_total();
                String fecha = registros.get(i).getFecha();
                String usuario = registros.get(i).getUsuario();
                // Mostrar una alerta con la dirección seleccionada
                AlertDialog.Builder builder = new AlertDialog.Builder(ListaHistorial.this);
                builder.setMessage(" Fecha: " + fecha  + " \n Rotulo: " + rotulo + " \n Provincia: " + provincia + " \n Municipio: " + municipio + " \n Localidad: " + localidad +
                                " \n Carburante: " + carburante + " \n Precio/litro: " + precio_carburante + " \n Litros repostados: " + litros_repostados + " \n Coste total: " + coste_total)
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
                                                android.app.AlertDialog.Builder alerta = new android.app.AlertDialog.Builder(ListaHistorial.this);
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
                                Toast.makeText(ListaHistorial.this, "Eliminado de Historial", Toast.LENGTH_SHORT).show();
                                EliminarHistorialRequest ehr = new EliminarHistorialRequest(field1, fecha, respuesta);
                                RequestQueue cola = Volley.newRequestQueue(ListaHistorial.this);
                                cola.add(ehr);
                                String url= "https://gasolapp.000webhostapp.com/mostrarHistorial.php";
                                recreate();
                            }
                        }).setNegativeButton("Volver", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // Acción a realizar al hacer clic en "volver"
                                dialog.dismiss();

                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });
    }

    private ArrayList<String> obtenerDirecciones(ArrayList<HistorialModel> historiales) {
        ArrayList<String> direcciones = new ArrayList<>();
        for (HistorialModel historial : historiales) {
            direcciones.add(historial.getDireccion());
            Log.d("direcciones", historial.getDireccion());
        }
        return direcciones;
    }

}