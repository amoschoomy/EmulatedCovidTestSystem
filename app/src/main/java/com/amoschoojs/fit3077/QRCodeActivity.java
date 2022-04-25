package com.amoschoojs.fit3077;

import static android.graphics.Color.BLACK;
import static android.graphics.Color.WHITE;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

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

    String smsPin = getIntent().getStringExtra("smsPin");

    Bitmap qrCode;
    try {
      qrCode = generateQRCode(smsPin);
      QRCodeView.setImageBitmap(qrCode);
      placeholderQRText.setVisibility(View.INVISIBLE);
      urlToTestingText.setText(R.string.testing_link);
      urlToTestingText.setMovementMethod(LinkMovementMethod.getInstance());

    } catch (WriterException e) {
      e.printStackTrace();
    }

  }

  /**
   * Generates a QRCode (Bitmap) that returns the infoText when scanned.
   */
  private Bitmap generateQRCode(String infoText) throws WriterException {
    QRCodeWriter qrCodeWriter = new QRCodeWriter();

    int length = 250;

    BitMatrix bitMatrix = qrCodeWriter.encode(infoText, BarcodeFormat.QR_CODE, length, length);
    int[] pixels = new int[length * length];

    int xlength, ylength;
    xlength = ylength = length;

    for (int y = 0; y < ylength; y++) {
      int offset = y * xlength;
      for (int x = 0; x < xlength; x++) {
        pixels[offset + x] = bitMatrix.get(x, y) ? BLACK : WHITE;
      }
    }

    Bitmap myBitmap = Bitmap.createBitmap(length, length, Bitmap.Config.ARGB_8888);
    myBitmap.setPixels(pixels, 0, length, 0, 0, length, length);

    return myBitmap;

  }

}
