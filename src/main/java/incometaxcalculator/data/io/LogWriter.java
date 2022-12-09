package incometaxcalculator.data.io;

import incometaxcalculator.data.management.TaxpayerManager;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

public abstract class LogWriter implements FileWriterInterface
{
    public abstract void generateTaxpayerLogFile(int taxRegistrationNumber) throws IOException;
    public abstract void writeTaxpayerVariationTaxOnReceipts(int taxRegistrationNumber, PrintWriter outputStream);
    public abstract void writeTaxpayerTotalTaxAndTotalReceipts(int taxRegistrationNumber, PrintWriter outputStream);
    public abstract void writeReceiptsAmountPerKind(int taxRegistrationNumber, HashMap<String, Integer> kindHashMap, PrintWriter outputStream);
    public abstract double calculateReceiptAmountForGivenKind(int taxRegistrationNumber, HashMap<String, Integer> kindHashMap, String kind);
    protected final HashMap<String, Integer> kindHashMap = new HashMap<>();
    protected TaxpayerManager manager = new TaxpayerManager();
    @Override
    public void generateFile(int taxRegistrationNumber) throws IOException
    {
        initializeKindHashMap();
        generateTaxpayerLogFile(taxRegistrationNumber);
    }
    public void initializeKindHashMap()
    {
        kindHashMap.put("ENTERTAINMENT",0);
        kindHashMap.put("BASIC",1);
        kindHashMap.put("TRAVEL",2);
        kindHashMap.put("HEALTH",3);
        kindHashMap.put("OTHER",4);
    }
}
