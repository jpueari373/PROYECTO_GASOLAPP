package com.example.gasolapp;


import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class FavoritosRequest extends StringRequest{
    private static final String ruta = "https://gasolapp.000webhostapp.com/insertarFavoritos.php";
    private final Map<String, String> parametros;
    public FavoritosRequest(int field1, String provincia, String municipio, String localidad, int codigo_po, String direccion, double longitud, double latitud
            ,double precio_gas, double precio_g_3, double precio_g_5, double precio_g_6, double precio_g_7, String rotulo
            ,String horario, String fecha,String usuario, Response.Listener<String> listener){
        super(Method.POST, ruta,listener, null);
        parametros = new HashMap<>();
        parametros.put("field1", field1+"");
        parametros.put("provincia", provincia+"");
        parametros.put("municipio", municipio+"");
        parametros.put("localidad", localidad+"");
        parametros.put("codigo_po", codigo_po+"");
        parametros.put("direccion", direccion+"");
        parametros.put("longitud", longitud+"");
        parametros.put("latitud", latitud+"");
        parametros.put("precio_gas", precio_gas+"");
        parametros.put("precio_g_3", precio_g_3+"");
        parametros.put("precio_g_5", precio_g_5+"");
        parametros.put("precio_g_6", precio_g_6+"");
        parametros.put("precio_g_7", precio_g_7+"");
        parametros.put("rotulo", rotulo+"");
        parametros.put("horario", horario+"");
        parametros.put("fecha", fecha+"");
        parametros.put("usuario", usuario+"");
    }


    @Override
    protected Map<String, String> getParams() {
        return parametros;
    }
}
