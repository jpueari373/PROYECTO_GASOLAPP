package com.example.gasolapp;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class EliminarHistorialRequest extends StringRequest {
    private static final String ruta = "https://gasolapp.000webhostapp.com/eliminarHistorial.php";
    private final Map<String, String> parametros;

    public EliminarHistorialRequest(int field1, String fecha, Response.Listener<String> listener){
        super(Method.POST, ruta,listener, null);
        parametros = new HashMap<>();
        parametros.put("field1", field1+"");
        parametros.put("fecha", fecha+"");
    }


    @Override
    protected Map<String, String> getParams() {
        return parametros;
    }
}

