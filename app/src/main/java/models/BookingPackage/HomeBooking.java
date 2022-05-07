package models.BookingPackage;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.MediaType;
import okhttp3.RequestBody;

public class HomeBooking implements Booking{

  private String customerID;
  private String testingSiteID;
  private String startTime;
  private String notes;
  private String testingURL;

  public HomeBooking(String customerID, String testingSiteID, String startTime, String notes) {
    this.customerID = customerID;
    this.testingSiteID = testingSiteID;
    this.startTime = startTime;
    this.notes = notes;
  }

  public RequestBody getRequestBody() throws JSONException {
        // creating post body

        String testingUrl = "http://www.example.com/";
    String custAdditionalInfoStr = String.format("{" + "\"url\":\"%s\"" + "}", testingUrl);
        JSONObject custInfoJson = new JSONObject(custAdditionalInfoStr);

        String jsonstr = String.format("{\"customerId\":\"%s\"," +
                        "\"testingSiteId\":\"%s\"," +
                        "\"startTime\":\"%s\"," +
                        "\"additionalInfo\": %s}",
                customerID, testingSiteID, startTime, custInfoJson);

        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonstr);
    return body;
    }

}
