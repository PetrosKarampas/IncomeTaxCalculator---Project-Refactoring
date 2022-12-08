package incometaxcalculator.data.io;

import incometaxcalculator.exceptions.WrongFileFormatException;
import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.IOException;

public interface FileReaderInterface {
    int checkForReceipt(@NotNull BufferedReader inputStream) throws NumberFormatException, IOException;
    String getValueOfField(String fieldsLine) throws WrongFileFormatException;

}
