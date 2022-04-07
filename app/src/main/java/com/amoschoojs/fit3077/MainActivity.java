package com.amoschoojs.fit3077;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class MainActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    Log.d("myTag", "This is my message");

    OkHttpClient client = new OkHttpClient();

    // insert key here
    final String MY_API_KEY = getString(R.string.api_key);
    String usersUrl = "https://fit3077.com/api/v1/user";

    Log.d("myTag", "here2");
    Request request =
        new Request.Builder().url(usersUrl).header("Authorization", MY_API_KEY).build();

    Log.d("myTag", "here3");
    client
        .newCall(request)
        .enqueue(
            new Callback() {
              @Override
              public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Log.d("myTag", "fail1");
                e.printStackTrace();
              }

              @Override
              public void onResponse(@NonNull Call call, @NonNull Response response)
                  throws IOException {
                if (response.isSuccessful()) {
                  assert response.body() != null;
                  String strResponse = response.body().string();
                  String strResponseCode = Integer.toString(response.code());
                  Log.d("myTag", strResponse);
                  Log.d("myTag", strResponseCode);
                } else {
                  Log.d("myTag", "fail2");
                }
              }
            });
  }
}
