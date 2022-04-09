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
    private static final String myApiKey = "KEY";
    private static final String rootUrl = "https://fit3077.com/api/v1";

    /**
     * Verifies if username and password are correct. If correct, a jwt token is generated.
     */
    private String checkCredentials(String username, String password) throws IOException, JSONException {
        Log.d("myTag", "Check Credentials");

        OkHttpClient client = new OkHttpClient();

        // Create json object
        RequestBody formBody = new FormBody.Builder()
                .add("userName", username)
                .add("password", password)
                .build();

        String loginUrl = rootUrl + "/user/login?jwt=true";

        Request request = new Request.Builder()
                .url(loginUrl)
                .header("Authorization", myApiKey)
                .header("Content-Type","application/json")
                .post(formBody)
                .build();

        // Have the response run in background or system will crash
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        Response response = client.newCall(request).execute();

        String output = response.body().string();

        // obtain jwt value from string
        JSONObject jObj = new JSONObject(output);
        String jwt = jObj.getString("jwt");
        Log.d("myTag", jwt);

        return jwt;

    }

    /**
     * Verifies if jwt token is valid.
     */
    private void VerifyJWTToken(String jwt) throws IOException {
        Log.d("myTag", "Verify Token");

        OkHttpClient client = new OkHttpClient();

        RequestBody formBody = new FormBody.Builder()
                .add("jwt", jwt)
                .build();

        String verifyTokenUrl = rootUrl + "/user/verify-token";

        Request request = new Request.Builder()
                .url(verifyTokenUrl)
                .header("Authorization", myApiKey)
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

