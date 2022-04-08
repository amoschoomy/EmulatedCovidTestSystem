package TestingFacilityPackage;

public class TestingFacility {
  private final TestingFacilityType testingFacilityType;
  private final boolean onSiteBooking;
  private final int waitingTime;
  private final String name;
  private final String description;
  private final String websiteURL;
  private final String phoneNumber;
  private String openingTime;
  private String closingTime;

  public TestingFacility(
      TestingFacilityType testingFacilityType,
      boolean onSiteBooking,
      int waitingTime,
      String name,
      String description,
      String websiteURL,
      String phoneNumber) {
    this.testingFacilityType = testingFacilityType;
    this.onSiteBooking = onSiteBooking;
    this.waitingTime = waitingTime;
    this.name = name;
    this.description = description;
    this.websiteURL = websiteURL;
    this.phoneNumber = phoneNumber;
  }

  public TestingFacilityType getTestingFacilityType() {
    return testingFacilityType;
  }

  public boolean isOnSiteBooking() {
    return onSiteBooking;
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
}