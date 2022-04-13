package UserPackage;

public class UserFactory {
    public User createUser(String userId, String givenName, String familyName, String userName, String phoneNumber,
                           Boolean isCustomer, Boolean isReceptionist, Boolean isHealthcareWorker,
                           String additionalInfo, String userRole) throws InvalidRoleException {

        User newUser = null;

        switch (userRole) {
            case "customer":
                if (isCustomer)
                    newUser = new Customer(userId, givenName, familyName, userName, phoneNumber,
                            isCustomer, isReceptionist, isHealthcareWorker, additionalInfo);
                break;
            case "receptionist":
                if (isReceptionist)
                    newUser = new Receptionist(userId, givenName, familyName, userName, phoneNumber,
                        isCustomer, isReceptionist, isHealthcareWorker, additionalInfo);
                break;
            case "healthcare worker":
                if (isHealthcareWorker)
                    newUser = new HealthcareWorker(userId, givenName, familyName, userName, phoneNumber,
                        isCustomer, isReceptionist, isHealthcareWorker, additionalInfo);
                break;
            case "patient":
                newUser = new Patient(userId, givenName, familyName, userName, phoneNumber,
                        isCustomer, isReceptionist, isHealthcareWorker, additionalInfo);
                break;
        }

        if (newUser==null)  throw new InvalidRoleException();

        return newUser;
    }

}
