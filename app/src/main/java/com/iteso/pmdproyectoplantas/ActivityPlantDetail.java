package com.iteso.pmdproyectoplantas;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.TimeUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.iteso.pmdproyectoplantas.beans.Alarma;
import com.iteso.pmdproyectoplantas.beans.Planta;
import com.iteso.pmdproyectoplantas.tools.Constants;
import com.iteso.pmdproyectoplantas.tools.ImageHelper;

import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

public class ActivityPlantDetail extends AppCompatActivity
        implements DialogAlarmAdd.NoticeDialogListener{
    private DialogFragment dialogoAlarmaNueva = new DialogAlarmAdd();
    private DatabaseReference mDataReference;
    private ValueEventListener valueEventListener;
    private List<String> alarmasId = new LinkedList<>();
    LinearLayout contenedorCuidados;
    Planta planta;

    public ActivityPlantDetail() {
        valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.getChildrenCount() > 0) {
                    contenedorCuidados.removeAllViewsInLayout();
                    alarmasId.clear();
                    for(DataSnapshot data : dataSnapshot.getChildren()) {
                        Alarma alarma = data.getValue(Alarma.class);
                        alarmasId.add(data.getKey());
                        TextView textView = new TextView(ActivityPlantDetail.this);
                        String contenido = "";
                        if (alarma.isRegar()) { contenido+="Regar, "; }
                        if (alarma.isCambiarTierra()) { contenido+="Cambiar tierra, "; }
                        if (alarma.isDesparacitar()) { contenido+="Desparacitar, "; }
                        if (alarma.isPodar()) { contenido+="Podar, "; }
                        if (alarma.getOtro()) {contenido+=alarma.getOtroCampo()+", ";}
                        contenido = contenido.substring(0, contenido.lastIndexOf(", "));
                        contenido+=": "+alarma.getTime();
                        if(!alarma.isOnce()) { contenido+=" para el "+alarma.getDate(); }
                        contenido+=" (cancelar)";
                        textView.setText(contenido);
                        textView.setOnClickListener((View v)->{ ActivityPlantDetail.this.onAlarmaBorrar(v);});
                        contenedorCuidados.addView(textView);
                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        };
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plant_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        contenedorCuidados = findViewById(R.id.activity_plant_cuidados_contenedor);

        planta = getIntent().getParcelableExtra(Constants.extraBeanPlanta);
        if(planta != null) {
            getSupportActionBar().setTitle(planta.getNombre());
            ((TextView)findViewById(R.id.apd_especie_sub)).setText(planta.getEspecie());
            ((TextView)findViewById(R.id.apd_grupos_sub)).setText("Flores de sombra");
            ((TextView)findViewById(R.id.apd_cuidados_sub)).setText(planta.getCuidados());
            ((TextView)findViewById(R.id.apd_eventos_sub)).setText("Tulipanes florecieron, y 9 más...");

            ImageHelper.loadImage(findViewById(R.id.backdrop), planta);
            findViewById(R.id.apd_add_alarm).setOnClickListener((View v)->{
                dialogoAlarmaNueva.show(getFragmentManager(), "DialogoAlarmaAgregar");
            });
            mDataReference = FirebaseDatabase.getInstance().getReference("users")
                    .child(FirebaseAuth.getInstance().getUid()).child("alarms").child(planta.getPlantaId());
            mDataReference.addValueEventListener(valueEventListener);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {
        Alarma alarma = ((DialogAlarmAdd)dialog).getAlarmData();
        String key = mDataReference.push().getKey();

        if(mDataReference != null && alarma != null) {
            mDataReference.child(key).setValue(alarma).addOnCompleteListener( var->{
                if(var.isSuccessful()) {
                    Toast.makeText(this, getString(R.string.dialog_add_alarma_agregado_exitoso)
                            , Toast.LENGTH_SHORT).show();
                } else if(var.isCanceled()) {
                    Toast.makeText(this, getString(R.string.dialog_add_alarma_agregado_fallido)
                            , Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {
        Toast.makeText(this, getString(R.string.texto_comun_cancelar), Toast.LENGTH_SHORT).show();
    }

    public void onAlarmaBorrar(View v) {
        int posAlarma = contenedorCuidados.indexOfChild(v);
        String targetAlarma = alarmasId.get(posAlarma);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.dialog_add_borrar_alarma)
                .setPositiveButton(R.string.si, (DialogInterface dialog, int id)->{
                    mDataReference.child(targetAlarma).removeValue().addOnCompleteListener((result)->{
                        if(result.isSuccessful()) {
                            contenedorCuidados.removeViewAt(posAlarma);
                            if (contenedorCuidados.getChildCount()==0) {
                                TextView textView = new TextView(this);
                                textView.setText(R.string.dialog_add_no_hay_alarmas);
                                contenedorCuidados.addView(textView);
                            }
                        } else if(result.isCanceled()) {
                            Toast.makeText(this
                                    , getString(R.string.dialog_add_borrar_alarma_fallido)
                                    , Toast.LENGTH_SHORT).show();
                        }
                    });
                })
                .setNegativeButton(R.string.no, (DialogInterface dialog, int id)->{});
        builder.create().show();
    }
}
