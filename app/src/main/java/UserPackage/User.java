package UserPackage;


public abstract class User {

    private String userId;
    private String givenName;
    private String familyName;
    private String userName;
    private String phoneNumber;
    private Boolean isCustomer;
    private Boolean isReceptionist;

  public String getUserId() {
    return userId;
  }

  public Boolean getCustomer() {
    return isCustomer;
  }

  public Boolean getReceptionist() {
    return isReceptionist;
  }

  public Boolean getHealthcareWorker() {
    return isHealthcareWorker;
  }

    private Boolean isHealthcareWorker;
    private String additionalInfo;

    public User(String userId, String givenName, String familyName, String userName,
                String phoneNumber, Boolean isCustomer, Boolean isReceptionist,
                Boolean isHealthcareWorker, String additionalInfo) {
        this.userId = userId;
        this.givenName = givenName;
        this.familyName = familyName;
        this.userName = userName;
        this.phoneNumber = phoneNumber;
        this.isCustomer = isCustomer;
        this.isReceptionist = isReceptionist;
        this.isHealthcareWorker = isHealthcareWorker;
        this.additionalInfo = additionalInfo;
    }

}
