package CovidTestPackage;

import BookingPackage.BookingInterface;
import TestingFacilityPackage.TestingFacilityInterface;
import UserPackage.User;

public class PatientCovidTest {
  private User patient;
  private User administer;
  private TestingFacilityInterface testingFacility;
  private CovidTest covidTest;
  private CovidTestResult result;
  private CovidTestStatus status;
  private String notes;
  private String datePerformed;
  private String dateOfResults;
  private BookingInterface booking;
}
