package incometaxcalculator.data.io;

import incometaxcalculator.data.management.Receipt;
import incometaxcalculator.data.management.TaxpayerManager;
import incometaxcalculator.exceptions.WrongTaxpayerStatusException;
import java.io.IOException;
import java.io.PrintWriter;


public abstract class InfoWriter implements FileWriterInterface {
    public abstract void generateTaxpayerInfo(int taxRegistrationNumber) throws WrongTaxpayerStatusException, IOException;
    public abstract void generateTaxpayerReceipts(int taxRegistrationNumber, PrintWriter outputStream);
    public abstract void generateReceiptID(Receipt receipt, PrintWriter outputStream);
    protected TaxpayerManager manager = new TaxpayerManager();
    @Override
    public void generateFile(int taxRegistrationNumber) throws IOException, WrongTaxpayerStatusException {
        generateTaxpayerInfo(taxRegistrationNumber);
    }

}
