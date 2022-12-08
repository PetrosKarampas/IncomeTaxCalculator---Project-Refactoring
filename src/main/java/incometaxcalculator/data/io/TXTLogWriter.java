package incometaxcalculator.data.io;

import incometaxcalculator.data.management.TaxpayerManager;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

public class TXTLogWriter extends LogWriter {

  private final HashMap<String, Integer> kindHashMap = new HashMap<>();

  @Override
  public void generateTaxpayerFile(int taxRegistrationNumber) throws IOException{
    PrintWriter outputStream = new PrintWriter(new java.io.FileWriter(taxRegistrationNumber + "_LOG.txt"));
    TaxpayerManager manager = new TaxpayerManager();
    initializeKindHashMap();
    outputStream.println("Name: "                  + manager.getTaxpayerFullName(taxRegistrationNumber));
    outputStream.println("AFM: "                   + taxRegistrationNumber);
    outputStream.println("Income: "                + manager.getTaxpayerIncome(taxRegistrationNumber));
    outputStream.println("Basic Tax: "             + manager.getTaxpayerBasicTax(taxRegistrationNumber));
    if (manager.getTaxpayerVariationTaxOnReceipts(taxRegistrationNumber) > 0)
      outputStream.println("Tax Increase: "        + manager.getTaxpayerVariationTaxOnReceipts(taxRegistrationNumber));
    else
      outputStream.println("Tax Decrease: "        + manager.getTaxpayerVariationTaxOnReceipts(taxRegistrationNumber));
    outputStream.println("Total Tax: "             + manager.getTaxpayerTotalTax(taxRegistrationNumber));
    outputStream.println("TotalReceiptsGathered: " + manager.getTaxpayerTotalReceipts(taxRegistrationNumber));
    outputStream.println("Entertainment: "         + manager.getTaxpayerReceiptAmountPerKind(taxRegistrationNumber, kindHashMap.get("ENTERTAINMENT")));
    outputStream.println("Basic: "                 + manager.getTaxpayerReceiptAmountPerKind(taxRegistrationNumber, kindHashMap.get("BASIC")));
    outputStream.println("Travel: "                + manager.getTaxpayerReceiptAmountPerKind(taxRegistrationNumber, kindHashMap.get("TRAVEL")));
    outputStream.println("Health: "                + manager.getTaxpayerReceiptAmountPerKind(taxRegistrationNumber, kindHashMap.get("HEALTH")));
    outputStream.println("Other: "                 + manager.getTaxpayerReceiptAmountPerKind(taxRegistrationNumber, kindHashMap.get("OTHER")));
    outputStream.close();
  }
  private void initializeKindHashMap()
  {
    kindHashMap.put("ENTERTAINMENT",0);
    kindHashMap.put("BASIC",1);
    kindHashMap.put("TRAVEL",2);
    kindHashMap.put("HEALTH",3);
    kindHashMap.put("OTHER",4);
  }

}
