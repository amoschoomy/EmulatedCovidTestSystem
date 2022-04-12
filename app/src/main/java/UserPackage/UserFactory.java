package UserPackage;

public class UserFactory {
    public User createUser(String givenName, String familyName, String userName, String phoneNumber,
                           Boolean isCustomer, Boolean isReceptionist, Boolean isHealthcareWorker,
                           String additionalInfo) {

        User newUser = null;

        // Need better method to differentiate Users

        if (additionalInfo.equals("isPatient")) {
            newUser = new Patient(givenName, familyName, userName, phoneNumber,
                    isCustomer, isReceptionist, isHealthcareWorker, additionalInfo);
        } else if(isReceptionist){
            newUser = new Receptionist(givenName, familyName, userName, phoneNumber,
                    isCustomer, isReceptionist, isHealthcareWorker, additionalInfo);
        } else if (isHealthcareWorker) {
            newUser = new HealthcareWorker(givenName, familyName, userName, phoneNumber,
                    isCustomer, isReceptionist, isHealthcareWorker, additionalInfo);
        } else if (isCustomer) {
            newUser = new Customer(givenName, familyName, userName, phoneNumber,
                    isCustomer, isReceptionist, isHealthcareWorker, additionalInfo);
        }

        return newUser;
    }

//    public User createUser(String givenName, String familyName, String userName, String phoneNumber,
//                           String additionalInfo,
//                           String type) {
//
//        User newUser = null;
//
//        switch (type) {
//            case "customer":
//                newUser = new Customer();
//                break;
//            case "receptionist":
//                newUser = new Receptionist();
//                break;
//            case "healthcare worker":
//                newUser = new HealthcareWorker();
//                break;
//            case "patient":
//                newUser = new Patient();
//                break;
//        }
//
//        return newUser;
//    }
}
