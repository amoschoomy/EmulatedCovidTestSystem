package UserPackage;

import static LoginSystemPackage.LoginSystem.MY_API_KEY;
import static LoginSystemPackage.LoginSystem.ROOT_URL;

import android.os.StrictMode;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import LoginSystemPackage.InvalidCredentialsException;
import LoginSystemPackage.LoginAuthentication;
import LoginSystemPackage.LoginSystem;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Receptionist extends User{


    public Receptionist(String userId, String givenName, String familyName, String userName, String phoneNumber,
                        Boolean isCustomer, Boolean isReceptionist, Boolean isHealthcareWorker, JSONObject additionalInfo) {
        super(userId, givenName, familyName, userName, phoneNumber, isCustomer, isReceptionist, isHealthcareWorker, additionalInfo);
    }


    /**
     * Creates a customer user using the input information. Returns the created User object.
     */
    public Customer createCustomer(String custGivenName, String custFamilyName, String custUserName,
                                   String custPassword, String custPhoneNumber, JSONObject custAdditionalInfo)
            throws JSONException, IOException, InvalidRoleException, InvalidCredentialsException {

        // Check if customer exist
        OkHttpClient client = new OkHttpClient();

        RequestBody formBody = new FormBody.Builder()
                .add("userName", custUserName)
                .add("password", custPassword)
                .build();

        String loginUrl = ROOT_URL + "/user/login";

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

        String header = response.toString();

        Customer cust = null;

        if (header.contains("code=403")) cust = createNewCustomer(custGivenName, custFamilyName,
                custUserName, custPassword, custPhoneNumber, custAdditionalInfo);
        else cust = useExistingCustomer(custUserName, custPassword);

        return cust;

    }


    /**
     * Creates a new customer user using the input information. Returns the created User object.
     */
    private Customer createNewCustomer(String custGivenName, String custFamilyName, String custUserName,
                               String custPassword, String custPhoneNumber, JSONObject custAdditionalInfo)
            throws JSONException, IOException, InvalidRoleException, InvalidCredentialsException {

        OkHttpClient client = new OkHttpClient();

//        RequestBody formBody = new FormBody.Builder()
//                .add("givenName", custGivenName)
//                .add("familyName", custFamilyName)
//                .add("userName", custUserName)
//                .add("password", custPassword)
//                .add("phoneNumber", custPhoneNumber)
//                .add("isCustomer", String.valueOf(true))
//                .add("isAdmin", String.valueOf(false))
//                .add("isHealthcareWorker", String.valueOf(false))
//                .add("additionalInfo", custAdditionalInfo)
//                .build();

        String custAdditionalInfoStr = custAdditionalInfo.toString();

        String jsonstr = String.format("{\"givenName\":\"%s\"," +
                        "\"familyName\":\"%s\"," +
                        "\"userName\":\"%s\"," +
                        "\"password\":\"%s\"," +
                        "\"phoneNumber\":\"%s\"," +
                        "\"isCustomer\":%b," +
                        "\"isAdmin\":%b," +
                        "\"isHealthcareWorker\":%b," +
                        "\"additionalInfo\":%s}",
                custGivenName, custFamilyName, custUserName, custPassword,
                custPhoneNumber, true, false, false, custAdditionalInfoStr);

        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonstr);
//        String jwt = jObj.getString("jwt");
//        Log.d("myTag", jwt);

        String verifyTokenUrl = ROOT_URL + "/user";

        Request request = new Request.Builder()
                .url(verifyTokenUrl)
                .header("Authorization", MY_API_KEY)
                .header("Content-Type","application/json")
                .post(body)
                .build();

        // Have the response run in background or system will crash
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        Response response = client.newCall(request).execute();

        assert response.body() != null;
        String output = response.body().string();

        Log.d("myTag", output);

        return useExistingCustomer(custUserName, custPassword);
    }

    /**
     * Returns the existing User object.
     *
     * InvalidCredentialsException is thrown when username and password is invalid.
     */
    private Customer useExistingCustomer(String custUserName, String custPassword)
            throws JSONException, IOException, InvalidCredentialsException {

        LoginSystem ls = new LoginSystem(MY_API_KEY);
        String jwt = ls.checkCredentials(custUserName, custPassword);

        LoginAuthentication la = LoginAuthentication.getInstance();
        String custUserId = la.getUserId(jwt);
        String custUserInfo = la.getUserInfo(custUserId);

        JSONObject jObj = new JSONObject(custUserInfo);
        String custGivenName = jObj.getString("givenName");
        String custFamilyName = jObj.getString("familyName");
        String custPhoneNumber = jObj.getString("phoneNumber");
        Boolean custIsCustomer = Boolean.valueOf(jObj.getString("isCustomer"));
        Boolean custIsReceptionist = Boolean.valueOf(jObj.getString("isReceptionist"));
        Boolean cutsIsHealthcareWorker = Boolean.valueOf(jObj.getString("isHealthcareWorker"));
        JSONObject custAdditionalInfo = new JSONObject(jObj.getString("additionalInfo"));
//        String custAdditionalInfo = jObj.getString("additionalInfo");


        return new Customer(custUserId, custGivenName, custFamilyName, custUserName, custPhoneNumber,
                custIsCustomer, custIsReceptionist, cutsIsHealthcareWorker, custAdditionalInfo);
    }

}
