package com.amoschoojs.fit3077;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONException;

import java.io.IOException;

import models.LoginSystemPackage.LoginAuthentication;
import models.UserPackage.User;
import viewmodel.BookingViewModel;
import viewmodel.UserViewModel;

public class AdminBookingInterfaceActivity extends AppCompatActivity {

    RecyclerViewAdapterRecep recyclerViewAdapter =
            new RecyclerViewAdapterRecep(getApplication());;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_booking_interface);

        final String API_KEY = getString(R.string.api_key);

        // get user + user model
        UserViewModel userViewModel = new UserViewModel(getApplication());
        LoginAuthentication loginAuthentication = null;
        try {
            loginAuthentication = LoginAuthentication.getInstance();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        User user = loginAuthentication.getUser();

        // set recyclerview adapter
        RecyclerView recyclerView = findViewById(R.id.recyclerViewAdmin);
//        RecyclerViewAdapterRecep recyclerViewAdapter =
//                new RecyclerViewAdapterRecep(getApplication());
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(recyclerViewAdapter);

        // populate recyclerview
        try {
      userViewModel
          .retrieveBookings(user.getUserId(), API_KEY)
          .observe(
              this,
              newData -> {
                recyclerViewAdapter.setBookings(newData);
                recyclerViewAdapter.notifyDataSetChanged();
              });
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        BookingViewModel bookingViewModel = new BookingViewModel(getApplication());
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

    }

    @Override
    protected void onResume() {
        super.onResume();
        RecyclerView recyclerView = findViewById(R.id.recyclerViewAdmin);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerViewAdapter.notifyDataSetChanged();
        recyclerView.setAdapter(recyclerViewAdapter);

    }
}