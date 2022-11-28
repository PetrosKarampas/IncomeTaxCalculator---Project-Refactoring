package incometaxcalculator.data.io;

import incometaxcalculator.exceptions.WrongFileFormatException;
import org.jetbrains.annotations.NotNull;

public class FileWriterFactory {

    public FileWriterFactory(){}

    public FileWriterInterface createFileWriter(@NotNull String format) throws WrongFileFormatException
    {
        return switch (format) {
            case "_INFO.txt" -> new TXTInfoWriter();
            case "_INFO.xml" -> new XMLInfoWriter();
            case "_LOG.txt"  -> new TXTLogWriter();
            case "_LOG.xml"  -> new XMLLogWriter();
            default          -> throw new WrongFileFormatException();
        };
    }

}
