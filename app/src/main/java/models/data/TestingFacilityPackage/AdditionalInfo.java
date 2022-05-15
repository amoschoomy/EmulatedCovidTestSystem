package models.data.TestingFacilityPackage;

import java.util.ArrayList;

/** Serialise JSON to AddtionalInfo class */
public class AdditionalInfo {
  private TestingFacilityType testingFacilityType;
  private String openingTime;
  private String closingTime;
  private boolean onSiteBooking;
  private String waitingTime;
  private ArrayList<String> admin;

  public TestingFacilityType getTestingFacilityType() {
    return testingFacilityType;
  }

  public String getOpeningTime() {
    return openingTime;
  }

  public String getClosingTime() {
    return closingTime;
  }

  @Override
  public String toString() {
    return "AdditionalInfo{"
        + "testingFacilityType="
        + testingFacilityType
        + ", openingTime='"
        + openingTime
        + '\''
        + ", closingTime='"
        + closingTime
        + '\''
        + ", onSiteBooking="
        + onSiteBooking
        + ", waitingTime='"
        + waitingTime
        + '\''
        + ", admin="
        + admin
        + '}';
  }

  public boolean isOnSiteBooking() {
    return onSiteBooking;
  }

  public String getWaitingTime() {
    return waitingTime;
  }

  public ArrayList<String> getAdmin() {
    return admin;
  }
}
