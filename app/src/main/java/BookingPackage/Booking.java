package BookingPackage;

import java.util.ArrayList;

import CovidTestPackage.PatientCovidTest;
import TestingFacilityPackage.TestingFacilityInterface;
import UserPackage.User;

public class Booking implements BookingInterface {
  private TestingFacilityInterface testingFacility;
  private String startTime;
  private BookingStatus bookingStatus;
  private String notes;
  private User user;
  private ArrayList<PatientCovidTest> covidTests;
}
