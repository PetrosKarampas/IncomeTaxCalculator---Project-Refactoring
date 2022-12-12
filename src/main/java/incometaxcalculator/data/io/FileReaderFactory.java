package incometaxcalculator.data.io;

import incometaxcalculator.exceptions.WrongFileFormatException;

public class FileReaderFactory {
    public FileReaderFactory() {}

    public FileReader createFileReader(String fileFormat) {
        if(fileFormat.equals("txt"))
            return new TXTFileReader();
        else
            return new XMLFileReader();
    }
}
