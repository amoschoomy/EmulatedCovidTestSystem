package models.data.LoginSystemPackage;

import static models.data.LoginSystemPackage.LoginSystem.MY_API_KEY;
import static models.data.LoginSystemPackage.LoginSystem.ROOT_URL;

import android.os.StrictMode;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Base64;

import models.data.ExceptionPackage.InvalidRoleException;
import models.data.UserPackage.CustomerFactory;
import models.data.UserPackage.HealthcareWorkerFactory;
import models.data.UserPackage.PatientFactory;
import models.data.UserPackage.ReceptionistFactory;
import models.data.UserPackage.User;
import models.data.UserPackage.UserFactory;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Class to keep track of user that is logged in
 */
public class LoginAuthentication {

  private static LoginAuthentication instance = null;
  private User user;
  //    private String jwt;

  private LoginAuthentication() {}

  /** To get the LoginAuthentication Instance */
  public static LoginAuthentication getInstance() throws IOException, JSONException {
    if (instance == null) {
      instance = new LoginAuthentication();
    }
    return instance;
  }

  /** Creates and sets the User Object in LoginAuthentication */
  public void setUser(String jwt, String userRole)
      throws JSONException, IOException, InvalidRoleException {
    //        this.jwt = jwt;

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
    JSONObject additionalInfo = new JSONObject(jObj.getString("additionalInfo"));
    //        String additionalInfo = jObj.getString("additionalInfo");

    User newUser = null;
    UserFactory uf;
    switch (userRole.toLowerCase()) {
      case "customer":
        if (isCustomer) {
          uf = new CustomerFactory();
          newUser = uf.createSpecificUser(userid, givenName, familyName, userName, phoneNumber,
                  true, false, false, additionalInfo);
        }
        break;
      case "receptionist":
        if (isReceptionist){
          Log.d("myTag", "here");
          uf = new ReceptionistFactory();
          newUser = uf.createSpecificUser(userid, givenName, familyName, userName, phoneNumber,
                  false, true, false, additionalInfo);
        }
        break;
      case "healthcare worker":
        if (isHealthcareWorker){
          uf = new HealthcareWorkerFactory();
          newUser = uf.createSpecificUser(userid, givenName, familyName, userName, phoneNumber,
                  false, false, true, additionalInfo);
        }
        break;
      case "patient":
        uf = new PatientFactory();
        newUser = uf.createSpecificUser(userid, givenName, familyName, userName, phoneNumber,
                false, false, false, additionalInfo);
        break;


    }
    if (newUser==null) throw new InvalidRoleException();

    this.user=newUser;

  }

  /** Get Userid from jwt */
  private String getUserId(String jwt) throws JSONException {

    Base64.Decoder decoder = Base64.getUrlDecoder();

    // split jwt into its 3 parts and take the payload
    String[] chunks = jwt.split("\\.");
    String payload = new String(decoder.decode(chunks[1]));

    // obtain userid from JSON String
    JSONObject jObj = new JSONObject(payload);
    String userid = jObj.getString("sub");

    return userid;
  }

  /** Get Userinfo from userid */
  private String getUserInfo(String userid) throws IOException {

    OkHttpClient client = new OkHttpClient();

    String loginUrl = ROOT_URL + "/user/" + userid;

    Request request =
        new Request.Builder().url(loginUrl).header("Authorization", MY_API_KEY).get().build();

    // Have the response run in background or system will crash
    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
    StrictMode.setThreadPolicy(policy);

    Response response = client.newCall(request).execute();

    String output = response.body().string();

    return output;
  }

  public User getUser() {
    return this.user;
  }
}
