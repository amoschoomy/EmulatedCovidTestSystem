package ExceptionPackage;

public class InvalidCredentialsException  extends Exception{

    public InvalidCredentialsException() {
        super("Invalid Username or Password");
    }
}
