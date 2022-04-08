package TestingFacilityPackage;

public class TestingFacility {
  private TestingFacilityType testingFacilityType;
  private boolean onSiteBooking;
  private int waitingTime;
  private String name;
  private String description;
  private String websiteURL;
  private String phoneNumber;
  private String openingTime;
  private String closingTime;

  public TestingFacility(
      TestingFacilityType testingFacilityType,
      boolean onSiteBooking,
      int waitingTime,
      String name,
      String description,
      String websiteURL,
      String phoneNumber,
      String openingTime,
      String closingTime) {
    this.testingFacilityType = testingFacilityType;
    this.onSiteBooking = onSiteBooking;
    this.waitingTime = waitingTime;
    this.name = name;
    this.description = description;
    this.websiteURL = websiteURL;
    this.phoneNumber = phoneNumber;
    this.openingTime = openingTime;
    this.closingTime = closingTime;
  }

  public String getOpeningTime() {
    return openingTime;
  }

  public String getClosingTime() {
    return closingTime;
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
