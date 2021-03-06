package models.data.UserPackage;

import org.json.JSONObject;

public class ReceptionistFactory extends UserFactory{

    @Override
    public Receptionist createSpecificUser(String userId, String givenName, String familyName, String userName, String phoneNumber, Boolean isCustomer, Boolean isReceptionist, Boolean isHealthcareWorker, JSONObject additionalInfo) {
        return new Receptionist(userId, givenName, familyName, userName, phoneNumber, isCustomer, isReceptionist, isHealthcareWorker, additionalInfo);
    }
}
