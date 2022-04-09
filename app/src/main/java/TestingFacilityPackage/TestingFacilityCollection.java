package TestingFacilityPackage;

import androidx.annotation.NonNull;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class TestingFacilityCollection {
  private static TestingFacilityCollection instance;
  private ArrayList<TestingFacility> testingFacilities = new ArrayList<>();

  private TestingFacilityCollection() {}

  public static TestingFacilityCollection getInstance() {
    if (instance == null) {
      instance = new TestingFacilityCollection();
    }
    return instance;
  }

  public ArrayList<TestingFacility> retrieveTestingFacilities(String API_KEY) {
    OkHttpClient client = new OkHttpClient();

    // insert key here
    final String MY_API_KEY = API_KEY;
    String usersUrl = "https://fit3077.com/api/v1/testing-site";

    Request request =
        new Request.Builder().url(usersUrl).header("Authorization", MY_API_KEY).build();

    client
        .newCall(request)
        .enqueue(
            new Callback() {
              @Override
              public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
              }

              @Override
              public void onResponse(@NonNull Call call, @NonNull Response response)
                  throws IOException {
                if (response.isSuccessful()) {
                  assert response.body() != null;
                  String strResponse = response.body().string();
                  String strResponseCode = Integer.toString(response.code());
                } else {
                }
              }
            });
    return testingFacilities;
  }

  public ArrayList<TestingFacility> getTestingFacilities() {
    return testingFacilities;
  }
}
