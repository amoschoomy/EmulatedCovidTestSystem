package ExceptionPackage;

public class InvalidTokenException extends Exception {

    public InvalidTokenException() {
        super("Invalid JWT Token");
    }
}
