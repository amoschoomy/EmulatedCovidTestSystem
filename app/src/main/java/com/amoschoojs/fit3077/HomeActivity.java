package com.amoschoojs.fit3077;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;

import java.io.IOException;

import LoginSystemPackage.LoginAuthentication;
import UserPackage.User;

public class HomeActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_home);

    Button onSiteTestingBtn = this.findViewById(R.id.onSiteTestingBtn);
    Button SearchTestingSitesBtn = this.findViewById(R.id.searchTestingSitesBtn);
    Button checkBookingButton = this.findViewById(R.id.checkbooking);

    LoginAuthentication loginAuthentication = null;
    try {
      loginAuthentication = LoginAuthentication.getInstance();
    } catch (IOException e) {
      e.printStackTrace();
    } catch (JSONException e) {
      e.printStackTrace();
    }
    User user = loginAuthentication.getUser();

    SearchTestingSitesBtn.setOnClickListener(
        new View.OnClickListener() {
          @Override
          public void onClick(View view) {
            Intent switchActivityIntent =
                new Intent(getApplicationContext(), SearchTestingSite.class);
            startActivity(switchActivityIntent);
          }
        });

    // Healthcare workers cannot check booking status
    if (user.getHealthcareWorker()) checkBookingButton.setVisibility(View.GONE);
    checkBookingButton.setOnClickListener(
        new View.OnClickListener() {
          @Override
          public void onClick(View view) {
            Intent switchActivityIntent = new Intent(getApplicationContext(), CheckBooking.class);
            startActivity(switchActivityIntent);
          }
        });

    // On-site testing button visibility and function
    if (!user.getHealthcareWorker()) {
      onSiteTestingBtn.setVisibility(View.GONE);
    } else {
      onSiteTestingBtn.setOnClickListener(
          new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              Intent switchActivityIntent =
                  new Intent(getApplicationContext(), OnSiteTestingActivity.class);
              startActivity(switchActivityIntent);
            }
          });
    }

  }

    @Override
    public void onBackPressed() {
        // disable back button
    }

}
