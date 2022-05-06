package models.UserPackage;

import org.json.JSONObject;

public class HealthcareWorker extends User {

    /**
     * Creates healthcare worker object
     *
     * @param userId
     * @param givenName
     * @param familyName
     * @param userName
     * @param phoneNumber
     * @param isCustomer
     * @param isReceptionist
     * @param isHealthcareWorker
     * @param additionalInfo
     */
    public HealthcareWorker(String userId, String givenName, String familyName, String userName, String phoneNumber,
                            Boolean isCustomer, Boolean isReceptionist, Boolean isHealthcareWorker,
                            JSONObject additionalInfo) {
        super(userId, givenName, familyName, userName, phoneNumber, isCustomer, isReceptionist, isHealthcareWorker, additionalInfo);
    }


}
