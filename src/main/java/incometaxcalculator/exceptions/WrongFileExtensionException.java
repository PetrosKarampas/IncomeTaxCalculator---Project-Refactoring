package incometaxcalculator.exceptions;

public class WrongFileExtensionException extends Exception {

  private static final long serialVersionUID = 8930076634284333602L;

  public WrongFileExtensionException() {
    super("Please check your file ending and try again!");
  }
}