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
  private ArrayList<Booking> bookings;
  private Address address;
  private AdditionalInfo additionalInfo;
}
