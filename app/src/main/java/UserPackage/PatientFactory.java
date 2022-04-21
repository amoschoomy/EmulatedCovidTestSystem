package UserPackage;

import org.json.JSONObject;

public class PatientFactory extends UserFactory{
    @Override
    public Patient createUser(String userId, String givenName, String familyName, String userName, String phoneNumber,
                               Boolean isCustomer, Boolean isReceptionist, Boolean isHealthcareWorker,
                               JSONObject additionalInfo, String userRole) {

        return new Patient(userId, givenName, familyName, userName, phoneNumber, isCustomer, isReceptionist, isHealthcareWorker, additionalInfo);
    }

    @Override
    public Patient createSpecificUser(String userId, String givenName, String familyName, String userName, String phoneNumber, Boolean isCustomer, Boolean isReceptionist, Boolean isHealthcareWorker, JSONObject additionalInfo) {
        return new Patient(userId, givenName, familyName, userName, phoneNumber, isCustomer, isReceptionist, isHealthcareWorker, additionalInfo);
    }
}
