package com.example.gasolapp.request;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class EliminarFavoritosRequest extends StringRequest {
    private static final String ruta = "https://gasolapp.000webhostapp.com/eliminarFavoritos.php";
    private final Map<String, String> parametros;

    public EliminarFavoritosRequest(int field1, Response.Listener<String> listener){
        super(Request.Method.POST, ruta,listener, null);
        parametros = new HashMap<>();
        parametros.put("field1", field1+"");
    }


    @Override
    protected Map<String, String> getParams() {
        return parametros;
    }
}

