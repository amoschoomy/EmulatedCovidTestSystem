package models.data.ExceptionPackage;

/**
 * Exception that catches errors where jwt token is invalid
 */
public class InvalidTokenException extends Exception {

    public InvalidTokenException() {
        super("Invalid JWT Token");
    }
}
