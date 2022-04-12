package UserPackage;

public class Customer extends User{


    public Customer(String userId, String givenName, String familyName, String userName, String phoneNumber,
                    Boolean isCustomer, Boolean isReceptionist, Boolean isHealthcareWorker,
                    String additionalInfo) {
        super(userId, givenName, familyName, userName, phoneNumber, isCustomer, isReceptionist, isHealthcareWorker, additionalInfo);
    }
}
