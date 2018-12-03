package com.iteso.pmdproyectoplantas;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.icu.text.SimpleDateFormat;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.iteso.pmdproyectoplantas.tools.Constants;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

public class ActivityAddEvent extends AppCompatActivity {

    private Button addDate, cancel, ok;
    private ImageButton takePicture, selectFromGallery, deletePicture;
    private EditText name;
    private EditText notes;
    private ImageView eventImage;
    private TextView date;
    private Spinner eventType;
    private Calendar calendar;
    private DatePickerDialog datePickerDialog;

    int day, month, year;

    private String mCurrentPhotoPath;
    private boolean imageNotWanted = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getString(R.string.activity_add_event_title));

        name = findViewById(R.id.add_event_name);
        notes = findViewById(R.id.add_event_notes);
        eventImage = findViewById(R.id.add_event_picture);
        takePicture = findViewById(R.id.add_event_add_picture);
        selectFromGallery = findViewById(R.id.add_event_gallery_picture);
        deletePicture = findViewById(R.id.add_event_delete_picture);
        date = findViewById(R.id.add_event_date);
        addDate = findViewById(R.id.add_event_add_date);
        cancel = findViewById(R.id.add_event_button_cancel);
        ok = findViewById(R.id.add_event_button_ok);

            //Spinner
        ArrayAdapter<CharSequence> eventAdapter = ArrayAdapter.createFromResource(this,
                R.array.event_types, android.R.layout.simple_spinner_item);
        eventAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        eventType.setAdapter(eventAdapter);


            //actividad de foto
        takePicture.setOnClickListener(view -> {
            dispatchTakePictureIntent();
        });
        selectFromGallery.setOnClickListener(view -> {
            makeGalleryIntent();
        });
        deletePicture.setOnClickListener(view -> {
            eventImage.setImageResource(android.R.color.darker_gray);
            mCurrentPhotoPath = "";
            imageNotWanted = true;
        });

        //obtener fecha y dejar como texto
        //traducir a Long para fecha en evento
        addDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar = Calendar.getInstance();

                day = calendar.get(Calendar.DAY_OF_MONTH);
                month = calendar.get(Calendar.MONTH);
                year = calendar.get(Calendar.YEAR);

                datePickerDialog = new DatePickerDialog(ActivityAddEvent.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int mYear, int mMonth, int mDay) {
                        //fecha es un long
                        date.setText(mYear + "," + mMonth + "," + mDay);
                    }
                }, day, month, year);
                datePickerDialog.show();
            }
        });

            //ok - devolver a ActivityEvents con los datos como extras del intento
        ok.setOnClickListener(view -> {
            agregarEvento();
        });
            //cancel - solo volver a actividad
        cancel.setOnClickListener((View v)->{
            onSupportNavigateUp();
        });

    }

    //obtener foto
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == Constants.takePictureIntentId) {
            if(resultCode == RESULT_OK) {
                eventImage.setImageURI(Uri.parse(mCurrentPhotoPath));
            } else if(resultCode == RESULT_CANCELED) {
                //Tal vez poner un Toast de confirmación para no poner imagen
            }
        } else if(requestCode == Constants.selectPictureIntentId) {
            if(resultCode == RESULT_OK) {
                Uri uri = data.getData();
                if(uri != null) {
                    mCurrentPhotoPath = uri.getPath();
                    eventImage.setImageURI(uri);
                }
            } else if(resultCode == RESULT_CANCELED) {
                //Tal vez poner un Toast de confirmación para no poner imagen
            }
        }
    }

    private void makeGalleryIntent() {
        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.
                EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, Constants.selectPictureIntentId);
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the file
                Toast.makeText(this, "Error al crear la imagen", Toast.LENGTH_LONG).show();
                return;
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.iteso.pmdproyectoplantas.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, Constants.takePictureIntentId);
            }
        }
    }

    @TargetApi(24)
    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }

    public boolean onSupportNavigateUp() {
        setResult(Activity.RESULT_CANCELED);
        finish();
        return true;
    }

    protected void agregarEvento() {
        if(!validFields()) {
            Toast.makeText(this, getString(R.string.error_field_required), Toast.LENGTH_SHORT).show();
            return;
        }

        //TODO: Construir el bean de evento y mandarlo en el put extra

        Intent intent = new Intent();
        intent.putExtra(Constants.extraBeanPlanta, "Test");
        setResult(Activity.RESULT_OK, intent);
        finish();
    }

    protected boolean validFields() {
        boolean val = true;

        if(name.getText().toString().trim().isEmpty()) {
            val = false;
            name.setError(getString(R.string.campo_en_blanco));
        }

        if(notes.getText().toString().trim().isEmpty()) {
            val = false;
            notes.setError(getString(R.string.campo_en_blanco));
        }

        if((mCurrentPhotoPath == null || mCurrentPhotoPath.isEmpty()) && !imageNotWanted) {
            DialogFragment dialogFragment = new DialogContinuarSinImagen();
            dialogFragment.show(getFragmentManager(), "dialogoImagenPlanta");
            imageNotWanted = !((DialogContinuarSinImagen)dialogFragment).continuarAsi;
            val &= imageNotWanted;
        }

        return val;
    }
}
