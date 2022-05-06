package models.BookingPackage;

import android.os.StrictMode;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class HomeBooking implements Booking{

    /**
     * Creates homebooking
     *
     * @param customerID
     * @param testingSiteID
     * @param startTime
     * @param notes
     * @param API_KEY
     * @return sms pin for user to keep track of booking
     * @throws IOException
     * @throws JSONException
     */
    @Override
    public String create(String customerID, String testingSiteID, String startTime,
                         String notes, String API_KEY) throws IOException, JSONException {

        String bookingUrl = "https://fit3077.com/api/v1/booking";
        OkHttpClient client = new OkHttpClient();

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