package models.ExceptionPackage;

public class InvalidUndoException extends Exception {
  public InvalidUndoException(String no_undo) {
    super(no_undo);
  }
}
