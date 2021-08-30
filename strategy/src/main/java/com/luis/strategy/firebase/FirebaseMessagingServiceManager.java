package com.luis.strategy.firebase;

import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.luis.lgameengine.gameutils.Settings;
import com.luis.lgameengine.implementation.fileio.FileIO;
import com.luis.strategy.connection.OnlineInputOutput;
import com.luis.strategy.constants.Define;

public class FirebaseMessagingServiceManager extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        if (remoteMessage.getNotification() != null) {

            String titulo = remoteMessage.getNotification().getTitle();
            String texto = remoteMessage.getNotification().getBody();

            Log.d("Debug", "NOTIFICACION RECIBIDA");
            Log.d("Debug", "Título: " + titulo);
            Log.d("Debug", "Texto: " + texto);

            //Opcional: mostramos la notificación en la barra de estado
            //showNotification(titulo, texto);
        }
    }

    @Override
    public void onNewToken(String firebaseIdDeviceToken) {
        try {
            String data = FileIO.getInstance().loadData(Define.DATA_USER,
                    Settings.getInstance().getActiviy().getApplicationContext());

            if (data != null && data.length() > 0) {
                String[] d = data.split("\n");
                final String userName = d[0];

                OnlineInputOutput.getInstance().sendFirebaseIdDeviceToken(this, userName, firebaseIdDeviceToken);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
