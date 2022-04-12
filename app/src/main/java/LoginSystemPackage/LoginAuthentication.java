package LoginSystemPackage;

import static LoginSystemPackage.LoginSystem.MY_API_KEY;
import static LoginSystemPackage.LoginSystem.ROOT_URL;

import android.os.StrictMode;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Base64;

import UserPackage.User;
import UserPackage.UserFactory;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class LoginAuthentication {

    private static LoginAuthentication instance = null;
    private User user;
    private String jwt;

    private LoginAuthentication(String jwt) throws JSONException, IOException {
        Log.d("myTag", "Constructor");

        this.jwt = jwt;

        // Create User object
        // Get Userid
        String userid = getUserId(jwt);
        String userInfoJson = getUserInfo(userid);

        JSONObject jObj = new JSONObject(userInfoJson);
        String givenName = jObj.getString("givenName");
        String familyName = jObj.getString("familyName");
        String userName = jObj.getString("userName");
        String phoneNumber = jObj.getString("phoneNumber");
        Boolean isCustomer = Boolean.valueOf(jObj.getString("isCustomer"));
        Boolean isReceptionist = Boolean.valueOf(jObj.getString("isReceptionist"));
        Boolean isHealthcareWorker = Boolean.valueOf(jObj.getString("isHealthcareWorker"));
        String additionalInfo = jObj.getString("additionalInfo");

        Log.d("myTag", familyName);

        UserFactory uf = new UserFactory();

        User newUser = uf.createUser(givenName, familyName, userName, phoneNumber,
                isCustomer, isReceptionist, isHealthcareWorker, additionalInfo);


    }

    public static LoginAuthentication getInstance(String jwt) throws IOException, JSONException {
        if(instance == null) {
            instance = new LoginAuthentication(jwt);
        }
        return instance;
    }

    /**
     * Get Userid from jwt
     */
    private String getUserId(String jwt) throws JSONException {
        Log.d("myTag", "Get User ID");

        Base64.Decoder decoder = Base64.getUrlDecoder();

        String[] chunks = jwt.split("\\.");
        String payload = new String(decoder.decode(chunks[1]));

        Log.d("myTag", payload);

        // obtain userid from JSON String
        JSONObject jObj = new JSONObject(payload);
        String userid = jObj.getString("sub");
        Log.d("myTag", userid);

        return userid;
    }

    /**
     * Get Userinfo from userid
     */
    private String getUserInfo(String userid) throws JSONException, IOException {

        OkHttpClient client = new OkHttpClient();

        String loginUrl = ROOT_URL + "/user/" + userid;

        Request request = new Request.Builder()
                .url(loginUrl)
                .header("Authorization", MY_API_KEY)
                .get()
                .build();

        // Have the response run in background or system will crash
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        Response response = client.newCall(request).execute();

        String output = response.body().string();

        Log.d("myTag", output);

        return output;
    }

    public User getUser() {
        return this.user;
    }

}