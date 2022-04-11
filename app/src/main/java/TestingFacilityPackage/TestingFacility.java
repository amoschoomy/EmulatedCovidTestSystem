package TestingFacilityPackage;

import androidx.annotation.NonNull;

import java.util.ArrayList;

import BookingPackage.Booking;

public class TestingFacility implements TestingFacilityInterface {
  private TestingFacilityType testingFacilityType;
  private int waitingTime;
  private String name;
  private String description;
  private String websiteURL;
  private String phoneNumber;

  public TestingFacilityType getTestingFacilityType() {
    return testingFacilityType;
  }

  public int getWaitingTime() {
    return waitingTime;
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

  public AdditionalInfo getAdditionalInfo() {
    return additionalInfo;
  }

  @Override
  @NonNull
  public String toString() {
    return "TestingFacility{"
        + "testingFacilityType="
        + testingFacilityType
        + ", waitingTime="
        + waitingTime
        + ", name='"
        + name
        + '\''
        + ", description='"
        + description
        + '\''
        + ", websiteURL='"
        + websiteURL
        + '\''
        + ", phoneNumber='"
        + phoneNumber
        + '\''
        + ", bookings="
        + bookings
        + ", address="
        + address
        + ", additionalInfo="
        + additionalInfo
        + '}';
  }

  private ArrayList<Booking> bookings;
  private Address address;
  private AdditionalInfo additionalInfo;
}
