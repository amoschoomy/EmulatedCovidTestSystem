package BookingPackage;

import TestingFacilityPackage.TestingFacility;
import UserPackage.User;

public class MakeBookingFacade {

  public Booking makeBooking(
      User user,
      User customer,
      TestingFacility testingFacility,
      boolean homeTesting,
      String startTime) {
    Booking booking = null;

    String userID = user.getUserId();
    if (user.getReceptionist()) {
      booking = new TestingOnSiteBooking(customer, testingFacility, startTime);
    } else if (user.getCustomer() && homeTesting) {

    } else if (user.getCustomer()) {
      booking = new TestingOnSiteBooking(user, testingFacility, startTime);
    }
    return booking;
  }
}
