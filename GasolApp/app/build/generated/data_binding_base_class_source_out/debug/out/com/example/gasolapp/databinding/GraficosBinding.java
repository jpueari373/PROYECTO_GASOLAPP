// Generated by view binder compiler. Do not edit!
package com.example.gasolapp.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.gasolapp.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class GraficosBinding implements ViewBinding {
  @NonNull
  private final LinearLayout rootView;

  @NonNull
  public final BarChart graficoBarras;

  @NonNull
  public final PieChart graficoCircular;

  @NonNull
  public final TextView textViewCircular;

  @NonNull
  public final TextView textViewTitulo;

  private GraficosBinding(@NonNull LinearLayout rootView, @NonNull BarChart graficoBarras,
      @NonNull PieChart graficoCircular, @NonNull TextView textViewCircular,
      @NonNull TextView textViewTitulo) {
    this.rootView = rootView;
    this.graficoBarras = graficoBarras;
    this.graficoCircular = graficoCircular;
    this.textViewCircular = textViewCircular;
    this.textViewTitulo = textViewTitulo;
  }

  @Override
  @NonNull
  public LinearLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static GraficosBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static GraficosBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.graficos, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static GraficosBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.grafico_barras;
      BarChart graficoBarras = ViewBindings.findChildViewById(rootView, id);
      if (graficoBarras == null) {
        break missingId;
      }

      id = R.id.grafico_circular;
      PieChart graficoCircular = ViewBindings.findChildViewById(rootView, id);
      if (graficoCircular == null) {
        break missingId;
      }

      id = R.id.textView_circular;
      TextView textViewCircular = ViewBindings.findChildViewById(rootView, id);
      if (textViewCircular == null) {
        break missingId;
      }

      id = R.id.textView_titulo;
      TextView textViewTitulo = ViewBindings.findChildViewById(rootView, id);
      if (textViewTitulo == null) {
        break missingId;
      }

      return new GraficosBinding((LinearLayout) rootView, graficoBarras, graficoCircular,
          textViewCircular, textViewTitulo);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
