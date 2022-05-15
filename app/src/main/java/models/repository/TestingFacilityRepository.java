package models.repository;

import android.app.Application;
import android.os.StrictMode;

import org.json.JSONException;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class TestingFacilityRepository {

    public TestingFacilityRepository(Application application) {}

    public String getTestingFacility(String API_KEY, String testingID)
            throws IOException, JSONException {
        OkHttpClient client = new OkHttpClient();

        // insert key here
        String usersUrl = String.format("https://fit3077.com/api/v2/testing-site/%s", testingID);
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
