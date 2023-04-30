package com.example.gasolapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;
//Clase de adaptador para la informacion de los marcadores, toma los elementos del layout info_gasolinera
// y muestra los datos del marcador en el mapa
public class MyinfoWindowAdapter implements GoogleMap.InfoWindowAdapter {
    private final Context mContext;

    public MyinfoWindowAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public View getInfoContents(Marker marker) {
        return null;
    }

    @Override
    public View getInfoWindow(Marker marker) {
        View infoView = LayoutInflater.from(mContext).inflate(R.layout.info_gasolinera, null);
        TextView titulo = infoView.findViewById(R.id.rotulo_gasolinera);
        TextView info = infoView.findViewById(R.id.info_gasolinera);

        titulo.setText(marker.getTitle());
        info.setText(marker.getSnippet());

        // Deshabilitar evento OnInfoWindowClickListener mientras se está tocando el View que contiene el info window adapter
        infoView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        // Deshabilitar evento OnInfoWindowClickListener
                        marker.hideInfoWindow();

                        return true;
                    case MotionEvent.ACTION_UP:
                        // Habilitar evento OnInfoWindowClickListener
                        marker.showInfoWindow();
                        return true;
                }
                return false;
            }
        });



        return infoView;
    }
}
