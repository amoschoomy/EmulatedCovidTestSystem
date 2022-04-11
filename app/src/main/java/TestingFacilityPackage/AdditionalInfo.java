package TestingFacilityPackage;

public class AdditionalInfo {
  private TestingFacilityType testingFacilityType;
  private String openingTime;
  private String closingTime;

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
        + '}';
  }

  public boolean isOnSiteBooking() {
    return onSiteBooking;
  }

  public String getWaitingTime() {
    return waitingTime;
  }

  private boolean onSiteBooking;
  private String waitingTime;
}
