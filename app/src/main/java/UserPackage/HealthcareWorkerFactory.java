package UserPackage;

import org.json.JSONObject;

public class HealthcareWorkerFactory extends UserFactory{

    @Override
    public HealthcareWorker createSpecificUser(String userId, String givenName, String familyName, String userName, String phoneNumber, Boolean isCustomer, Boolean isReceptionist, Boolean isHealthcareWorker, JSONObject additionalInfo) {
        return new HealthcareWorker(userId, givenName, familyName, userName, phoneNumber, isCustomer, isReceptionist, isHealthcareWorker, additionalInfo);
    }
}
