package incometaxcalculator.data.io;

public class InfoWriterFactory {

    public InfoWriterFactory() {}

    public InfoWriter createInfoWriter(String fileFormat) {
        if(fileFormat.equals("xml"))
            return new XMLInfoWriter();
        else
            return new TXTInfoWriter();
    }
}
