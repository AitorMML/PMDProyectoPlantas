package com.iteso.pmdproyectoplantas;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.DialogFragment;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.icu.text.SimpleDateFormat;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.iteso.pmdproyectoplantas.beans.Planta;
import com.iteso.pmdproyectoplantas.tools.Constants;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

public class ActivityAddPlant extends AppCompatActivity {
    private ImageView imagen;
    private ImageButton tomarFoto;
    private ImageButton tomarDeGaleria;
    private ImageButton quitar;
    private EditText nombre;
    private EditText especie;
    private EditText cuidados;
    private Button guardar;
    private Button cancelar;

    private String mCurrentPhotoPath = "";
    private boolean imageNotWanted = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_plant);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getString(R.string.activity_add_plant_titulo));

        imagen = findViewById(R.id.activity_add_plant_imagen);
        imagen.setScaleType(ImageView.ScaleType.CENTER_CROP);
        tomarFoto = findViewById(R.id.activity_add_plant_agregarfoto);
        tomarDeGaleria = findViewById(R.id.activity_add_plant_agregarGaleria);
        quitar = findViewById(R.id.activity_add_plant_quitarImagen);
        nombre = findViewById(R.id.activity_add_plant_nombre);
        especie = findViewById(R.id.activity_add_plant_especie);
        cuidados = findViewById(R.id.activity_add_plant_cuidados);
        guardar = findViewById(R.id.activity_add_plant_guardar);
        cancelar = findViewById(R.id.activity_add_plant_cancelar);

        cancelar.setOnClickListener((View v)->{
            onSupportNavigateUp();
        });
        guardar.setOnClickListener(view -> {
            agregarPlanta();
        });
        tomarFoto.setOnClickListener(view -> {
            /*Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if(cameraIntent.resolveActivity(getPackageManager()) != null) {
                startActivityForResult(cameraIntent, Constants.takePictureIntentId);
            }*/
            dispatchTakePictureIntent();
        });
        tomarDeGaleria.setOnClickListener(view -> {
            makeGalleryIntent();
        });
        quitar.setOnClickListener(view -> {
            imagen.setImageResource(android.R.color.darker_gray);
            if(!mCurrentPhotoPath.isEmpty()) {
                File archivo = new File(mCurrentPhotoPath);
                archivo.delete();
                mCurrentPhotoPath = "";
            }
            //imageNotWanted = true;
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        setResult(Activity.RESULT_CANCELED);
        finish();
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == Constants.takePictureIntentId) {
            if(resultCode == RESULT_OK) {
                imagen.setImageURI(Uri.parse(mCurrentPhotoPath));
                //galleryAddPic();
            } else if(resultCode == RESULT_CANCELED) {
                //Tal vez poner un Toast de confirmación para no poner imagen
            }
        } else if(requestCode == Constants.selectPictureIntentId) {
            if(resultCode == RESULT_OK) {
                Uri uri = data.getData();
                String filepath = null;
                if(uri != null) {
                    String []projection = { MediaStore.Images.Media.DATA };
                    Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
                    if(cursor != null) {
                        cursor.moveToFirst();
                        int columnIndex = cursor.getColumnIndex(projection[0]);
                        filepath = cursor.getString(columnIndex);
                        cursor.close();
                    }
                    try(BufferedInputStream bis
                                = new BufferedInputStream(new FileInputStream(filepath))
                        ; BufferedOutputStream bos
                                = new BufferedOutputStream(new FileOutputStream(createImageFile()
                            .getAbsolutePath()))) {

                        byte[] buf = new byte[1024];
                        bis.read(buf);
                        do {
                            bos.write(buf);
                        } while(bis.read(buf) != -1);
                        bos.close();
                        imagen.setImageURI(Uri.parse(mCurrentPhotoPath));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } else if(resultCode == RESULT_CANCELED) {
                //Tal vez poner un Toast de confirmación para no poner imagen
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
                Toast.makeText(this
                        , getString(R.string.activity_add_plant_error_crear_imagen)
                        , Toast.LENGTH_LONG).show();
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

    private void makeGalleryIntent() {
        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.
                EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, Constants.selectPictureIntentId);
    }

    protected void agregarPlanta() {
        if(!validFields()) {
            Toast.makeText(this, getString(R.string.error_field_required), Toast.LENGTH_SHORT).show();
            return;
        }

        Planta planta = new Planta();
        planta.setNombre(nombre.getText().toString());
        planta.setCuidados(cuidados.getText().toString());
        planta.setEspecie(especie.getText().toString());
        planta.setImagenUriString(mCurrentPhotoPath);

        Intent intent = new Intent();
        intent.putExtra(Constants.extraBeanPlanta, planta);
        setResult(Activity.RESULT_OK, intent);
        finish();
    }

    protected boolean validFields() {
        boolean val = true;

        if(nombre.getText().toString().trim().isEmpty()) {
            val = false;
            nombre.setError(getString(R.string.campo_en_blanco));
        }

        if(especie.getText().toString().trim().isEmpty()) {
            val = false;
            especie.setError(getString(R.string.campo_en_blanco));
        }

        if((mCurrentPhotoPath == null || mCurrentPhotoPath.isEmpty()) /*&& !imageNotWanted*/) {
            DialogContinuarSinImagen dialogFragment = new DialogContinuarSinImagen();
            dialogFragment.show(getFragmentManager(), "dialogoImagenPlanta");
        }

        return val;
    }

    private void galleryAddPic() {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(mCurrentPhotoPath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        this.sendBroadcast(mediaScanIntent);
    }
}
