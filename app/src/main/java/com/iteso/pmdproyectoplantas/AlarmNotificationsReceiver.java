package com.iteso.pmdproyectoplantas;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class AlarmNotificationsReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "Llamado desde el receiver", Toast.LENGTH_SHORT).show();
    }
}
