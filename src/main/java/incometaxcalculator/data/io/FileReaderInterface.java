package incometaxcalculator.data.io;

import incometaxcalculator.exceptions.WrongFileFormatException;
import java.io.BufferedReader;
import java.io.IOException;

public interface FileReaderInterface
{
    int checkForReceipt(BufferedReader inputStream) throws NumberFormatException, IOException;
    String getValueOfField(String fieldsLine) throws WrongFileFormatException;
}
