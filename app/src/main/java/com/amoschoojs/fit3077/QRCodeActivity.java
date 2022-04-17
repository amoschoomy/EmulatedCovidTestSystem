package com.amoschoojs.fit3077;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import BookingPackage.HomeBooking;

public class QRCodeActivity extends AppCompatActivity {

  ImageView QRCodeView;
  TextView placeholderQRText, urlToTestingText;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_qrcode);

    QRCodeView = findViewById(R.id.QRCodeView);
    placeholderQRText = findViewById(R.id.placeholderQRtxt);
    urlToTestingText = findViewById(R.id.urlToTesting);

    HomeBooking booking = HomeBooking.getInstance();
    Bitmap qrCode = booking.getQRCode();

    if (qrCode != null) {
      QRCodeView.setImageBitmap(qrCode);
      placeholderQRText.setVisibility(View.INVISIBLE);
      urlToTestingText.setText(R.string.testing_link);
      urlToTestingText.setMovementMethod(LinkMovementMethod.getInstance());
    }
  }
}
