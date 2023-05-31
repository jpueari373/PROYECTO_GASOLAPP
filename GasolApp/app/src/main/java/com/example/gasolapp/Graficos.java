package com.example.gasolapp;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.gasolapp.model.HistorialModel;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Graficos extends AppCompatActivity {
    private ArrayAdapter<String> adaptador; //Adaptador controlador de datos
    String usuario;
    PieChart pieChart;
    BarChart barChart;
    String url="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.graficos);
        Bundle bundle = this.getIntent().getExtras();
        String nombreUsuario = bundle.getString("usuario");
        usuario = nombreUsuario;

        pieChart = findViewById(R.id.grafico_circular);//grafico circular
        barChart = findViewById(R.id.grafico_barras);//grafico barras
        graficoCircular();
    }
    private void graficoCircular() {
        url = "https://gasolapp.000webhostapp.com/carburantes.php";//url del fichero php para obtener los datos para cargarlos en las graficas
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success");
                            if (success) {
                                JSONObject mediaObject = jsonObject.getJSONObject("media");

                                float precio_sp95 = Float.parseFloat(mediaObject.getString("media_Precio_gas"));
                                float precio_sp98 = Float.parseFloat(mediaObject.getString("media_Precio_g_3"));
                                float precio_A = Float.parseFloat(mediaObject.getString("media_Precio_g_5"));
                                float precio_APlus = Float.parseFloat(mediaObject.getString("media_Precio_g_6"));
                                float precio_B = Float.parseFloat(mediaObject.getString("media_Precio_g_7"));
                                //array de PieEntry donde cada nuevo elemento es un nuevo campo en el grafico
                                ArrayList<PieEntry> pieEntries = new ArrayList<>();
                                pieEntries.add(new PieEntry(precio_sp95, "SP95"));
                                pieEntries.add(new PieEntry(precio_sp98, "SP98"));
                                pieEntries.add(new PieEntry(precio_A, "A"));
                                pieEntries.add(new PieEntry(precio_APlus, "A+"));
                                pieEntries.add(new PieEntry(precio_B, "B"));
                                //colores para cada uno de los PieEntry
                                int[] colors = new int[]{Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW, Color.parseColor("#FFA500")};
                                //para añadir los valores a la grafica
                                PieDataSet pieDataSet = new PieDataSet(pieEntries, "Precio medio de los carburantes");
                                //para aplicarle el color a cada elemento de la grafica
                                pieDataSet.setColors(colors);
                                //aplicar color y tamaño del texto
                                pieDataSet.setValueTextColor(Color.BLACK);
                                pieDataSet.setValueTextSize(16f);
                                //objeto de la grafica donde le pasamos los valores anteriores y le aplicamos animaciones
                                PieData pieData = new PieData(pieDataSet);
                                pieChart.setData(pieData);
                                pieChart.setCenterText("Media carburantes");
                                Description description = new Description();
                                description.setText("");
                                pieChart.setDescription(description);
                                pieChart.animateY(2000);
                                pieChart.invalidate();
                                //llamada al metodo que crea el grafico de barra a partir de los valores anteriores
                                crearGraficoHistorialBarra(precio_sp95, precio_sp98, precio_A, precio_APlus, precio_B);

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), "Error al obtener los datos", Toast.LENGTH_SHORT).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

//metodo que crea el grafico de barras con los valores del grafico circular
    private void crearGraficoHistorialBarra(float precio_sp95, float precio_sp98, float precio_A, float precio_APlus, float precio_B) {
        ArrayList<BarEntry> barEntries = new ArrayList<>();
        ArrayList<String> labels = new ArrayList<>();
        //barEntries son las barras de la grafica cada barEntries representa a una barra dentro de la grafica
        barEntries.add(new BarEntry(0, precio_sp95));
        barEntries.add(new BarEntry(1, precio_sp98));
        barEntries.add(new BarEntry(2, precio_A));
        barEntries.add(new BarEntry(3, precio_APlus));
        barEntries.add(new BarEntry(4, precio_B));

        labels.add("SP95");
        labels.add("SP98");
        labels.add("A");
        labels.add("A+");
        labels.add("B");

        ArrayList<Integer> colors = new ArrayList<>();
        colors.add(Color.RED);
        colors.add(Color.GREEN);
        colors.add(Color.BLUE);
        colors.add(Color.YELLOW);
        colors.add(Color.parseColor("#FFA500"));

        BarDataSet barDataSet = new BarDataSet(barEntries, "Precio medio de los carburantes");
        barDataSet.setColors(colors);
        barDataSet.setValueTextColor(Color.BLACK);
        barDataSet.setValueTextSize(16f);

        BarData barData = new BarData(barDataSet);
        barChart.setData(barData);

        XAxis xAxis = barChart.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(labels));
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularity(1f);
        xAxis.setGranularityEnabled(true);

        Description description = new Description();
        description.setText("");
        barChart.setDescription(description);
        barChart.animateY(2000);
        barChart.invalidate();
    }


}


