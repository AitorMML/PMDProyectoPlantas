package com.iteso.pmdproyectoplantas;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

import com.iteso.pmdproyectoplantas.R;

public class DialogContinuarSinImagen extends DialogFragment {
    /*public boolean continuarAsi = false;
    public Object var;*/

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(R.string.activity_add_plant_dialog_message)
                .setPositiveButton("Ok", (DialogInterface dialog, int id)->{
                }).setIcon(android.R.drawable.ic_dialog_alert);
        // Create the AlertDialog object and return it
        return builder.create();
    }
}
