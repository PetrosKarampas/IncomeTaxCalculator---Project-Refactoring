package incometaxcalculator.data.io;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import incometaxcalculator.data.management.Receipt;
import incometaxcalculator.exceptions.WrongTaxpayerStatusException;

public class TXTInfoWriter extends InfoWriter
{
  @Override
  public void generateTaxpayerInfo(int taxRegistrationNumber) throws WrongTaxpayerStatusException, IOException {
    PrintWriter outputStream = new PrintWriter(new java.io.FileWriter(taxRegistrationNumber + "_INFO.txt"));
    outputStream.println("Name: "   + manager.getTaxpayerFullName(taxRegistrationNumber));
    outputStream.println("AFM: "    + taxRegistrationNumber);
    outputStream.println("Status: " + manager.getTaxpayerStatus(taxRegistrationNumber));
    outputStream.println("Income: " + manager.getTaxpayerIncome(taxRegistrationNumber));
    outputStream.println();
    generateTaxpayerReceipts(taxRegistrationNumber, outputStream);
  }
  @Override
  public void generateTaxpayerReceipts(int taxRegistrationNumber, PrintWriter outputStream)
  {
    outputStream.println("Receipts:");
    outputStream.println();
    HashMap<Integer, Receipt> receiptsHashMap = manager.getReceiptHashMap(taxRegistrationNumber);
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
    outputStream.println("Receipt ID: " + receipt.getReceiptId());
    outputStream.println("Date: "       + receipt.getIssueDate());
    outputStream.println("Kind: "       + receipt.getKind());
    outputStream.println("Amount: "     + receipt.getAmount());
    outputStream.println("Company: "    + receipt.getCompany().getName());
    outputStream.println("Country: "    + receipt.getCompany().getCountry());
    outputStream.println("City: "       + receipt.getCompany().getCity());
    outputStream.println("Street: "     + receipt.getCompany().getStreet());
    outputStream.println("Number: "     + receipt.getCompany().getNumber());
    outputStream.println();
  }
}