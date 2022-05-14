package com.amoschoojs.fit3077;

import android.app.NotificationManager;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import models.BookingPackage.Booking;
import models.LoginSystemPackage.LoginAuthentication;
import models.UserPackage.User;
import viewmodel.BookingViewModel;
import viewmodel.UserViewModel;

public class AdminBookingInterfaceActivity extends AppCompatActivity {

    RecyclerViewAdapterRecep recyclerViewAdapter;
    BookingViewModel bookingViewModel;
    UserViewModel userViewModel;
    LiveData<String> myNotification;
//    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_booking_interface);

        final String API_KEY = getString(R.string.api_key);

        bookingViewModel = new BookingViewModel(getApplication());

        // get user + user model
        userViewModel = new UserViewModel(getApplication());
//        LoginAuthentication loginAuthentication = null;
//        try {
//            loginAuthentication = LoginAuthentication.getInstance();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        User user = loginAuthentication.getUser();

        // set recyclerview adapter
        RecyclerView recyclerView = findViewById(R.id.recyclerViewAdmin);
        recyclerViewAdapter =
                new RecyclerViewAdapterRecep(getApplication(), bookingViewModel, userViewModel);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(recyclerViewAdapter);

        // populate recyclerview
        try {
            bookingViewModel
                    .getBookings(API_KEY)
                    .observe(
                            this,
                            newData -> {
                                recyclerViewAdapter.setBookings(newData);
                                recyclerViewAdapter.notifyDataSetChanged();
                            });
        } catch (JSONException jsonException) {
            jsonException.printStackTrace();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }

        userViewModel
                .getNotification()
                .observe(
                        this,
                        newData -> {
                            sendNotification();
                        });
    }

    public void sendNotification() {
        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(
                        this, "BOOKING CONFIRM")
                        .setContentTitle("Booking Notification")
                        .setSmallIcon(R.drawable.ic_launcher_background)
                        .setContentText("test notification here")
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT);
        NotificationManager notificationManager =
                (NotificationManager)
                        this.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(1, builder.build());

    }


    // Need this to update when Booking is Processed
    @Override
    protected void onResume() {

        try {
            final String API_KEY = getString(R.string.api_key);
            bookingViewModel
                    .getBookings(API_KEY)
                    .observe(
                            this,
                            newData -> {
                                recyclerViewAdapter.setBookings(newData);
                                recyclerViewAdapter.notifyDataSetChanged();
                            });
        } catch (Exception e) {
            e.printStackTrace();
        }

        RecyclerView recyclerView = findViewById(R.id.recyclerViewAdmin);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerViewAdapter.notifyDataSetChanged();
        recyclerView.setAdapter(recyclerViewAdapter);

        super.onResume();

    }

}