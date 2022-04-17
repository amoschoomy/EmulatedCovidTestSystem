package BookingPackage;

import static android.graphics.Color.BLACK;
import static android.graphics.Color.WHITE;

import android.graphics.Bitmap;
import android.os.StrictMode;
import android.util.Log;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class HomeBooking implements Booking{

    private static HomeBooking instance = null;
    private Bitmap qrCode = null;

    private HomeBooking(){

    }

    /**
     * To get the HomeBooking Instance
     */
    public static HomeBooking getInstance(){
        if(instance == null) {
            instance = new HomeBooking();
        }
        return instance;
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

    public void setQRCode(String infoText) throws WriterException {
        Log.d("myTag", "here");
        this.qrCode = generateQRCode(infoText);
    }

    public Bitmap getQRCode() {
        return this.qrCode;
    }

    @Override
    public String create(String customerID, String testingSiteID, String startTime,
                         String notes, String API_KEY) throws IOException, JSONException {

        String bookingUrl = "https://fit3077.com/api/v1/booking";
        OkHttpClient client = new OkHttpClient();

//        FormBody.Builder builder =
//                new FormBody.Builder()
//                        .add("customerId", customerID)
//                        .add("testingSiteId", testingSiteID)
//                        .add("startTime", startTime);
//        if (notes != null) builder.add("notes", notes);
//        RequestBody formBody = builder.build();

        // creating post body

        String testingUrl = "http://www.example.com/";
        String custAdditionalInfoStr = String.format(
                        "{" +
                            "\"url\":\"%s\"" +
                        "}",
        testingUrl);
        JSONObject custInfoJson = new JSONObject(custAdditionalInfoStr);

        String jsonstr = String.format("{\"customerId\":\"%s\"," +
                        "\"testingSiteId\":\"%s\"," +
                        "\"startTime\":\"%s\"," +
                        "\"additionalInfo\": %s}",
                customerID, testingSiteID, startTime, custInfoJson);

        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonstr);

        Request request =
                new Request.Builder()
                        .url(bookingUrl)
                        .header("Authorization", API_KEY)
                        .post(body)
                        .build();
        // Have the response run in background or system will crash
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        Response response = client.newCall(request).execute();

        assert response.body() != null;
        String output = response.body().string();
        JSONObject jObj = new JSONObject(output);
        String smsPin = jObj.getString("smsPin");

        return smsPin;
    }


}
