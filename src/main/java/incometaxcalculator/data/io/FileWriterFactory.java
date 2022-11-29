package incometaxcalculator.data.io;

import incometaxcalculator.exceptions.WrongFileFormatException;
import org.jetbrains.annotations.NotNull;

public class FileWriterFactory {

    public FileWriterFactory(){}

    public FileWriterInterface createFileWriter(@NotNull String format) throws WrongFileFormatException
    {
        switch (format) {
            case "_INFO.txt":
                return new TXTInfoWriter();
            case "_INFO.xml":
                return new XMLInfoWriter();
            case "_LOG.txt":
                return new TXTLogWriter();
            case "_LOG.xml":
                return new XMLLogWriter();
            default:
                throw new WrongFileFormatException();
        }

    }

}
