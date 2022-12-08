package incometaxcalculator.data.io;

import incometaxcalculator.data.management.TaxpayerManager;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

public class XMLLogWriter extends LogWriter {

  private final HashMap<String, Integer> kindHashMap = new HashMap<>();
  @Override
  public void generateTaxpayerFile(int taxRegistrationNumber) throws IOException
  {
    PrintWriter outputStream = new PrintWriter(new java.io.FileWriter(taxRegistrationNumber + "_LOG.xml"));
    TaxpayerManager manager = new TaxpayerManager();
    initializeKindHashMap();
    outputStream.println("<Name> "          + manager.getTaxpayerFullName(taxRegistrationNumber) + " </Name>");
    outputStream.println("<AFM> "           + taxRegistrationNumber + " </AFM>");
    outputStream.println("<Income> "        + manager.getTaxpayerIncome(taxRegistrationNumber) + " </Income>");
    outputStream.println("<BasicTax> "      + manager.getTaxpayerBasicTax(taxRegistrationNumber) + " </BasicTax>");
    if (manager.getTaxpayerVariationTaxOnReceipts(taxRegistrationNumber) > 0)
      outputStream.println("<TaxIncrease> " + manager.getTaxpayerVariationTaxOnReceipts(taxRegistrationNumber) + " </TaxIncrease>");
    else
      outputStream.println("<TaxDecrease> " + manager.getTaxpayerVariationTaxOnReceipts(taxRegistrationNumber) + " </TaxDecrease>");

    outputStream.println("<TotalTax> "      + manager.getTaxpayerTotalTax(taxRegistrationNumber) + " </TotalTax>");
    outputStream.println("<Receipts> "      + manager.getTaxpayerTotalReceipts(taxRegistrationNumber) + " </Receipts>");
    outputStream.println("<Entertainment> " + manager.getTaxpayerReceiptAmountPerKind(taxRegistrationNumber, kindHashMap.get("ENTERTAINMENT")) + " </Entertainment>");
    outputStream.println("<Basic> "         + manager.getTaxpayerReceiptAmountPerKind(taxRegistrationNumber, kindHashMap.get("BASIC")) + " </Basic>");
    outputStream.println("<Travel> "        + manager.getTaxpayerReceiptAmountPerKind(taxRegistrationNumber, kindHashMap.get("TRAVEL")) + " </Travel>");
    outputStream.println("<Health> "        + manager.getTaxpayerReceiptAmountPerKind(taxRegistrationNumber, kindHashMap.get("HEALTH")) + " </Health>");
    outputStream.println("<Other> "         + manager.getTaxpayerReceiptAmountPerKind(taxRegistrationNumber, kindHashMap.get("OTHER")) + " </Other>");
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
