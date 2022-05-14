package com.amoschoojs.fit3077;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

public class MyBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        String msg = intent.getExtras().getString("key");
//        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();

        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(
                        context, "BOOKING CONFIRM")
                        .setContentTitle("Booking Notification")
                        .setSmallIcon(R.drawable.ic_launcher_background)
                        .setContentText(msg)
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT);
        NotificationManager notificationManager =
                (NotificationManager)
                        context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(1, builder.build());

    }
}
