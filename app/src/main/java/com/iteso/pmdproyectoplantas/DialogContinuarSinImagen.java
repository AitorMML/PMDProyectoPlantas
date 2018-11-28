package com.iteso.pmdproyectoplantas;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

import com.iteso.pmdproyectoplantas.R;

public class DialogContinuarSinImagen extends DialogFragment {
    public boolean continuarAsi = false;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(R.string.activity_add_plant_dialog_message)
                .setPositiveButton(R.string.si, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        continuarAsi = true;
                    }
                })
                .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        continuarAsi = false;
                    }
                });
        // Create the AlertDialog object and return it
        return builder.create();
    }

}
