package BookingPackage;

import org.json.JSONException;

import java.io.IOException;

import TestingFacilityPackage.TestingFacility;
import UserPackage.User;

public class MakeBookingFacade {

  public String makeBooking(
      User user,
      User customer,
      TestingFacility testingFacility,
      String notes,
      boolean homeTesting,
      String startTime,
      String API_KEY)
      throws JSONException, IOException {
    Booking booking = null;
    String smsPin = null;
    String userID = user.getUserId();
    if (user.getReceptionist()) {
      booking = new TestingOnSiteBooking();
      smsPin = booking.create(userID, testingFacility.getId(), startTime, notes, API_KEY);
    } else if (user.getCustomer() && homeTesting) {

    } else if (user.getCustomer()) {
      booking = new TestingOnSiteBooking();
      smsPin = booking.create(userID, testingFacility.getId(), startTime, notes, API_KEY);
    }
    return smsPin;
  }
}
