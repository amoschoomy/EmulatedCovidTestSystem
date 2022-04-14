package com.amoschoojs.fit3077;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;

import java.io.IOException;

import BookingPackage.MakeBookingFacade;

public class CheckBooking extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_check_booking);

    EditText pin = findViewById(R.id.bookingPinInput);
    Button check = findViewById(R.id.check);
    check.setOnClickListener(
        new View.OnClickListener() {
          @Override
          public void onClick(View view) {
            try {
              String pinText = pin.getText().toString();
              String status = MakeBookingFacade.checkBooking(pinText, getString(R.string.api_key));
              TextView statusView = findViewById(R.id.bookingstatus);
              statusView.setText(status);
            } catch (IOException e) {
              e.printStackTrace();
            } catch (JSONException e) {
              e.printStackTrace();
            }
          }
        });
  }
}
