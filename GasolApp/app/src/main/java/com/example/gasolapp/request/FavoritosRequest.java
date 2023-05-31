package com.example.gasolapp.request;


import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class FavoritosRequest extends StringRequest{
    private static final String ruta = "https://gasolapp.000webhostapp.com/insertarFavoritos.php";
    private final Map<String, String> parametros;
    public FavoritosRequest(int Field1, String provincia, String municipio, String localidad, int codigo_po, String direccion, double longitud, double latitud
            ,double precio_gas, double precio_g_3, double precio_g_5, double precio_g_6, double precio_g_7, String rotulo
            ,String horario, String fecha,String usuario, Response.Listener<String> listener){
        super(Method.POST, ruta,listener, null);
        parametros = new HashMap<>();
        parametros.put("Field1", Field1+"");
        parametros.put("Provincia", provincia+"");
        parametros.put("Municipio", municipio+"");
        parametros.put("Localidad", localidad+"");
        parametros.put("Codigo_po", codigo_po+"");
        parametros.put("Direccion", direccion+"");
        parametros.put("Longitud", longitud+"");
        parametros.put("Latitud", latitud+"");
        parametros.put("Precio_gas", precio_gas+"");
        parametros.put("Precio_g_3", precio_g_3+"");
        parametros.put("Precio_g_5", precio_g_5+"");
        parametros.put("Precio_g_6", precio_g_6+"");
        parametros.put("Precio_g_7", precio_g_7+"");
        parametros.put("Rotulo", rotulo+"");
        parametros.put("Horario", horario+"");
        parametros.put("fecha", fecha+"");
        parametros.put("usuario", usuario+"");
    }


    @Override
    protected Map<String, String> getParams() {
        return parametros;
    }
}