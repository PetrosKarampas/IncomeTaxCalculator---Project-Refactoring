package incometaxcalculator.exceptions;

public class WrongTaxRegistrationNumberException extends Exception{

    public WrongTaxRegistrationNumberException(){ super("The given taxRegistrationNumber does not exist!");}
}
