package com.luis.strategy.firebase;

import android.util.Log;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

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


}
