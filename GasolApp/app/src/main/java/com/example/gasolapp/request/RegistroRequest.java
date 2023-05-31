package com.example.gasolapp.request;


import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class RegistroRequest extends StringRequest{
    private static final String ruta = "https://gasolapp.000webhostapp.com/registro.php";
    private final Map<String, String> parametros;
    public RegistroRequest (String nombre, String usuario, String contrasena, String email, int telefono, String rol, Response.Listener<String> listener){
        super(Method.POST, ruta,listener, null);
        parametros = new HashMap<>();
        parametros.put("nombre", nombre+"");
        parametros.put("usuario", usuario+"");
        parametros.put("contrasena", contrasena+"");
        parametros.put("email", email+"");
        parametros.put("telefono", telefono+"");
        parametros.put("rol", rol+"");
    }


    @Override
    protected Map<String, String> getParams() {
        return parametros;
    }
}
