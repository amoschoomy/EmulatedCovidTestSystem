package UserPackage;

import org.json.JSONObject;

public abstract class UserFactory {

    /**
     * A factory method that creates a user object with a role
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
     * @return User object with role
     */


    public abstract User createSpecificUser(String userId, String givenName, String familyName, String userName, String phoneNumber,
                                            Boolean isCustomer, Boolean isReceptionist, Boolean isHealthcareWorker,
                                            JSONObject additionalInfo);

}
