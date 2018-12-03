package com.iteso.pmdproyectoplantas;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;

import com.iteso.pmdproyectoplantas.beans.Evento;

import java.util.Calendar;

public class ActivityEvents extends AppCompatActivity {

    FloatingActionButton addEvent;
    Calendar calendar;
    DatePicker datePicker;
    DatePickerDialog datePickerDialog;
    int day, month, year;


    /**
     * poner lista con los eventos
     * día del mes y lo que hace
     * fab abre un calne
     *
     */


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);


        addEvent = findViewById(R.id.activity_event_add);
        //datePicker = findViewById(R.id.activity_events_picker);

        FragmentEventos fragmentEventos = new FragmentEventos();


        addEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //pedir datos de evento
                //parcelable?
                Intent intent = new Intent(ActivityEvents.this, ActivityAddEvent.class);
                startActivity(intent);
            }
        });

    }
}
