package incometaxcalculator.data.io;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
public class TXTLogWriter extends LogWriter
{

  @Override
  public void generateTaxpayerLogFile(int taxRegistrationNumber) throws IOException {
    PrintWriter outputStream = new PrintWriter(new java.io.FileWriter(taxRegistrationNumber + "_LOG.txt"));
    outputStream.println("Name: "       + manager.getTaxpayerFullName(taxRegistrationNumber));
    outputStream.println("AFM: "        + taxRegistrationNumber);
    outputStream.println("Income: "     + manager.getTaxpayerIncome(taxRegistrationNumber));
    outputStream.println("Basic Tax: "  + manager.getTaxpayerBasicTax(taxRegistrationNumber));
    writeTaxpayerVariationTaxOnReceipts(taxRegistrationNumber, outputStream);
    writeTaxpayerTotalTaxAndTotalReceipts(taxRegistrationNumber, outputStream);
    writeReceiptsAmountPerKind(taxRegistrationNumber, kindHashMap, outputStream);
  }

  @Override
  public void writeTaxpayerVariationTaxOnReceipts(int taxRegistrationNumber, PrintWriter outputStream) {
    if (manager.getTaxpayerVariationTaxOnReceipts(taxRegistrationNumber) > 0) 
        outputStream.println("Tax Increase: " + manager.getTaxpayerVariationTaxOnReceipts(taxRegistrationNumber));
    else 
        outputStream.println("Tax Decrease: " + manager.getTaxpayerVariationTaxOnReceipts(taxRegistrationNumber));
  }

  @Override
  public void writeTaxpayerTotalTaxAndTotalReceipts(int taxRegistrationNumber, PrintWriter outputStream) {
    outputStream.println("Total Tax: "             + manager.getTaxpayerTotalTax(taxRegistrationNumber));
    outputStream.println("TotalReceiptsGathered: " + manager.getTaxpayerTotalReceipts(taxRegistrationNumber));
  }

  @Override
  public void writeReceiptsAmountPerKind(int taxRegistrationNumber, HashMap<String, Integer> kindHashMap, PrintWriter outputStream) {
    outputStream.println("Entertainment: " + calculateReceiptAmountForGivenKind(taxRegistrationNumber, kindHashMap, "ENTERTAINMENT"));
    outputStream.println("Basic: "         + calculateReceiptAmountForGivenKind(taxRegistrationNumber, kindHashMap, "BASIC"));
    outputStream.println("Travel: "        + calculateReceiptAmountForGivenKind(taxRegistrationNumber, kindHashMap, "TRAVEL"));
    outputStream.println("Health: "        + calculateReceiptAmountForGivenKind(taxRegistrationNumber, kindHashMap, "HEALTH"));
    outputStream.println("Other: "         + calculateReceiptAmountForGivenKind(taxRegistrationNumber, kindHashMap, "OTHER"));
    outputStream.close();
  }

  @Override
  public double calculateReceiptAmountForGivenKind(int taxRegistrationNumber, HashMap<String, Integer> kindHashMap, String kind) {
    return manager.getTaxpayerReceiptAmountPerKind(taxRegistrationNumber, kindHashMap.get(kind));
  }
}
