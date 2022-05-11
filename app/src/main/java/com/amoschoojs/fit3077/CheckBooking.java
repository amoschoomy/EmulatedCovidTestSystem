package com.amoschoojs.fit3077;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;

import java.io.IOException;

import viewmodel.BookingViewModel;

/** CheckBooking UI class */
public class CheckBooking extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_check_booking);
    BookingViewModel bookingViewModel = new BookingViewModel(getApplication());
    EditText pin = findViewById(R.id.bookingPinInput);
    Button check = findViewById(R.id.check);
    Button actionButton = findViewById(R.id.actionbutton);
    check.setOnClickListener(
        new View.OnClickListener() {
          @Override
          public void onClick(View view) {
            TextView statusView = findViewById(R.id.bookingstatus);
            TextView tsView = findViewById(R.id.testingsitevieww);
            TextView startTimeView = findViewById(R.id.starttimeview);
            TextView updatedTimeView = findViewById(R.id.updatedtimeview);
            boolean isPin = true;
            try {
              String pinText = pin.getText().toString();
              if (pinText.length() > 6) {
                isPin = false;
              }
              String[] array =
                  bookingViewModel.checkBooking(pinText, getString(R.string.api_key), isPin);
              statusView.setText(array[1]);
              tsView.setText(array[2]);
              startTimeView.setText(array[3]);
              updatedTimeView.setText(array[4]);
              actionButton.setVisibility(View.VISIBLE);
            } catch (IOException e) {
              statusView.setText("No Booking Found");
              e.printStackTrace();
            } catch (JSONException e) {
              statusView.setText("No Booking Found");
              e.printStackTrace();
            } catch (Exception e) {
              statusView.setText("No Booking Found");
            }
          }
        });
    Button viewAll = findViewById(R.id.viewallbooking);
    viewAll.setOnClickListener(
        new View.OnClickListener() {
          @Override
          public void onClick(View view) {

            Intent switchActivityIntent = new Intent(getApplicationContext(), ViewBookings.class);
            startActivity(switchActivityIntent);
          }
        });

    actionButton.setOnClickListener(
        new View.OnClickListener() {
          @Override
          public void onClick(View view) {}
        });
  }
}
