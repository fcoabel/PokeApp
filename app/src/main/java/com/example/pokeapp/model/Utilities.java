package com.example.pokeapp.model;

import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.google.android.material.snackbar.Snackbar;

/**
 * Clase de utilidades.
 */
public class Utilities {

    /**
     * Metodo que muestra un Toast en pantalla.
     * @param context, contexto donde se muestra el toast.
     * @param message, mensaje a mostrar.
     * @param duration, duracion del toast.
     */
    public static void showToastMessage(Context context, String message, int duration){
        Toast.makeText(context, message, duration).show();
    }

    /**
     * Metodo que muestra un Snackbar en pantalla.
     * @param view, vista donde se muestra el snackbar.
     * @param message, mensaje a mostrar.
     * @param duration, duracion del snackbar.
     */
    public static void showSnackbarMessage(View view, String message, int duration){
        Snackbar.make(view, message, duration).show();
    }

    /**
     * Metodo que oculta el teclado.
     * @param view, vista donde se oculta el teclado.
     * @param context, contexto donde se oculta el teclado.
     */
    public static void closeKeyboard(View view, Context context){
        // Cerrar teclado
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (view != null) {
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    /**
     *  Metodo para mostrar Dialogo
     * @param context, contexto donde se muestra el dialogo.
     * @param title, titulo del dialogo.
     * @param message, mensaje del dialogo.
     */
    public static void showDialog(Context context, String title, String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title); // Título del diálogo
        builder.setMessage(message); // Mensaje

        // Botón de Aceptar
        builder.setPositiveButton("Aceptar", (dialog, which) -> {
            dialog.dismiss(); // Cierra el diálogo
        });

        // Mostrar el diálogo
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
