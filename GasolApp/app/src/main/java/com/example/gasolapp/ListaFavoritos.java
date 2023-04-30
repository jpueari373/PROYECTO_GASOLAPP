package com.example.gasolapp;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
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
import com.example.gasolapp.model.Gasolineras;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ListaFavoritos extends AppCompatActivity {
    private ArrayAdapter<String> adaptador; //Adaptador controlador de datos
    String usuario;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lista_favoritos);
        Bundle bundle = this.getIntent().getExtras();
        String nombreUsuario = bundle.getString("usuario");
        usuario = nombreUsuario;

        //Accion del boton de buscar por provincia, donde totatmos la url del fichero php que hace la consulta a la bd
        // y se lo pasamos al metodo


        String url= "https://gasolapp.000webhostapp.com/mostrarFavoritos.php";
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
                            //Si el array es 0 quiere decir que esa provincia no tiene gasolineras
                            if (jsonArray.length() == 0) {
                                Toast.makeText(ListaFavoritos.this, "No se encontraron gasolineras en favoritos", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            ArrayList<Gasolineras> gasolineras = new ArrayList<>();
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject gasolinera = jsonArray.getJSONObject(i);
                                int field1 = gasolinera.getInt("Field1");
                                String provincia = gasolinera.getString("Provincia");
                                String municipio = gasolinera.getString("Municipio");
                                String localidad = gasolinera.getString("Localidad");
                                int codigo_postal = gasolinera.getInt("Codigo_po");
                                String direccion = gasolinera.getString("Direccion");
                                Double longitud = gasolinera.getDouble("Longitud");
                                Double latitud = gasolinera.getDouble("Latitud");
                                Double precio_gas = gasolinera.getDouble("Precio_gas");
                                Double precio_g_3 = gasolinera.getDouble("Precio_g_3");
                                Double precio_g_5 = gasolinera.getDouble("Precio_g_5");
                                Double precio_g_6 = gasolinera.getDouble("Precio_g_6");
                                Double precio_g_7 = gasolinera.getDouble("Precio_g_7");
                                String rotulo = gasolinera.getString("Rotulo");
                                String horario = gasolinera.getString("Horario");
                                String fecha =  gasolinera.getString("fecha");
                                Gasolineras objetoGasolinera  = new Gasolineras(field1,provincia,municipio,localidad,codigo_postal,direccion,longitud,latitud
                                        ,precio_gas,precio_g_3,precio_g_5,precio_g_6,precio_g_7,rotulo,horario,fecha);
                                gasolineras.add(objetoGasolinera);
                            }
                            //Metodo que carga el resultado en la lista
                            cargarLista(gasolineras);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(ListaFavoritos.this, "No se encontraron gasolineras en favoritos", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ListaFavoritos.this, "Error: " + error.toString(), Toast.LENGTH_SHORT).show();
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
    private void cargarLista(ArrayList<Gasolineras> gasolineras) {
        adaptador = new ArrayAdapter<String>(this, R.layout.list_item, R.id.nombre_item, obtenerDirecciones(gasolineras));

        ListView listaView = findViewById(R.id.lista_favoritos);
        listaView.setAdapter(adaptador);
        // Agregar un listener para manejar los clicks sobre los elementos de la lista
        listaView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                // Obtener la dirección seleccionada
                int field1 = gasolineras.get(i).getField1();
                String provincia = gasolineras.get(i).getProvincia();
                String municipio = gasolineras.get(i).getMunicipio();
                String localidad = gasolineras.get(i).getLocalidad();
                int codigo_po = gasolineras.get(i).getCodigo_postal();
                String direccion = gasolineras.get(i).getDireccion();
                double latitud = gasolineras.get(i).getLatitud();
                double longitud = gasolineras.get(i).getLongitud();
                String fecha = gasolineras.get(i).getFecha();
                String rotulo = gasolineras.get(i).getRotulo();
                double sp95 = gasolineras.get(i).getPrecio_gas();
                double sp98 = gasolineras.get(i).getPrecio_g_3();
                double a = gasolineras.get(i).getPrecio_g_5();
                double aPlus = gasolineras.get(i).getPrecio_g_6();
                double b = gasolineras.get(i).getPrecio_g_7();
                String horario = gasolineras.get(i).getHorario();
                // Mostrar una alerta con la dirección seleccionada
                AlertDialog.Builder builder = new AlertDialog.Builder(ListaFavoritos.this);
                builder.setMessage("Rótulo: " + rotulo + "\n SP95: " + sp95 + " l/€\n SP98: " + sp98 + " l/€\n A: " + a + " l/€\n A+: " + aPlus + " l/€\n B: " + b + " l/€\n Horario: " + horario)
                        .setPositiveButton("Eliminar de favoritos", new DialogInterface.OnClickListener() {
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
                                                android.app.AlertDialog.Builder alerta = new android.app.AlertDialog.Builder(ListaFavoritos.this);
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
                                Toast.makeText(ListaFavoritos.this, "Eliminada de favoritos", Toast.LENGTH_SHORT).show();
                                EliminarFavoritosRequest efr = new EliminarFavoritosRequest(field1, respuesta);
                                RequestQueue cola = Volley.newRequestQueue(ListaFavoritos.this);
                                cola.add(efr);
                                String url= "https://gasolapp.000webhostapp.com/mostrarFavoritos.php";
                                realizarConsulta(usuario,url);
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

    private ArrayList<String> obtenerDirecciones(ArrayList<Gasolineras> gasolineras) {
        ArrayList<String> direcciones = new ArrayList<>();
        for (Gasolineras gasolinera : gasolineras) {
            direcciones.add(gasolinera.getDireccion());
        }
        return direcciones;
    }

}