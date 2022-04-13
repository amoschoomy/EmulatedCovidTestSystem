package BookingPackage;

import TestingFacilityPackage.TestingFacilityInterface;
import UserPackage.User;

public class TestingOnSiteBooking implements Booking {
  private TestingFacilityInterface testingFacility;
  private String startTime;
  private String notes;
  private User customer;

  public TestingOnSiteBooking(
      User customer, TestingFacilityInterface testingFacility, String startTime) {
    this.customer = customer;
    this.testingFacility = testingFacility;
    this.startTime = startTime;
  }
}
