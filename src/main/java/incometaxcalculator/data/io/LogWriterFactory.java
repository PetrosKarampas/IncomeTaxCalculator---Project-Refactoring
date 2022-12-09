package incometaxcalculator.data.io;

import incometaxcalculator.exceptions.WrongFileFormatException;

public class LogWriterFactory {

    public LogWriterFactory() {}

    public LogWriter createLogWriter(String fileFormat) throws WrongFileFormatException{
        if(fileFormat.equals("txt"))
            return new TXTLogWriter();
        else if(fileFormat.equals("xml"))
            return new XMLLogWriter();
        else
            throw new WrongFileFormatException();
    }
}
