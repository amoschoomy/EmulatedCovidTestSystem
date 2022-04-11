package TestingFacilityPackage;

import android.os.StrictMode;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;

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

  public ArrayList<TestingFacility> retrieveTestingFacilities(String API_KEY) throws IOException {
    OkHttpClient client = new OkHttpClient();

    // insert key here
    String usersUrl = "https://fit3077.com/api/v1/testing-site";

    Request request = new Request.Builder().url(usersUrl).header("Authorization", API_KEY).build();
    // Have the response run in background or system will crash
    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
    StrictMode.setThreadPolicy(policy);

    Response response = client.newCall(request).execute();

    assert response.body() != null;
    String output = response.body().string();
    Type listType = new TypeToken<ArrayList<TestingFacility>>() {}.getType();
    testingFacilities = new Gson().fromJson(output, listType);
    //    Log.d("myTag", testingFacilities.toString());

    return testingFacilities;
  }

  public ArrayList<TestingFacility> getTestingFacilities() {
    return testingFacilities;
  }
}
