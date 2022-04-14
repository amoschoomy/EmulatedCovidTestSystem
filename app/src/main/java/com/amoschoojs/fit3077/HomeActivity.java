package com.amoschoojs.fit3077;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

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

        SearchTestingSitesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                  Intent switchActivityIntent = new Intent(getApplicationContext(), SearchTestingSite.class);
                  startActivity(switchActivityIntent);
            }
        });


        LoginAuthentication loginAuthentication = null;
        try {
            loginAuthentication = LoginAuthentication.getInstance();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        User user = loginAuthentication.getUser();

        // On-site testing button visibility and function
        if (!user.getHealthcareWorker()) {
            onSiteTestingBtn.setVisibility(View.GONE);
        } else {
            onSiteTestingBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                  Intent switchActivityIntent = new Intent(getApplicationContext(), OnSiteTestingActivity.class);
//                  startActivity(switchActivityIntent);
                }
            });
        }

    }
}