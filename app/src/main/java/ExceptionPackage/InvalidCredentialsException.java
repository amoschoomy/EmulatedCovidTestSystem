package ExceptionPackage;

/**
 * Exception that catches errors where users input the wrong username or password
 */
public class InvalidCredentialsException  extends Exception{
    public InvalidCredentialsException() {
        super("Invalid Username or Password");
    }
}
