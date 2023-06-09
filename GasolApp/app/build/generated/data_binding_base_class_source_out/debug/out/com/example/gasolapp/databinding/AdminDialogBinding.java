// Generated by view binder compiler. Do not edit!
package com.example.gasolapp.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.gasolapp.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class AdminDialogBinding implements ViewBinding {
  @NonNull
  private final LinearLayout rootView;

  @NonNull
  public final TextView customDialogText;

  @NonNull
  public final Button eliminarFavoritos;

  @NonNull
  public final Button eliminarHistorial;

  @NonNull
  public final Button eliminarUsuario;

  private AdminDialogBinding(@NonNull LinearLayout rootView, @NonNull TextView customDialogText,
      @NonNull Button eliminarFavoritos, @NonNull Button eliminarHistorial,
      @NonNull Button eliminarUsuario) {
    this.rootView = rootView;
    this.customDialogText = customDialogText;
    this.eliminarFavoritos = eliminarFavoritos;
    this.eliminarHistorial = eliminarHistorial;
    this.eliminarUsuario = eliminarUsuario;
  }

  @Override
  @NonNull
  public LinearLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static AdminDialogBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static AdminDialogBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.admin_dialog, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static AdminDialogBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.custom_dialog_text;
      TextView customDialogText = ViewBindings.findChildViewById(rootView, id);
      if (customDialogText == null) {
        break missingId;
      }

      id = R.id.eliminarFavoritos;
      Button eliminarFavoritos = ViewBindings.findChildViewById(rootView, id);
      if (eliminarFavoritos == null) {
        break missingId;
      }

      id = R.id.eliminarHistorial;
      Button eliminarHistorial = ViewBindings.findChildViewById(rootView, id);
      if (eliminarHistorial == null) {
        break missingId;
      }

      id = R.id.eliminarUsuario;
      Button eliminarUsuario = ViewBindings.findChildViewById(rootView, id);
      if (eliminarUsuario == null) {
        break missingId;
      }

      return new AdminDialogBinding((LinearLayout) rootView, customDialogText, eliminarFavoritos,
          eliminarHistorial, eliminarUsuario);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
