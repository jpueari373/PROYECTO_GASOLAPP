package com.example.gasolapp.request;


import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class HistorialRequest extends StringRequest{
    private static final String ruta = "https://gasolapp.000webhostapp.com/historial.php";
    private final Map<String, String> parametros;
    public HistorialRequest(int field1, String provincia, String municipio, String localidad, String rotulo, String direccion,
            String carburante, double precio_carburante, double litros_repostados, double coste_total, String fecha, String usuario, Response.Listener<String> listener){
        super(Method.POST, ruta,listener, null);
        parametros = new HashMap<>();
        parametros.put("field1", field1+"");
        parametros.put("provincia", provincia+"");
        parametros.put("municipio", municipio+"");
        parametros.put("localidad", localidad+"");
        parametros.put("rotulo", rotulo+"");
        parametros.put("direccion", direccion+"");
        parametros.put("carburante", carburante+"");
        parametros.put("precio_carburante", precio_carburante+"");
        parametros.put("litros_repostados", litros_repostados+"");
        parametros.put("coste_total", coste_total+"");
        parametros.put("fecha", fecha+"");
        parametros.put("usuario", usuario+"");
    }


    @Override
    protected Map<String, String> getParams() {
        return parametros;
    }
}
