package com.example.gasolapp.request;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class EliminarUsuarioRequest extends StringRequest {
    private static final String ruta = "https://gasolapp.000webhostapp.com/eliminarUsuario.php";
    private final Map<String, String> parametros;

    public EliminarUsuarioRequest(String usuario, Response.Listener<String> listener){
        super(Method.POST, ruta,listener, null);
        parametros = new HashMap<>();
        parametros.put("usuario", usuario+"");
    }


    @Override
    protected Map<String, String> getParams() {
        return parametros;
    }
}

