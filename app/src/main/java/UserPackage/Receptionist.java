package UserPackage;

import static LoginSystemPackage.LoginSystem.MY_API_KEY;
import static LoginSystemPackage.LoginSystem.ROOT_URL;

import android.os.StrictMode;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Base64;

import ExceptionPackage.InvalidCredentialsException;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Receptionist extends User{

    /**
     * Creates receptionist object
     *
     * @param userId
     * @param givenName
     * @param familyName
     * @param userName
     * @param phoneNumber
     * @param isCustomer
     * @param isReceptionist
     * @param isHealthcareWorker
     * @param additionalInfo
     */
    public Receptionist(String userId, String givenName, String familyName, String userName, String phoneNumber,
                        Boolean isCustomer, Boolean isReceptionist, Boolean isHealthcareWorker, JSONObject additionalInfo) {
        super(userId, givenName, familyName, userName, phoneNumber, isCustomer, isReceptionist, isHealthcareWorker, additionalInfo);
    }



    /**
     * Creates a customer user object using the input information. Returns the created User object.
     */
    public Customer createCustomer(String custGivenName, String custFamilyName, String custUserName,
                                   String custPassword, String custPhoneNumber, JSONObject custAdditionalInfo)
            throws JSONException, IOException, InvalidCredentialsException {


        // Check if customer exist
        OkHttpClient client = new OkHttpClient();

        RequestBody formBody = new FormBody.Builder()
                .add("userName", custUserName)
                .add("password", custPassword)
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

        String header = response.toString();

        Customer cust = null;
        if (header.contains("code=403")) cust = createNewCustomer(custGivenName, custFamilyName,
                custUserName, custPassword, custPhoneNumber, custAdditionalInfo);
        else {
            String jwt = response.body().string();
            cust = useExistingCustomerJWT(jwt);
        }

        return cust;

    }


    /**
     * Creates a new customer user using the input information. Returns the created User object.
     */
    private Customer createNewCustomer(String custGivenName, String custFamilyName, String custUserName,
                               String custPassword, String custPhoneNumber, JSONObject custAdditionalInfo)
            throws JSONException, IOException {

        OkHttpClient client = new OkHttpClient();

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
     * Get Userinfo using username and password, and create a customer object
     */
    private Customer useExistingCustomer(String custUserName, String custPassword) throws IOException, JSONException {

        // Get jwt from userName and password
        OkHttpClient client = new OkHttpClient();

        RequestBody formBody = new FormBody.Builder()
                .add("userName", custUserName)
                .add("password", custPassword)
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

        String jwt = response.body().string();
        return useExistingCustomerJWT(jwt);

    }

    /**
     * Get Userinfo using jwt and create a customer object
     */
    private Customer useExistingCustomerJWT(String jwt) throws IOException, JSONException {

        // Get User Id from jwt
        String custUserId = getCustId(jwt);

        // Get User Info from User Id
        String custUserInfo = getCustInfo(custUserId);
        JSONObject custInfo = new JSONObject(custUserInfo);

        String custGivenName = custInfo.getString("givenName");
        String custFamilyName = custInfo.getString("familyName");
        String custUserName = custInfo.getString("phoneNumber");
        String custPhoneNumber = custInfo.getString("phoneNumber");
        Boolean custIsCustomer = Boolean.valueOf(custInfo.getString("isCustomer"));
        Boolean custIsReceptionist = Boolean.valueOf(custInfo.getString("isReceptionist"));
        Boolean cutsIsHealthcareWorker = Boolean.valueOf(custInfo.getString("isHealthcareWorker"));
        JSONObject custAdditionalInfo = new JSONObject(custInfo.getString("additionalInfo"));
//        String custAdditionalInfo = jObj.getString("additionalInfo");

        CustomerFactory cf = new CustomerFactory();
        return cf.createSpecificUser(custUserId, custGivenName, custFamilyName, custUserName, custPhoneNumber,
                custIsCustomer, custIsReceptionist, cutsIsHealthcareWorker, custAdditionalInfo);
//        return new Customer(custUserId, custGivenName, custFamilyName, custUserName, custPhoneNumber,
//                custIsCustomer, custIsReceptionist, cutsIsHealthcareWorker, custAdditionalInfo);
    }

    /**
     * Get Userid from jwt
     */
    private String getCustId(String jwt) throws JSONException {

        // Get User Id from jwt
        Base64.Decoder decoder = Base64.getUrlDecoder();

        // split jwt into its 3 parts and take the payload
        String[] chunks = jwt.split("\\.");
        String payload = new String(decoder.decode(chunks[1]));


        // obtain userid from JSON String
        JSONObject jObj = new JSONObject(payload);
        String custUserId = jObj.getString("sub");

        return custUserId;
    }

    /**
     * Get Userinfo from userid
     */
    private String getCustInfo(String custUserId) throws IOException {

        OkHttpClient client = new OkHttpClient();

        String loginUrl = ROOT_URL + "/user/" + custUserId;

        Request request = new Request.Builder()
                .url(loginUrl)
                .header("Authorization", MY_API_KEY)
                .get()
                .build();

        // Have the response run in background or system will crash
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        Response response = client.newCall(request).execute();

        return response.body().string();
    }

}