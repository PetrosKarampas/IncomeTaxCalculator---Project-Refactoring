package incometaxcalculator.data.io;

import incometaxcalculator.data.management.Receipt;
import incometaxcalculator.data.management.TaxpayerManager;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

public abstract class InfoWriter implements FileWriterInterface {

    public abstract void generateTaxpayerFile(int taxRegistrationNumber) throws IOException;
    public abstract void generateReceipts(HashMap<Integer, Receipt> receiptsHashMap, PrintWriter outputStream);
    @Override
    public void generateFile(int taxRegistrationNumber) throws IOException {
        generateTaxpayerFile(taxRegistrationNumber);
    }

    public void generateTaxpayerReceipts(int taxRegistrationNumber, PrintWriter outputStream) {
        TaxpayerManager manager = new TaxpayerManager();
        HashMap<Integer, Receipt> receiptsHashMap = manager.getReceiptHashMap(taxRegistrationNumber);
        generateReceipts(receiptsHashMap, outputStream);
    }
}
