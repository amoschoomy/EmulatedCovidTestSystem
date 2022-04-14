package UserPackage;

import org.json.JSONObject;

public class Customer extends User{


    public Customer(String userId, String givenName, String familyName, String userName, String phoneNumber,
                    Boolean isCustomer, Boolean isReceptionist, Boolean isHealthcareWorker,
                    JSONObject additionalInfo) {
        super(userId, givenName, familyName, userName, phoneNumber, isCustomer, isReceptionist, isHealthcareWorker, additionalInfo);
    }

  @Override
  public String checkBooking(String pin) {
    return null;
  }
}
