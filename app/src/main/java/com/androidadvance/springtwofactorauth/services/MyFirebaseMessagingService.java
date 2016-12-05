package com.androidadvance.springtwofactorauth.services;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import com.androidadvance.springtwofactorauth.R;
import com.androidadvance.springtwofactorauth.activities.MainActivity;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.socks.library.KLog;
import java.io.Serializable;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

  private static final String TAG = "MyFirebaseMsgService";

  @Override public void onMessageReceived(RemoteMessage remoteMessage) {
    KLog.d(TAG, "From: " + remoteMessage.getFrom());
    if (remoteMessage.getData().size() > 0) {
      KLog.d(TAG, "Message data payload: " + remoteMessage.getData());
    }
    if (remoteMessage.getNotification() != null) {
      KLog.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
    }

    //----------- TWO FACTOR AUTH LOGIC ------------

    String browser = remoteMessage.getData().get("browser");
    String ip = remoteMessage.getData().get("ip");
    String location = remoteMessage.getData().get("location");

    Intent main_activity = new Intent(getApplicationContext(), MainActivity.class);
    main_activity.putExtra("who", (Serializable) remoteMessage.getData());
    startActivity(main_activity);
  }

  //create a notification that will show on statusbar if needed.
  private void sendNotification(String messageBody) {
    Intent intent = new Intent(this, MainActivity.class);
    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
    PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent, PendingIntent.FLAG_ONE_SHOT);

    Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
    NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this).setSmallIcon(R.drawable.ic_launcher)
        .setContentTitle("FCM Message")
        .setContentText(messageBody)
        .setAutoCancel(true)
        .setSound(defaultSoundUri)
        .setContentIntent(pendingIntent);

    NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

    notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
  }
}