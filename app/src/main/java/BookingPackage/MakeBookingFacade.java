package BookingPackage;

import android.os.StrictMode;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import UserPackage.User;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MakeBookingFacade {

  public static String makeBooking(
      User user,
      String testingFacilityID,
      String notes,
      boolean homeTesting,
      String startTime,
      String API_KEY)
      throws JSONException, IOException {
    Booking booking = null;
    String smsPin = null;
    String userID = user.getUserId();
    if (user.getReceptionist()) {
      booking = new TestingOnSiteBooking();
      smsPin = booking.create(userID, testingFacilityID, startTime, notes, API_KEY);
    } else if (user.getCustomer() && homeTesting) {

    } else if (user.getCustomer()) {
      booking = new TestingOnSiteBooking();
      smsPin = booking.create(userID, testingFacilityID, startTime, notes, API_KEY);
    }
    return smsPin;
  }

  public static String checkBooking(String smsPin, String API_KEY)
      throws IOException, JSONException {
    String usersUrl = "https://fit3077.com/api/v1/booking";
    OkHttpClient client = new OkHttpClient();
    // Create json object

    Request request =
        new Request.Builder().url(usersUrl).header("Authorization", API_KEY).get().build();
    // Have the response run in background or system will crash
    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
    StrictMode.setThreadPolicy(policy);

    Response response = client.newCall(request).execute();

    assert response.body() != null;
    String output = response.body().string();
    JSONArray c = new JSONArray(output);
    for (int i = 0; i < c.length(); i++) {
      JSONObject obj = c.getJSONObject(i);
      String A = obj.getString("smsPin");
      //      System.out.println(smsPin);
      //      System.out.println(A);
      if (A.equals(smsPin)) {
        String B = obj.getString("status");
        return B;
      }
    }
    return "No Booking Found";
  }
}
