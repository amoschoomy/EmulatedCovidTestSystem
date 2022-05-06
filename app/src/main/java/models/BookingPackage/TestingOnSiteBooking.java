package models.BookingPackage;

import android.os.StrictMode;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class TestingOnSiteBooking implements Booking {

  public String create(
      String customerID, String testingSiteID, String startTime, String notes, String API_KEY)
      throws IOException, JSONException {
    String usersUrl = "https://fit3077.com/api/v1/booking";
    OkHttpClient client = new OkHttpClient();
    // Create json object

    FormBody.Builder builder =
        new FormBody.Builder()
            .add("customerId", customerID)
            .add("testingSiteId", testingSiteID)
            .add("startTime", startTime);
    if (notes != null) builder.add("notes", notes);
    RequestBody formBody = builder.build();

    Request request =
        new Request.Builder().url(usersUrl).header("Authorization", API_KEY).post(formBody).build();
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
