package com.iteso.pmdproyectoplantas.tools;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.widget.ImageView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.iteso.pmdproyectoplantas.adapters.AdapterPlanta;
import com.iteso.pmdproyectoplantas.beans.Planta;

public class ImageHelper {
    public static void loadImage(ImageView imagen, Planta current) {
        if(!current.getImagenUriString().startsWith("/storage/")) {
            StorageReference mStorageReference = FirebaseStorage.getInstance().getReference("users")
                    .child(FirebaseAuth.getInstance().getUid()).child("plants")
                    .child(current.getPlantaId()).child(current.getImagenUriString());
            mStorageReference.getDownloadUrl().addOnSuccessListener((Uri _uri)->{
                SimpleDraweeView draweeView = (SimpleDraweeView) imagen;
                draweeView.setImageURI(_uri);
            });
        } else {
            Bitmap bitmap = BitmapFactory.decodeFile(current.getImagenUriString());
            if (bitmap != null)  { imagen.setImageBitmap(bitmap); }
            else { imagen.setImageResource(android.R.color.darker_gray);}
        }
    }
}
