package UserPackage;

public class InvalidRoleException extends Exception{
    public InvalidRoleException ()
    {
        super("User Account does not have role");
    }
}
