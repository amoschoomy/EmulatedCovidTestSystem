package TestingFacilityPackage;

import java.util.ArrayList;

import BookingPackage.Booking;

public class TestingFacility implements TestingFacilityInterface {
  private TestingFacilityType testingFacilityType;
  private int waitingTime;
  private String name;
  private String description;
  private String websiteURL;
  private String phoneNumber;

  @Override
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
