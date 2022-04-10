package UserPackage;

public class UserFactory {
    public User createUser(String type) {
        User newUser = null;

        if(type.equals("Customer")){
            newUser = new Customer();
        } else if (type.equals("Receptionist")) {
            newUser = new Receptionist();
        }

        return newUser;
    }
}
