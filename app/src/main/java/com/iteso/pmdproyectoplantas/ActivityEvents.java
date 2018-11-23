package com.iteso.pmdproyectoplantas;

import android.app.DatePickerDialog;
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

        final FragmentEventos fragmentEventos = new FragmentEventos(); //lista de eventos



        addEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //abrir un datePicker
                calendar = Calendar.getInstance();
                day = calendar.get(Calendar.DAY_OF_MONTH);
                month = calendar.get(Calendar.MONTH);
                year = calendar.get(Calendar.YEAR);

                final Evento evento = new Evento();

                datePickerDialog = new DatePickerDialog(ActivityEvents.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int mYear, int mMonth, int dayOfMonth) {
                        //fecha es un long
                        evento.setEpochFecha(mYear*10_000 + mMonth*100 + dayOfMonth);
                    }
                }, day, month, year);

            }
        });

    }
}
