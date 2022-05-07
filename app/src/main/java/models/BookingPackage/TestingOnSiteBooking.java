package models.BookingPackage;

import org.json.JSONException;

import okhttp3.FormBody;
import okhttp3.RequestBody;

public class TestingOnSiteBooking implements Booking {

  private String customerID;
  private String testingSiteID;
  private String startTime;
  private String notes;

  public TestingOnSiteBooking(
      String customerID, String testingSiteID, String startTime, String notes) {
    this.customerID = customerID;
    this.testingSiteID = testingSiteID;
    this.startTime = startTime;
    this.notes = notes;
  }

  @Override
  public RequestBody getRequestBody() throws JSONException {
    FormBody.Builder builder =
        new FormBody.Builder()
            .add("customerId", customerID)
            .add("testingSiteId", testingSiteID)
            .add("startTime", startTime);
    if (notes != null) builder.add("notes", notes);
    RequestBody formBody = builder.build();
    return formBody;
  }


}
