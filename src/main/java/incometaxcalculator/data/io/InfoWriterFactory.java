package incometaxcalculator.data.io;

import incometaxcalculator.data.io.FileWriter;
import incometaxcalculator.data.io.TXTInfoWriter;
import incometaxcalculator.data.io.XMLInfoWriter;

import java.io.Writer;

public class InfoWriterFactory {

    public InfoWriterFactory() {}

    public InfoWriter createInfoWriter(String fileFormat) {
        if(fileFormat.equals("xml"))
            return new XMLInfoWriter();
        else
            return new TXTInfoWriter();
    }
}
