package incometaxcalculator.data.io;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

import incometaxcalculator.data.management.Receipt;
import incometaxcalculator.data.management.TaxpayerManager;
import org.jetbrains.annotations.NotNull;

public class TXTInfoWriter extends InfoWriter {

  public void generateTaxpayerFile(int taxRegistrationNumber) throws IOException {
    TaxpayerManager manager = new TaxpayerManager();
    PrintWriter outputStream = new PrintWriter(new java.io.FileWriter(taxRegistrationNumber + "_INFO.txt"));
    outputStream.println("Name: "   + manager.getTaxpayerFullName(taxRegistrationNumber));
    outputStream.println("AFM: "    + taxRegistrationNumber);
    outputStream.println("Status: " + manager.getTaxpayerStatus(taxRegistrationNumber));
    outputStream.println("Income: " + manager.getTaxpayerIncome(taxRegistrationNumber));
    outputStream.println();
    outputStream.println("Receipts:");
    outputStream.println();
    generateTaxpayerReceipts(taxRegistrationNumber, outputStream);
    outputStream.close();
  }
  public void generateReceipts(@NotNull HashMap<Integer, Receipt> receiptsHashMap, PrintWriter outputStream) {
    for (HashMap.Entry<Integer, Receipt> entry : receiptsHashMap.entrySet()) {
      Receipt receipt = entry.getValue();
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
}