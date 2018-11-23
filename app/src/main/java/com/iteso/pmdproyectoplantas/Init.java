package com.iteso.pmdproyectoplantas;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;

public class Init extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(this);
    }
}
