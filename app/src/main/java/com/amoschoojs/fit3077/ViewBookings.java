package com.amoschoojs.fit3077;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONException;

import java.io.IOException;

import models.LoginSystemPackage.LoginAuthentication;
import models.UserPackage.User;
import viewmodel.UserViewModel;

public class ViewBookings extends AppCompatActivity {

  UserViewModel userViewModel;
  RecyclerView recyclerView;
  RecyclerViewAdapterBooking recyclerViewAdapter;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_view_bookings);
    userViewModel = new UserViewModel(getApplication());

    LoginAuthentication loginAuthentication = null;
    try {
      loginAuthentication = LoginAuthentication.getInstance();
    } catch (IOException e) {
      e.printStackTrace();
    } catch (JSONException e) {
      e.printStackTrace();
    }
    User user = loginAuthentication.getUser();
    recyclerView = findViewById(R.id.recyclerViewbooking);
    recyclerViewAdapter = new RecyclerViewAdapterBooking(getApplication());
    final String API_KEY = getString(R.string.api_key);
    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
    recyclerView.setLayoutManager(layoutManager);
    recyclerView.setAdapter(recyclerViewAdapter);

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
  }

  @Override
  protected void onResume() {
    try {
      final String API_KEY = getString(R.string.api_key);
      LoginAuthentication loginAuthentication = null;
      try {
        loginAuthentication = LoginAuthentication.getInstance();
      } catch (IOException e) {
        e.printStackTrace();
      } catch (JSONException e) {
        e.printStackTrace();
      }
      User user = loginAuthentication.getUser();
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

    super.onResume();
  }
}
