package incometaxcalculator.data.io;

import incometaxcalculator.exceptions.WrongFileFormatException;

public class FileWriterFactory
{
    public FileWriterFactory(){}

    public FileWriterInterface createFileWriter(String format)
    {
        switch (format)
        {
            case "_INFO.txt": return new TXTInfoWriter();
            case "_INFO.xml": return new XMLInfoWriter();
            case "_LOG.txt" : return new TXTLogWriter();
            default:  return new XMLLogWriter();

        }
    }
}
