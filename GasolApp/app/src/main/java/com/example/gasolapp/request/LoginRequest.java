package com.example.gasolapp.request;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;
//Clase de request que envia los parametros introducidos en la ventana de login al fichero php para que realice la consulta a la bd
public class LoginRequest extends StringRequest {
    private static final String ruta = "https://gasolapp.000webhostapp.com/login.php";
    private final Map<String, String> parametros;
    public LoginRequest (String usuario, String contrasena, Response.Listener<String> listener){
        super(Method.POST, ruta,listener, null);
        parametros = new HashMap<>();
        parametros.put("usuario", usuario+"");
        parametros.put("contrasena", contrasena+"");
    }


    @Override
    protected Map<String, String> getParams() {
        return parametros;
    }
}
