package com.iteso.pmdproyectoplantas;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Calendar;

public class ActivityAddEvent extends AppCompatActivity {

    private static final int CAMERA_REQUEST = 1999;

    Button addDAte, cancel, ok, takePicture;
    EditText id, title, group, notes;
    ImageView eventImage;
    TextView date;

    Calendar calendar;
    DatePickerDialog datePickerDialog;

    int day, month, year;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);

        id = findViewById(R.id.add_event_id);
        title = findViewById(R.id.add_event_title);
        group = findViewById(R.id.add_event_group);
        notes = findViewById(R.id.add_event_notes);

        eventImage = findViewById(R.id.add_event_picture);
        takePicture = findViewById(R.id.add_event_add_picture);

        date = findViewById(R.id.add_event_date);
        addDAte = findViewById(R.id.add_event_add_date);

        cancel = findViewById(R.id.add_button_cancel);
        ok = findViewById(R.id.add_button_ok);



        //actividad de foto
        takePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cameraIntent = new
                        Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                if (cameraIntent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(cameraIntent, CAMERA_REQUEST);
                }
            }
        });


        //obtener fecha y dejar como texto
        //traducir a Long para fecha en evento
        addDAte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar = Calendar.getInstance();

                day = calendar.get(Calendar.DAY_OF_MONTH);
                month = calendar.get(Calendar.MONTH);
                year = calendar.get(Calendar.YEAR);

                datePickerDialog = new DatePickerDialog(ActivityAddEvent.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int mYear, int mMonth, int dayOfMonth) {
                        //fecha es un long
                        date.setText(mYear*10000 + mMonth*100 + dayOfMonth);
                    }
                }, day, month, year);
            }
        });

        //ok - devolver a ActivityEvents con los datos como extras del intento

        //cancel - solo volver a actividad
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    //obtener foto
    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent data) {
        switch(requestCode){
            case CAMERA_REQUEST:
                if(resultCode == RESULT_OK){
                    Bitmap photo = (Bitmap) data.getExtras().get("data");
                    eventImage.setImageBitmap(photo);
                }
                break;
        }
    }

}
