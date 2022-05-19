package com.amoschoojs.fit3077;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import java.util.Observable;
import java.util.Observer;

public class MainActivity extends AppCompatActivity implements Observer {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    createNotificationChannel();

    Intent switchActivityIntent = new Intent(this, LoginActivity.class);
    startActivity(switchActivityIntent);
  }

  private void createNotificationChannel() {
    // Create the NotificationChannel, but only on API 26+ because
    // the NotificationChannel class is new and not in the support library
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
      CharSequence name = "name";
      String description = "desc";
      int importance = NotificationManager.IMPORTANCE_DEFAULT;
      NotificationChannel channel = new NotificationChannel("BOOKING CONFIRM", name, importance);
      channel.setDescription(description);
      // Register the channel with the system; you can't change the importance
      // or other notification behaviors after this
      NotificationManager notificationManager = getSystemService(NotificationManager.class);
      notificationManager.createNotificationChannel(channel);
    }
  }

  // Observe if got notification, if got, then push notification
  @Override
  public void update(Observable observable, Object o) {
    String notification = (String) o;
    NotificationCompat.Builder builder =
            new NotificationCompat.Builder(
                    this, "BOOKING CONFIRM")
                    .setContentTitle("Booking Update")
                    .setSmallIcon(R.drawable.ic_launcher_background)
                    .setContentText(notification)
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT);
    Toast.makeText(this, notification, Toast.LENGTH_LONG)
            .show();
    NotificationManager notificationManager =
            (NotificationManager)
                    this.getSystemService(Context.NOTIFICATION_SERVICE);
    notificationManager.notify(1, builder.build());
  }
}
