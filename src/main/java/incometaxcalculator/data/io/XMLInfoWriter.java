package incometaxcalculator.data.io;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

import incometaxcalculator.data.management.Receipt;
import incometaxcalculator.exceptions.WrongTaxpayerStatusException;

public class XMLInfoWriter extends InfoWriter
{
  @Override
  public void generateTaxpayerInfo(int taxRegistrationNumber) throws WrongTaxpayerStatusException, IOException
  {
    PrintWriter outputStream = new PrintWriter(new java.io.FileWriter(taxRegistrationNumber + "_INFO.xml"));
    outputStream.println("<Name> "    + manager.getTaxpayerFullName(taxRegistrationNumber) + " </Name>");
    outputStream.println("<AFM> "     + taxRegistrationNumber + " </AFM>");
    outputStream.println("<Status> "  + manager.getTaxpayerStatus(taxRegistrationNumber) + " </Status>");
    outputStream.println("<Income> "  + manager.getTaxpayerIncome(taxRegistrationNumber) + " </Income>");
    outputStream.println();
    generateTaxpayerReceipts(taxRegistrationNumber, outputStream);
  }
  @Override
  public void generateTaxpayerReceipts(int taxRegistrationNumber, PrintWriter outputStream)
  {
    HashMap<Integer, Receipt> receiptsHashMap = manager.getReceiptHashMap(taxRegistrationNumber);
    outputStream.println("<Receipts>");
    outputStream.println();
    for (HashMap.Entry<Integer, Receipt> entry : receiptsHashMap.entrySet())
    {
      Receipt receipt = entry.getValue();
      generateReceiptID(receipt, outputStream);
    }
    outputStream.close();
  }
  @Override
  public void generateReceiptID(Receipt receipt, PrintWriter outputStream)
  {
    outputStream.println("<ReceiptID> " + receipt.getReceiptId()            + " </ReceiptID>");
    outputStream.println("<Date> "      + receipt.getIssueDate()            + " </Date>");
    outputStream.println("<Kind> "      + receipt.getKind()                 + " </Kind>");
    outputStream.println("<Amount> "    + receipt.getAmount()               + " </Amount>");
    outputStream.println("<Company> "   + receipt.getCompany().getName()    + " </Company>");
    outputStream.println("<Country> "   + receipt.getCompany().getCountry() + " </Country>");
    outputStream.println("<City> "      + receipt.getCompany().getCity()    + " </City>");
    outputStream.println("<Street> "    + receipt.getCompany().getStreet()  + " </Street>");
    outputStream.println("<Number> "    + receipt.getCompany().getNumber()  + " </Number>");
    outputStream.println();
  }
}