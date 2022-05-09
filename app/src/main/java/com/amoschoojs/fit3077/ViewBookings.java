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

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_view_bookings);
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
    RecyclerView recyclerView = findViewById(R.id.recyclerViewbooking);
    RecyclerViewAdapterBooking recyclerViewAdapter =
        new RecyclerViewAdapterBooking(getApplication());
    final String API_KEY = getString(R.string.api_key);
    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
    recyclerView.setLayoutManager(layoutManager);
    recyclerView.setAdapter(recyclerViewAdapter);

    try {
      userViewModel
          .getAllBookings(user.getUserId(), API_KEY)
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
}
