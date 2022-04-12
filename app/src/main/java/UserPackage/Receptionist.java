package UserPackage;

public class Receptionist extends User{


    public Receptionist(String userId, String givenName, String familyName, String userName, String phoneNumber,
                   Boolean isCustomer, Boolean isReceptionist, Boolean isHealthcareWorker,
                   String additionalInfo) {
        super(userId, givenName, familyName, userName, phoneNumber, isCustomer, isReceptionist, isHealthcareWorker, additionalInfo);
    }
}
