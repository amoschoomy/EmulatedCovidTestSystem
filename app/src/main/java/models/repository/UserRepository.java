package models.repository;

import android.app.Application;
import android.os.StrictMode;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import models.data.BookingPackage.Booking;
import models.data.BookingPackage.TestingOnSiteBooking;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class UserRepository {

  public UserRepository(Application application) {}

  MutableLiveData<ArrayList<Booking>> bookings = new MutableLiveData<ArrayList<Booking>>();
  MutableLiveData<String> notification = new MutableLiveData<String>();

  public LiveData<ArrayList<Booking>> getAllBookings(String userID, String API_KEY)
      throws IOException, JSONException {
    ArrayList<Booking> dummy = new ArrayList<>();
    String usersUrl = String.format("https://fit3077.com/api/v2/user/%s?fields=bookings", userID);

    Request request =
        new Request.Builder().url(usersUrl).header("Authorization", API_KEY).get().build();
    // Have the response run in background or system will crash
    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
    StrictMode.setThreadPolicy(policy);
    OkHttpClient client = new OkHttpClient();
    Response response = client.newCall(request).execute();

    assert response.body() != null;
    String output = response.body().string();
    JSONObject jObj = new JSONObject(output);
    JSONArray bookingsArray = jObj.getJSONArray("bookings");
    for (int i = 0; i < bookingsArray.length(); i++) {
      JSONObject obj = bookingsArray.getJSONObject(i);

      if (!(obj.getJSONObject("additionalInfo").has("url"))) { // only show onsite booking
        String bookingID = obj.getString("id");
        JSONObject object = obj.getJSONObject("testingSite");
        String testingSiteID = object.getString("id");
        String testingSiteName = object.getString("name");
        String updatedTime = obj.getString("updatedAt");
        String startTime = obj.getString("startTime");
        TestingOnSiteBooking b = new TestingOnSiteBooking(userID, testingSiteID, startTime, null);
        b.setBookingID(bookingID);
        b.setUpdatedAt(updatedTime);
        b.setTestingSiteName(testingSiteName);
        dummy.add(b);
      }
    }
    bookings.setValue(dummy);
    return bookings;
  }

  public String getUser(String API_KEY, String userId)
          throws IOException, JSONException {
    OkHttpClient client = new OkHttpClient();

    // insert key here
    String usersUrl = String.format("https://fit3077.com/api/v2/user/%s", userId);
    Request request =
            new Request.Builder()
                    .url(usersUrl)
                    .header("Authorization", API_KEY)
                    .get()
                    .build();

    // Have the response run in background or system will crash
    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
    StrictMode.setThreadPolicy(policy);

    Response response = client.newCall(request).execute();
    String output = response.body().string();

    return output;
  }

}
