package com.amoschoojs.fit3077;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import models.BookingPackage.BookingFacade;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class OnSiteTestingActivity extends AppCompatActivity {

  Boolean feverStatus,
      coughStatus,
      throatStatus,
      fatigueStatus,
      diarrheaStatus,
      nauseaStatus,
      jointStatus;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_on_site_testing);

    RadioGroup feverRadioGroup = findViewById(R.id.FeverRadioGroup);
    RadioGroup coughRadioGroup = findViewById(R.id.CoughRadioGroup);
    RadioGroup throatRadioGroup = findViewById(R.id.ThroatRadioGroup);
    RadioGroup fatigueRadioGroup = findViewById(R.id.FatigueRadioGroup);
    RadioGroup diarrheaRadioGroup = findViewById(R.id.DiarrheaRadioGroup);
    RadioGroup nauseaRadioGroup = findViewById(R.id.NauseaRadioGroup);
    RadioGroup jointRadioGroup = findViewById(R.id.JointRadioGroup);
    EditText bookingPin = findViewById(R.id.pinInput);
    Button recButton = findViewById(R.id.RecButton);
    recButton.setOnClickListener(
        new View.OnClickListener() {
          @Override
          public void onClick(View view) {
            try {
              feverStatus = checkStatus(feverRadioGroup);
              coughStatus = checkStatus(coughRadioGroup);
              throatStatus = checkStatus(throatRadioGroup);
              fatigueStatus = checkStatus(fatigueRadioGroup);
              diarrheaStatus = checkStatus(diarrheaRadioGroup);
              nauseaStatus = checkStatus(nauseaRadioGroup);
              jointStatus = checkStatus(jointRadioGroup);
              String pin = bookingPin.getText().toString();
              String[] array = BookingFacade.checkBooking(pin, getString(R.string.api_key));
              if (pin.length() < 6) {
                throw new Exception("Invalid Pin");
              }
              TextView testRecView = findViewById(R.id.RecommendedTestTxt);
              String recommendedTest = "PCR Test";
              if (feverStatus
                  || coughStatus
                  || throatStatus
                  || fatigueStatus
                  || diarrheaStatus
                  || nauseaStatus
                  || jointStatus) {
                // recommend PCR Test
                testRecView.setText(recommendedTest);

              } else {
                // recommend RAT Test
                recommendedTest = "RAT Test";
                testRecView.setText(recommendedTest);
              }
              testRecView.setVisibility(View.VISIBLE);

              String bookingID = array[0];
              String jsonstr =
                  String.format(
                      "{\"additionalInfo\":{\"recommendedTest\":\"%s\"}, \"status\": \"PROCESSED\"}",
                      recommendedTest);

              RequestBody body =
                  RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonstr);
              BookingFacade.updateBookingDetails(getString(R.string.api_key), bookingID, body);
            } catch (NullPointerException e) {
              Toast.makeText(getApplicationContext(), "Please Input All Fields", Toast.LENGTH_SHORT)
                  .show();
            } catch (Exception e) {
              Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
            }
          }
        });
  }

  /**
   * Checks If Yes or No is selected from the Radio Button Group. If yes is selected, return true,
   * otherwise return false.
   *
   * @throws NullPointerException Nothing is selected in the radio group.
   */
  private boolean checkStatus(RadioGroup v) throws NullPointerException {
    int radioId = v.getCheckedRadioButtonId();

    RadioButton buttonSelected = findViewById(radioId);
    String choice = buttonSelected.getText().toString();
    return choice.equalsIgnoreCase("Yes");
  }
}
