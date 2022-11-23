package incometaxcalculator.data.io;

import incometaxcalculator.exceptions.WrongFileFormatException;
import org.jetbrains.annotations.NotNull;

public class FileReaderFactory {

    public FileReaderFactory() {}

    public FileReader createFileReader(@NotNull String fileFormat) throws WrongFileFormatException{
        if(fileFormat.equals("txt"))
            return new TXTFileReader();
        else if(fileFormat.equals("xml"))
            return new XMLFileReader();
        else
            throw new WrongFileFormatException();
    }
}
