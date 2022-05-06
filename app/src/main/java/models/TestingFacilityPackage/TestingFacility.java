package models.TestingFacilityPackage;

import java.util.ArrayList;

import models.BookingPackage.Booking;

public class TestingFacility implements TestingFacilityInterface {
  private String id;
  private String name;
  private String description;
  private String websiteURL;
  private String phoneNumber;

  public String getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public String getDescription() {
    return description;
  }

  public String getWebsiteURL() {
    return websiteURL;
  }

  public String getPhoneNumber() {
    return phoneNumber;
  }

  public ArrayList<Booking> getBookings() {
    return bookings;
  }

  public Address getAddress() {
    return address;
  }

  public String getOpeningTimes() {
    return additionalInfo.getOpeningTime();
  }

  public String getClosingTimes() {
    return additionalInfo.getClosingTime();
  }

  public TestingFacilityType getTestingFacilityType() {
    return additionalInfo.getTestingFacilityType();
  }

  public boolean isOnSiteBooking() {
    return additionalInfo.isOnSiteBooking();
  }

  public String getWaitingTimes() {
    return additionalInfo.getWaitingTime();
  }
  private ArrayList<Booking> bookings;
  private Address address;
  private AdditionalInfo additionalInfo;
}
