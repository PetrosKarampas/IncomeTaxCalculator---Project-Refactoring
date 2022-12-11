package incometaxcalculator.data.io;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
public class XMLLogWriter extends LogWriter
{
  public XMLLogWriter(){}

  protected ArrayList<String> contents = new ArrayList<>();
  @Override
  public void generateTaxpayerLogFile(int taxRegistrationNumber) throws IOException
  {
    PrintWriter outputStream = new PrintWriter(new java.io.FileWriter(taxRegistrationNumber + "_LOG.xml"));
    outputStream.println("<Name> "      + manager.getTaxpayerFullName(taxRegistrationNumber) + " </Name>");
    contents.add(manager.getTaxpayerFullName(taxRegistrationNumber));
    outputStream.println("<AFM> "       + taxRegistrationNumber + " </AFM>");
    contents.add(String.valueOf(taxRegistrationNumber));
    outputStream.println("<Income> "    + manager.getTaxpayerIncome(taxRegistrationNumber) + " </Income>");
    contents.add(manager.getTaxpayerIncome(taxRegistrationNumber));
    outputStream.println("<BasicTax> "  + manager.getTaxpayerBasicTax(taxRegistrationNumber) + " </BasicTax>");
    contents.add(String.valueOf(manager.getTaxpayerBasicTax(taxRegistrationNumber)));
    writeTaxpayerVariationTaxOnReceipts(taxRegistrationNumber, outputStream);
    writeTaxpayerTotalTaxAndTotalReceipts(taxRegistrationNumber, outputStream);
    writeReceiptsAmountPerKind(taxRegistrationNumber, kindHashMap, outputStream);
  }
  @Override
  public void writeTaxpayerVariationTaxOnReceipts(int taxRegistrationNumber, PrintWriter outputStream)
  {
    if (manager.getTaxpayerVariationTaxOnReceipts(taxRegistrationNumber) > 0)
    {
      outputStream.println("<TaxIncrease> " + manager.getTaxpayerVariationTaxOnReceipts(taxRegistrationNumber) + " </TaxIncrease>");
      contents.add(String.valueOf(manager.getTaxpayerVariationTaxOnReceipts(taxRegistrationNumber)));
    }
    else
    {
      outputStream.println("<TaxDecrease> " + manager.getTaxpayerVariationTaxOnReceipts(taxRegistrationNumber) + " </TaxDecrease>");
      contents.add(String.valueOf(manager.getTaxpayerVariationTaxOnReceipts(taxRegistrationNumber)));
    }
  }
  @Override
  public void writeTaxpayerTotalTaxAndTotalReceipts(int taxRegistrationNumber, PrintWriter outputStream)
  {
    outputStream.println("<TotalTax> "  + manager.getTaxpayerTotalTax(taxRegistrationNumber) + " </TotalTax>");
    contents.add(String.valueOf(manager.getTaxpayerTotalTax(taxRegistrationNumber)));
    outputStream.println("<Receipts> "  + manager.getTaxpayerTotalReceipts(taxRegistrationNumber) + " </Receipts>");
    contents.add(String.valueOf(manager.getTaxpayerTotalReceipts(taxRegistrationNumber)));
  }
  @Override
  public void writeReceiptsAmountPerKind(int taxRegistrationNumber, HashMap<String, Integer> kindHashMap, PrintWriter outputStream)
  {
    outputStream.println("<Entertainment> " + calculateReceiptAmountForGivenKind(taxRegistrationNumber, kindHashMap, "ENTERTAINMENT") + " </Entertainment>");
    contents.add(String.valueOf(calculateReceiptAmountForGivenKind(taxRegistrationNumber, kindHashMap, "ENTERTAINMENT")));
    outputStream.println("<Basic> "         + calculateReceiptAmountForGivenKind(taxRegistrationNumber, kindHashMap, "BASIC") + " </Basic>");
    contents.add(String.valueOf(calculateReceiptAmountForGivenKind(taxRegistrationNumber, kindHashMap, "BASIC")));
    outputStream.println("<Travel> "        + calculateReceiptAmountForGivenKind(taxRegistrationNumber, kindHashMap, "TRAVEL") + " </Travel>");
    contents.add(String.valueOf(calculateReceiptAmountForGivenKind(taxRegistrationNumber, kindHashMap, "TRAVEL")));
    outputStream.println("<Health> "        + calculateReceiptAmountForGivenKind(taxRegistrationNumber, kindHashMap, "HEALTH") + " </Health>");
    contents.add(String.valueOf(calculateReceiptAmountForGivenKind(taxRegistrationNumber, kindHashMap, "HEALTH")));
    outputStream.println("<Other> "         + calculateReceiptAmountForGivenKind(taxRegistrationNumber, kindHashMap, "OTHER") + " </Other>");
    contents.add(String.valueOf(calculateReceiptAmountForGivenKind(taxRegistrationNumber, kindHashMap, "OTHER")));
    outputStream.close();
    for(String content: contents)
      System.out.println(content);
  }
  @Override
  public double calculateReceiptAmountForGivenKind(int taxRegistrationNumber, HashMap<String, Integer> kindHashMap, String kind)
  {
    return manager.getTaxpayerReceiptAmountPerKind(taxRegistrationNumber, kindHashMap.get(kind));
  }

  public String getContents(int index)
  {
    return contents.get(index);
  }
}
