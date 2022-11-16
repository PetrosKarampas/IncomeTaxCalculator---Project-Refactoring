package incometaxcalculator.data.io;

import java.io.IOException;

public abstract class LogWriter implements FileWriterInterface{

    public abstract void generateTaxpayerFile(int taxRegistrationNumber) throws IOException;
    @Override
    public void generateFile(int taxRegistrationNumber) throws IOException
    {
        generateTaxpayerFile(taxRegistrationNumber);
    }
}
