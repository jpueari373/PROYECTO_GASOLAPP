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
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.gasolapp.model.Usuario;
import com.example.gasolapp.request.EliminarUsuarioRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ListaUsuarios extends AppCompatActivity {
    private ArrayAdapter<String> adaptador; //Adaptador controlador de datos

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lista_usuarios);
        Bundle bundle = this.getIntent().getExtras();
        String url= "https://gasolapp.000webhostapp.com/listaUsuario.php";//url fichero php para listar los usuarios

        mostrarUsuario(url);
        Button eliminar = findViewById(R.id.boton_eliminar_usuario);

        //accion del boton eliminar usuario
        eliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText nombreUsuario = findViewById(R.id.campo_usuario);
                String usuario = nombreUsuario.getText().toString();
                Response.Listener<String> respuesta = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("RESPUESTA", response); // Agregar este Log para ver la respuesta del servidor

                        try {
                            JSONObject jsonRespuesta = new JSONObject(response);
                            boolean res = jsonRespuesta.getBoolean("success");
                            if (res == true){

                            }else{
                                android.app.AlertDialog.Builder alerta = new android.app.AlertDialog.Builder(ListaUsuarios.this);
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
                Toast.makeText(ListaUsuarios.this, "Usuario eliminado", Toast.LENGTH_SHORT).show();
                EliminarUsuarioRequest eur = new EliminarUsuarioRequest(usuario, respuesta);
                RequestQueue cola = Volley.newRequestQueue(ListaUsuarios.this);
                cola.add(eur);
                recreate();
            }
        });


    }
    //Este metodo realiza una consulta a la bd, mediante un fichero php, donde se le pasa la url del fichero y carga los usuarios de la app en la lista
    private void mostrarUsuario(String url) {
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
                            //Si el array es 0 quiere decir que no tiene usuarios
                            if (jsonArray.length() == 0) {
                                Toast.makeText(ListaUsuarios.this, "No se encontraron usuarios", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            ArrayList<Usuario> usuarios = new ArrayList<>();
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject gasolinera = jsonArray.getJSONObject(i);
                                int id = gasolinera.getInt("id");
                                String nombre = gasolinera.getString("nombre");
                                String usuario = gasolinera.getString("usuario");
                                String contrasena = gasolinera.getString("contrasena");
                                String email = gasolinera.getString("email");
                                int telefono = gasolinera.getInt("telefono");
                                String rol = gasolinera.getString("rol");
                                Usuario objetoUsuario  = new Usuario(id,nombre,usuario,contrasena,email,telefono,rol);
                                usuarios.add(objetoUsuario);
                            }
                            //Metodo que carga el resultado en la lista
                            cargarLista(usuarios);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(ListaUsuarios.this, "Inserte un usuario existente", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ListaUsuarios.this, "Error: " + error.toString(), Toast.LENGTH_SHORT).show();
            }
        }) {

        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    //Metodo que se encarga de cargar los datos en la lista
    private void cargarLista(ArrayList<Usuario> usuarios) {
        adaptador = new ArrayAdapter<String>(this, R.layout.list_item, R.id.nombre_item, obtenerUsuarios(usuarios));
        ListView listaView = findViewById(R.id.lista_usuario);
        listaView.setAdapter(adaptador);
        // Agregar un listener para manejar los clicks sobre los elementos de la lista
        listaView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                // Obtener los datos del usuario seleccionado
                int id = usuarios.get(i).getId();
                String nombre = usuarios.get(i).getNombre();
                String usuario = usuarios.get(i).getUsuario();
                String contrasena = usuarios.get(i).getContrasena();
                String email = usuarios.get(i).getEmail();
                int telefono = usuarios.get(i).getTelefono();
                String rol = usuarios.get(i).getRol();

                // Mostrar una alerta del usuario seleccionado
                AlertDialog.Builder builder = new AlertDialog.Builder(ListaUsuarios.this);
                builder.setMessage(" Nombre: " + nombre + "\n Username: " + usuario + "\n Contraseña: " + contrasena + "\n Email: " + email + "\n Telefono: " + telefono + "\n Rol: " + rol)
                        .setPositiveButton("Eliminar Usuario", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Response.Listener<String> respuesta = new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        Log.d("RESPUESTA", response); // Agregar este Log para ver la respuesta del servidor

                                        try {
                                            JSONObject jsonRespuesta = new JSONObject(response);
                                            boolean res = jsonRespuesta.getBoolean("success");
                                            if (res == true){
                                                Toast.makeText(ListaUsuarios.this, "Usuario Eliminado", Toast.LENGTH_SHORT).show();
                                            }else{
                                                android.app.AlertDialog.Builder alerta = new android.app.AlertDialog.Builder(ListaUsuarios.this);
                                                alerta.setMessage("Error al eliminar")
                                                        .setNegativeButton("Reintentar", null)
                                                        .create()
                                                        .show();
                                            }
                                        } catch (JSONException e) {
                                            e.getMessage();
                                        }
                                    }
                                };
                                EliminarUsuarioRequest eur = new EliminarUsuarioRequest(usuario, respuesta);
                                RequestQueue cola = Volley.newRequestQueue(ListaUsuarios.this);
                                cola.add(eur);
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
        });
    }

    private ArrayList<String> obtenerUsuarios(ArrayList<Usuario> usuarios) {
        ArrayList<String> username = new ArrayList<>();
        for (Usuario usuario : usuarios) {
            username.add(usuario.getUsuario());
        }
        return username;
    }



}