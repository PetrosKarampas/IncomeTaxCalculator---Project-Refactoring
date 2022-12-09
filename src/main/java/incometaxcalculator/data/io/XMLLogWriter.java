package incometaxcalculator.data.io;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
public class XMLLogWriter extends LogWriter
{
  @Override
  public void generateTaxpayerLogFile(int taxRegistrationNumber) throws IOException
  {
    PrintWriter outputStream = new PrintWriter(new java.io.FileWriter(taxRegistrationNumber + "_LOG.xml"));
    outputStream.println("<Name> "      + manager.getTaxpayerFullName(taxRegistrationNumber) + " </Name>");
    outputStream.println("<AFM> "       + taxRegistrationNumber + " </AFM>");
    outputStream.println("<Income> "    + manager.getTaxpayerIncome(taxRegistrationNumber) + " </Income>");
    outputStream.println("<BasicTax> "  + manager.getTaxpayerBasicTax(taxRegistrationNumber) + " </BasicTax>");
    writeTaxpayerVariationTaxOnReceipts(taxRegistrationNumber, outputStream);
    writeTaxpayerTotalTaxAndTotalReceipts(taxRegistrationNumber, outputStream);
    writeReceiptsAmountPerKind(taxRegistrationNumber, kindHashMap, outputStream);
  }
  @Override
  public void writeTaxpayerVariationTaxOnReceipts(int taxRegistrationNumber, PrintWriter outputStream)
  {
    if (manager.getTaxpayerVariationTaxOnReceipts(taxRegistrationNumber) > 0)
      outputStream.println("<TaxIncrease> " + manager.getTaxpayerVariationTaxOnReceipts(taxRegistrationNumber) + " </TaxIncrease>");
    else
      outputStream.println("<TaxDecrease> " + manager.getTaxpayerVariationTaxOnReceipts(taxRegistrationNumber) + " </TaxDecrease>");
  }
  @Override
  public void writeTaxpayerTotalTaxAndTotalReceipts(int taxRegistrationNumber, PrintWriter outputStream)
  {
    outputStream.println("<TotalTax> "  + manager.getTaxpayerTotalTax(taxRegistrationNumber) + " </TotalTax>");
    outputStream.println("<Receipts> "  + manager.getTaxpayerTotalReceipts(taxRegistrationNumber) + " </Receipts>");
  }
  @Override
  public void writeReceiptsAmountPerKind(int taxRegistrationNumber, HashMap<String, Integer> kindHashMap, PrintWriter outputStream)
  {
    outputStream.println("<Entertainment> " + calculateReceiptAmountForGivenKind(taxRegistrationNumber, kindHashMap, "ENTERTAINMENT") + " </Entertainment>");
    outputStream.println("<Basic> "         + calculateReceiptAmountForGivenKind(taxRegistrationNumber, kindHashMap, "BASIC") + " </Basic>");
    outputStream.println("<Travel> "        + calculateReceiptAmountForGivenKind(taxRegistrationNumber, kindHashMap, "TRAVEL") + " </Travel>");
    outputStream.println("<Health> "        + calculateReceiptAmountForGivenKind(taxRegistrationNumber, kindHashMap, "HEALTH") + " </Health>");
    outputStream.println("<Other> "         + calculateReceiptAmountForGivenKind(taxRegistrationNumber, kindHashMap, "OTHER") + " </Other>");
    outputStream.close();
  }
  @Override
  public double calculateReceiptAmountForGivenKind(int taxRegistrationNumber, HashMap<String, Integer> kindHashMap, String kind)
  {
    return manager.getTaxpayerReceiptAmountPerKind(taxRegistrationNumber, kindHashMap.get(kind));
  }
}
