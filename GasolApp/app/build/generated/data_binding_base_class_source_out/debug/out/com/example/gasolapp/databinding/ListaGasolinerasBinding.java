// Generated by view binder compiler. Do not edit!
package com.example.gasolapp.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.gasolapp.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ListaGasolinerasBinding implements ViewBinding {
  @NonNull
  private final LinearLayout rootView;

  @NonNull
  public final Button botonBuscarMunicipio;

  @NonNull
  public final Button botonBuscarProvincia;

  @NonNull
  public final ListView lista;

  @NonNull
  public final EditText municipioLista;

  @NonNull
  public final EditText provinciaLista;

  private ListaGasolinerasBinding(@NonNull LinearLayout rootView,
      @NonNull Button botonBuscarMunicipio, @NonNull Button botonBuscarProvincia,
      @NonNull ListView lista, @NonNull EditText municipioLista, @NonNull EditText provinciaLista) {
    this.rootView = rootView;
    this.botonBuscarMunicipio = botonBuscarMunicipio;
    this.botonBuscarProvincia = botonBuscarProvincia;
    this.lista = lista;
    this.municipioLista = municipioLista;
    this.provinciaLista = provinciaLista;
  }

  @Override
  @NonNull
  public LinearLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ListaGasolinerasBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ListaGasolinerasBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.lista_gasolineras, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ListaGasolinerasBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.boton_buscar_municipio;
      Button botonBuscarMunicipio = ViewBindings.findChildViewById(rootView, id);
      if (botonBuscarMunicipio == null) {
        break missingId;
      }

      id = R.id.boton_buscar_provincia;
      Button botonBuscarProvincia = ViewBindings.findChildViewById(rootView, id);
      if (botonBuscarProvincia == null) {
        break missingId;
      }

      id = R.id.lista;
      ListView lista = ViewBindings.findChildViewById(rootView, id);
      if (lista == null) {
        break missingId;
      }

      id = R.id.municipio_lista;
      EditText municipioLista = ViewBindings.findChildViewById(rootView, id);
      if (municipioLista == null) {
        break missingId;
      }

      id = R.id.provincia_lista;
      EditText provinciaLista = ViewBindings.findChildViewById(rootView, id);
      if (provinciaLista == null) {
        break missingId;
      }

      return new ListaGasolinerasBinding((LinearLayout) rootView, botonBuscarMunicipio,
          botonBuscarProvincia, lista, municipioLista, provinciaLista);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}