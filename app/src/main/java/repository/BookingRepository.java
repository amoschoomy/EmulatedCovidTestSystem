package repository;

import android.app.Application;
import android.os.StrictMode;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import models.BookingPackage.Booking;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class BookingRepository {

  public BookingRepository(Application application) {}

  public LiveData<ArrayList<Booking>> getAllBookings() {
    // api call here //TODO: get all booking from API
    return new LiveData<ArrayList<Booking>>() {
      @Override
      public void observe(
          @NonNull LifecycleOwner owner, @NonNull Observer<? super ArrayList<Booking>> observer) {
        super.observe(owner, observer);
      }
    };
  }

  public String[] create(Booking booking, String API_KEY) throws IOException, JSONException {
    String usersUrl = "https://fit3077.com/api/v2/booking";
    RequestBody body = booking.getRequestBody();
    Request request =
        new Request.Builder().url(usersUrl).header("Authorization", API_KEY).post(body).build();
    // Have the response run in background or system will crash
    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
    StrictMode.setThreadPolicy(policy);
    OkHttpClient client = new OkHttpClient();
    Response response = client.newCall(request).execute();

    assert response.body() != null;
    String output = response.body().string();
    JSONObject jObj = new JSONObject(output);
    String smsPin = jObj.getString("smsPin");
    String bookingID = jObj.getString("id");
    return new String[] {smsPin, bookingID};
  }

  public String[] check(String code, String API_KEY, Boolean isPin) throws Exception {
    String usersUrl = "https://fit3077.com/api/v2/booking";
    String field = isPin ? "smsPin" : "id";
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
      String A = obj.getString(field);
      if (A.equals(field)) {
        String B = obj.getString("status");
        String bookingID = obj.getString("id");
        return new String[] {bookingID, B};
      }
    }
    throw new Exception("No Booking Found");
  }

  public void update(String API_KEY, String bookingID, RequestBody requestBody) throws IOException {
    OkHttpClient client = new OkHttpClient();

    // insert key here
    String usersUrl = String.format("https://fit3077.com/api/v2/booking/%s", bookingID);
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
