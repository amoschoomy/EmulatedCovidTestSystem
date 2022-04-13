package com.amoschoojs.fit3077;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    //    Intent switchActivityIntent = new Intent(this, LoginActivity.class);
    //    startActivity(switchActivityIntent);
    Intent switchActivityIntent = new Intent(this, SearchTestingSite.class);
    startActivity(switchActivityIntent);
  }
}
