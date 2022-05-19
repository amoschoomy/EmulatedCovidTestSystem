package models.data.ExceptionPackage;

/**
 * Exception that catches errors where users input a role they do not have
 */
public class InvalidRoleException extends Exception{
    public InvalidRoleException ()
    {
        super("User Account does not have role");
    }
}
