package BookingPackage;

import java.util.ArrayList;

import CovidTestPackage.CovidTest;
import TestingFacilityPackage.TestingFacility;
import UserPackage.User;

public class Booking {
  private TestingFacility testingFacility;
  private String startTime;
  private BookingStatus bookingStatus;
  private String notes;
  private User user;
  private ArrayList<CovidTest> covidTests;
}
