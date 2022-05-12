package models.BookingPackage;

import org.json.JSONException;

import okhttp3.FormBody;
import okhttp3.RequestBody;

public class TestingOnSiteBooking extends Booking {

  public TestingOnSiteBooking(
      String customerID, String testingSiteID, String startTime, String notes) {
    super(customerID,testingSiteID,startTime,notes);
  }



}
