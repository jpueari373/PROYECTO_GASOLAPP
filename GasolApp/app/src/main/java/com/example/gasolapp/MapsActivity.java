package com.example.gasolapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.gasolapp.model.Gasolineras;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.gasolapp.databinding.ActivityMapsBinding;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMapClickListener, GoogleMap.OnInfoWindowClickListener {

    private GoogleMap mMap;
    private ActivityMapsBinding binding;
    LatLng actual;
    LatLng gasolineraSeleccionada;
    private Polyline rutaPolyline;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        getLocalizacion();
        Button boton_buscar_provincia = findViewById(R.id.boton_buscar_mapa);

        //Accion del boton de buscar por provincia
        boton_buscar_provincia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText ProvinciaM = findViewById(R.id.provincia_mapa);
                String provincia = ProvinciaM.getText().toString();
                String url= "https://gasolapp.000webhostapp.com/gasolinerasProvincias.php";
                //metodo que realiza la consulta a la bd, donde le pasamos el campo y la url del fichero php
                mMap.clear();
                realizarConsultaProvincia(provincia,url);

            }
        });

    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setOnMapClickListener(this);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mMap.setMyLocationEnabled(true);//activa la posicio actual
        mMap.getUiSettings().setMyLocationButtonEnabled(false);
        LocationManager locationManager = (LocationManager) MapsActivity.this.getSystemService(Context.LOCATION_SERVICE);
        LocationListener locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                actual = new LatLng(location.getLatitude(), location.getLongitude()); //objeto de posicion en el mapa con los datos de la ubicacion actual
                mMap.moveCamera(CameraUpdateFactory.newLatLng(actual));//mueve la camara a la ubicacion actual
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(actual, 12), 5000, null);//animacion de la camara
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 7, locationListener);//actualiza a la ubicacion actual al mover la camara la distancia marcada
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(@NonNull Marker marker) {
                marker.showInfoWindow();
                //Toma la posicion de la gasolinera seleccionada
                gasolineraSeleccionada = marker.getPosition();
                //En el gps del emulador da error por la ubicacion, pero en el movil funciona
                //trazarRuta(actual,gasolineraSeleccionada);
                return true;
            }
        });
    }

    @Override
    public void onInfoWindowClick(@NonNull Marker marker) {

    }

    @Override
    public void onMapClick(@NonNull LatLng latLng) {

    }


    //prueba geolocalizacion
    private void getLocalizacion() {
        int permiso = ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION);
        if(permiso == PackageManager.PERMISSION_DENIED){
            if(ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.ACCESS_FINE_LOCATION)){
            }else{
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            }
        }
    }
    //Este metodo realiza una consulta a la bd, mediante un fichero php, donde se le pasa el valor de la provincia y devuelve las gasolineras con esa provincia
    private void realizarConsultaProvincia(String provincia, String url) {
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
                            //Si el array es 0 quiere decir que esa provincia no tiene gasolineras
                            if (jsonArray.length() == 0) {
                                Toast.makeText(MapsActivity.this, "No se encontraron gasolineras para la provincia seleccionada", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            ArrayList<Gasolineras> gasolineras = new ArrayList<>();
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject gasolinera = jsonArray.getJSONObject(i);
                                int field1 = gasolinera.getInt("Field1");
                                String provincia = gasolinera.getString("Provincia");
                                String municipio = gasolinera.getString("Municipio");
                                String localidad = gasolinera.getString("Localidad");
                                int codigo_postal = gasolinera.getInt("Codigo_po");
                                String direccion = gasolinera.getString("Direccion");
                                Double longitud = gasolinera.getDouble("Longitud");
                                Double latitud = gasolinera.getDouble("Latitud");
                                Double precio_gas = gasolinera.getDouble("Precio_gas");
                                Double precio_g_3 = gasolinera.getDouble("Precio_g_3");
                                Double precio_g_5 = gasolinera.getDouble("Precio_g_5");
                                Double precio_g_6 = gasolinera.getDouble("Precio_g_6");
                                Double precio_g_7 = gasolinera.getDouble("Precio_g_7");
                                String rotulo = gasolinera.getString("Rotulo");
                                String horario = gasolinera.getString("Horario");
                                String fecha =  gasolinera.getString("fecha");
                                //segun el nombre de la gasolinera, mostrara un logo distinto en el mapa
                                switch (rotulo){
                                    case "RED CAR OIL":
                                        mMap.addMarker(new MarkerOptions().position(new LatLng(latitud, longitud))
                                                .title(rotulo)
                                                .snippet("Dirección: " + direccion + "\nSP95: " + precio_gas + " l/€" + "\nSP98: " + precio_g_3 + " l/€"
                                                        + "\nA: " + precio_g_7 + " l-/€" + "\nA+: " + precio_g_6 + " l/€" + "\nB: " + precio_g_5 + " l/€" + "\nHorario: " + horario)
                                                .icon(BitmapDescriptorFactory.fromResource(R.drawable.redcaroil)));
                                        break;
                                    case "REPSOL":
                                        mMap.addMarker(new MarkerOptions().position(new LatLng(latitud, longitud)).title(rotulo)
                                                .snippet("Dirección: " + direccion + "\nSP95: " + precio_gas + " l/€" + "\nSP98: " + precio_g_3 + " l/€"
                                                        + "\nA: " + precio_g_7 + " l/€" + "\nA+: " + precio_g_6 + " l/€" + "\nB: " + precio_g_5 + " l/€" + "\nHorario: " + horario)
                                                .icon(BitmapDescriptorFactory.fromResource(R.drawable.repsol)));
                                        break;
                                    case "CEPSA":
                                        mMap.addMarker(new MarkerOptions().position(new LatLng(latitud, longitud)).title(rotulo)
                                                .snippet("Dirección: " + direccion + "\nSP95: " + precio_gas + " l/€" + "\nSP98: " + precio_g_3 + " l/€"
                                                        + "\nA: " + precio_g_7 + " l/€" + "\nA+: " + precio_g_6 + " l/€" + "\nB: " + precio_g_5 + " l/€" + "\nHorario: " + horario)
                                                .icon(BitmapDescriptorFactory.fromResource(R.drawable.cepsa)));
                                        break;
                                    case "AGLA":
                                        mMap.addMarker(new MarkerOptions().position(new LatLng(latitud, longitud)).title(rotulo)
                                                .snippet("Dirección: " + direccion + "\nSP95: " + precio_gas + " l/€" + "\nSP98: " + precio_g_3 + " l/€"
                                                        + "\nA: " + precio_g_7 + " l/€" + "\nA+: " + precio_g_6 + " l/€" + "\nB: " + precio_g_5 + " l/€" + "\nHorario: " + horario)
                                                .icon(BitmapDescriptorFactory.fromResource(R.drawable.agla)));
                                        break;
                                    case "SHELL":
                                        mMap.addMarker(new MarkerOptions().position(new LatLng(latitud, longitud)).title(rotulo)
                                                .snippet("Dirección: " + direccion + "\nSP95: " + precio_gas + " l/€" + "\nSP98: " + precio_g_3 + " l/€"
                                                        + "\nA: " + precio_g_7 + " l/€" + "\nA+: " + precio_g_6 + " l/€" + "\nB: " + precio_g_5 + " l/€" + "\nHorario: " + horario)
                                                .icon(BitmapDescriptorFactory.fromResource(R.drawable.shell)));
                                        break;
                                    case "CARREFOUR":
                                        mMap.addMarker(new MarkerOptions().position(new LatLng(latitud, longitud)).title(rotulo)
                                                .snippet("Dirección: " + direccion + "\nSP95: " + precio_gas + " l/€" + "\nSP98: " + precio_g_3 + " l/€"
                                                        + "\nA: " + precio_g_7 + " l/€" + "\nA+: " + precio_g_6 + " l/€" + "\nB: " + precio_g_5 + " l/€" + "\nHorario: " + horario)
                                                .icon(BitmapDescriptorFactory.fromResource(R.drawable.carrefour)));
                                        break;
                                    case "ALCAMPO":
                                        mMap.addMarker(new MarkerOptions().position(new LatLng(latitud, longitud)).title(rotulo)
                                                .snippet("Dirección: " + direccion + "\nSP95: " + precio_gas + " l/€" + "\nSP98: " + precio_g_3 + " l/€"
                                                        + "\nA: " + precio_g_7 + " l/€" + "\nA+: " + precio_g_6 + " l/€" + "\nB: " + precio_g_5 + " l/€" + "\nHorario: " + horario)
                                                .icon(BitmapDescriptorFactory.fromResource(R.drawable.alcampo)));
                                        break;
                                    case "BALLENOIL":
                                        mMap.addMarker(new MarkerOptions().position(new LatLng(latitud, longitud)).title(rotulo)
                                                .snippet("Dirección: " + direccion + "\nSP95: " + precio_gas + " l/€" + "\nSP98: " + precio_g_3 + " l/€"
                                                        + "\nA: " + precio_g_7 + " l/€" + "\nA+: " + precio_g_6 + " l/€" + "\nB: " + precio_g_5 + " l/€" + "\nHorario: " + horario)
                                                .icon(BitmapDescriptorFactory.fromResource(R.drawable.ballenoil)));
                                        break;
                                    case "PETROPRIX":
                                        mMap.addMarker(new MarkerOptions().position(new LatLng(latitud, longitud)).title(rotulo)
                                                .snippet("Dirección: " + direccion + "\nSP95: " + precio_gas + " l/€" + "\nSP98: " + precio_g_3 + " l/€"
                                                        + "\nA: " + precio_g_7 + " l/€" + "\nA+: " + precio_g_6 + " l/€" + "\nB: " + precio_g_5 + " l/€" + "\nHorario: " + horario)
                                                .icon(BitmapDescriptorFactory.fromResource(R.drawable.ballenoil)));
                                        break;
                                    default:
                                        mMap.addMarker(new MarkerOptions().position(new LatLng(latitud, longitud)).title(rotulo)
                                                .snippet("Dirección: " + direccion + "\nSP95: " + precio_gas + " l/€" + "\nSP98: " + precio_g_3 + " l/€"
                                                        + "\nA: " + precio_g_7 + " l/€" + "\nA+: " + precio_g_6 + " l/€" + "\nB: " + precio_g_5 + " l/€" + "\nHorario: " + horario)
                                                .icon(BitmapDescriptorFactory.fromResource(R.drawable.gasolineras)));
                                        mMap.setInfoWindowAdapter(new MyinfoWindowAdapter(MapsActivity.this));
                                        break;
                                }

                                Gasolineras objetoGasolinera  = new Gasolineras(field1,provincia,municipio,localidad,codigo_postal,direccion,longitud,latitud
                                        ,precio_gas,precio_g_3,precio_g_5,precio_g_6,precio_g_7,rotulo,horario,fecha);
                                gasolineras.add(objetoGasolinera);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(MapsActivity.this, "Inserte una Provincia válida ", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MapsActivity.this, "Error: " + error.toString(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {//Le pasamos el campo de Provincia al fichero php para la consulta
                Map<String, String> params = new HashMap<String, String>();
                params.put("Provincia", provincia);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }


    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }

    //Traza la ruta de la ubicacion actual hasta la gasolinera seleccionada
    //Da error al usarlo en el emulador de android por la ubicacion del dispositivo
    private void trazarRuta(LatLng origen, LatLng destino) {
        // Borra la ruta anterior si existe
        if (rutaPolyline != null) {
            rutaPolyline.remove();
        }

        // Aquí trazas la ruta entre los puntos origen y destino
        // Puedes usar la clase PolylineOptions para agregar una línea en el mapa
        PolylineOptions options = new PolylineOptions()
                .add(origen)
                .add(destino)
                .width(5)
                .color(Color.RED);
        rutaPolyline = mMap.addPolyline(options);
    }
    //El metodo comentado traza la ruta por las carreteras del mapa sin trazar una linea recta, pero tiene el inconveniente que pide facturación para poder usar
    //la api de directions de google, esta activada pero tendria que pagar para usarla.
    /*
    private void trazarRuta(LatLng origen, LatLng destino) {
    GeoApiContext context = new GeoApiContext.Builder()
            .apiKey("AIzaSyBwVRaBdxMMO40HHLcrqP0paTdH8lRtpdE")
            .build();

    DirectionsApiRequest request = DirectionsApi.getDirections(context,
            origen.latitude + "," + origen.longitude,
            destino.latitude + "," + destino.longitude);

    try {
        DirectionsResult result = request.await();
        if (result.routes != null && result.routes.length > 0) {
            DirectionsRoute ruta = result.routes[0];

            List<LatLng> puntosRuta = new ArrayList<>();
            for (DirectionsLeg leg : ruta.legs) {
                for (DirectionsStep step : leg.steps) {
                    puntosRuta.add(new LatLng(step.startLocation.lat, step.startLocation.lng));
                    puntosRuta.addAll(PolylineEncoding.decode(step.polyline.getEncodedPath()));
                    puntosRuta.add(new LatLng(step.endLocation.lat, step.endLocation.lng));
                }
            }

            if (rutaPolyline != null) {
                rutaPolyline.remove();
            }

            PolylineOptions opcionesRuta = new PolylineOptions()
                    .addAll(puntosRuta)
                    .width(8)
                    .color(Color.BLUE)
                    .geodesic(true);

            rutaPolyline = mMap.addPolyline(opcionesRuta);
     */
}