package models.data.UserPackage;

import org.json.JSONObject;

public class CustomerFactory extends UserFactory{

    @Override
    public Customer createSpecificUser(String userId, String givenName, String familyName, String userName, String phoneNumber, Boolean isCustomer, Boolean isReceptionist, Boolean isHealthcareWorker, JSONObject additionalInfo) {
        return new Customer(userId, givenName, familyName, userName, phoneNumber, isCustomer, isReceptionist, isHealthcareWorker, additionalInfo);
    }
}
