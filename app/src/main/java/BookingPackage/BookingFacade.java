package BookingPackage;

import android.os.StrictMode;

import com.google.zxing.WriterException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import UserPackage.User;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/** Booking Facade class */
public class BookingFacade {

  /**
   * method to make booking using a facade
   *
   * @param user
   * @param testingFacilityID
   * @param notes
   * @param homeTesting
   * @param startTime
   * @param API_KEY
   * @return
   * @throws JSONException
   * @throws IOException
   * @throws WriterException
   */
  public static String makeBooking(
      User user,
      String testingFacilityID,
      String notes,
      boolean homeTesting,
      String startTime,
      String API_KEY)
      throws JSONException, IOException, WriterException {
    Booking booking;
    String smsPin = null;
    String userID = user.getUserId();

    // receptionist
    if (user.getReceptionist()) {
      booking = new TestingOnSiteBooking();
      smsPin = booking.create(userID, testingFacilityID, startTime, notes, API_KEY);
    }
    // customer that choose home testing
    else if (user.getCustomer() && homeTesting) {
      booking = new HomeBooking();
      smsPin = booking.create(userID, testingFacilityID, startTime, notes, API_KEY);
    }
    // customer online booking
    else if (user.getCustomer()) {
      booking = new TestingOnSiteBooking();
      smsPin = booking.create(userID, testingFacilityID, startTime, notes, API_KEY);
    }
    return smsPin;
  }

  /**
   * check a booking using a facade
   *
   * @param smsPin
   * @param API_KEY
   * @return
   * @throws Exception
   */
  public static String[] checkBooking(String smsPin, String API_KEY) throws Exception {
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
      if (A.equals(smsPin)) {
        String B = obj.getString("status");
        String bookingID = obj.getString("id");
        return new String[] {bookingID, B};
      }
    }
    throw new Exception("No Booking Found");
  }

  /**
   * method to update booking details
   *
   * @param API_KEY
   * @param bookingID
   * @param requestBody
   * @throws IOException
   */
  public static void updateBookingDetails(String API_KEY, String bookingID, RequestBody requestBody)
      throws IOException {
    OkHttpClient client = new OkHttpClient();

    // insert key here
    String usersUrl = String.format("https://fit3077.com/api/v1/booking/%s", bookingID);
    System.out.println(usersUrl);
    Request request =
        new Request.Builder()
            .url(usersUrl)
            .header("Authorization", API_KEY)
            .patch(requestBody)
            .build();

    // Have the response run in background or system will crash
    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
    StrictMode.setThreadPolicy(policy);

    Response response = client.newCall(request).execute();
  }
}
