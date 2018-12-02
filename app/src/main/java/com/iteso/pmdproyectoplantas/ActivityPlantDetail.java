package com.iteso.pmdproyectoplantas;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.iteso.pmdproyectoplantas.beans.Planta;
import com.iteso.pmdproyectoplantas.tools.Constants;
import com.iteso.pmdproyectoplantas.tools.ImageHelper;

public class ActivityPlantDetail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plant_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Planta planta = getIntent().getParcelableExtra(Constants.extraBeanPlanta);
        if(planta != null) {
            getSupportActionBar().setTitle(planta.getNombre());
            ((TextView)findViewById(R.id.apd_especie_sub)).setText(planta.getEspecie());
            ((TextView)findViewById(R.id.apd_grupos_sub)).setText("Flores de sombra");
            ((TextView)findViewById(R.id.apd_cuidados_sub)).setText(planta.getCuidados());
            ((TextView)findViewById(R.id.apd_alarmas_sub)).setText("No hay alarmas");
            ((TextView)findViewById(R.id.apd_eventos_sub)).setText("Tulipanes florecieron, y 9 más...");

            ImageHelper.loadImage(findViewById(R.id.backdrop), planta);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
