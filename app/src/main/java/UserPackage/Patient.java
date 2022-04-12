package UserPackage;

public class Patient extends User {


    public Patient(String userId, String givenName, String familyName, String userName, String phoneNumber,
                   Boolean isCustomer, Boolean isReceptionist, Boolean isHealthcareWorker,
                   String additionalInfo) {
        super(userId, givenName, familyName, userName, phoneNumber, isCustomer, isReceptionist, isHealthcareWorker, additionalInfo);
    }
}
