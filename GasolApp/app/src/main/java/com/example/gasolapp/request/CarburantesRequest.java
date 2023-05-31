package com.example.gasolapp.request;


import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class CarburantesRequest extends StringRequest {
    private static final String ruta = "https://gasolapp.000webhostapp.com/sp95.php";
    private final Map<String, String> params;

    public CarburantesRequest(Response.Listener<String> listener) {
        super(Method.POST, ruta, listener, null);
        params = new HashMap<>();
    }

    @Override
    protected Map<String, String> getParams() {
        return params;
    }
}