package LoginSystemPackage;

import android.os.StrictMode;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class LoginSystem {

    // insert key here
    public static final String MY_API_KEY = "KEY";
    public static final String ROOT_URL = "https://fit3077.com/api/v1";

    /**
     * Verifies if username and password are correct. If correct, a jwt token is generated.
     */
    public String checkCredentials(String username, String password) throws IOException, JSONException {
        Log.d("myTag", "Check Credentials");
        Log.d("myTag", username);
        Log.d("myTag", password);

        OkHttpClient client = new OkHttpClient();

        // Create json object
        RequestBody formBody = new FormBody.Builder()
                .add("userName", username)
                .add("password", password)
                .build();

        String loginUrl = ROOT_URL + "/user/login?jwt=true";

        Request request = new Request.Builder()
                .url(loginUrl)
                .header("Authorization", MY_API_KEY)
                .header("Content-Type","application/json")
                .post(formBody)
                .build();

        // Have the response run in background or system will crash
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        Response response = client.newCall(request).execute();

        String output = response.body().string();

        Log.d("myTag", output);

        // obtain jwt value from string
        JSONObject jObj = new JSONObject(output);
        String jwt = jObj.getString("jwt");
        Log.d("myTag", jwt);

        return jwt;

    }

    /**
     * Verifies if jwt token is valid.
     */
    public static void VerifyJWTToken(String jwt) throws IOException {
        Log.d("myTag", "Verify Token");

        OkHttpClient client = new OkHttpClient();

        RequestBody formBody = new FormBody.Builder()
                .add("jwt", jwt)
                .build();

        String verifyTokenUrl = ROOT_URL + "/user/verify-token";

        Request request = new Request.Builder()
                .url(verifyTokenUrl)
                .header("Authorization", MY_API_KEY)
                .header("Content-Type","application/json")
                .post(formBody)
                .build();

        // Have the response run in background or system will crash
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        Response response = client.newCall(request).execute();

        String output = response.body().string();

        Log.d("myTag", output);

    }
}

