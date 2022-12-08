package incometaxcalculator.data.io;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

import incometaxcalculator.data.management.Receipt;
import incometaxcalculator.data.management.TaxpayerManager;
import org.jetbrains.annotations.NotNull;

public class XMLInfoWriter extends InfoWriter {

  public void generateTaxpayerFile(int taxRegistrationNumber) throws IOException {
    TaxpayerManager manager = new TaxpayerManager();
    PrintWriter outputStream = new PrintWriter(new java.io.FileWriter(taxRegistrationNumber + "_INFO.xml"));
    outputStream.println("<Name> "    + manager.getTaxpayerFullName(taxRegistrationNumber) + " </Name>");
    outputStream.println("<AFM> "     + taxRegistrationNumber + " </AFM>");
    outputStream.println("<Status> "  + manager.getTaxpayerStatus(taxRegistrationNumber) + " </Status>");
    outputStream.println("<Income> "  + manager.getTaxpayerIncome(taxRegistrationNumber) + " </Income>");
    outputStream.println();
    outputStream.println("<Receipts>");
    outputStream.println();
    generateTaxpayerReceipts(taxRegistrationNumber, outputStream);
    outputStream.close();
  }

  public void generateReceipts(@NotNull HashMap<Integer, Receipt> receiptsHashMap, PrintWriter outputStream) {
    for (HashMap.Entry<Integer, Receipt> entry : receiptsHashMap.entrySet()) {
      Receipt receipt = entry.getValue();
      outputStream.println("<ReceiptID> " + receipt.getReceiptId()            + " </ReceiptID>");
      outputStream.println("<Date> "      + receipt.getIssueDate()            + " </Date>");
      outputStream.println("<Kind> "      + receipt.getKind()                 + " </Kind>");
      outputStream.println("<Amount> "    + receipt.getAmount()               + " </Amount>");
      outputStream.println("<Company> "   + receipt.getCompany().getCity()    + " </Company>");
      outputStream.println("<Country> "   + receipt.getCompany().getCountry() + " </Country>");
      outputStream.println("<City> "      + receipt.getCompany().getCity()    + " </City>");
      outputStream.println("<Street> "    + receipt.getCompany().getStreet()  + " </Street>");
      outputStream.println("<Number> "    + receipt.getCompany().getNumber()  + " </Number>");
      outputStream.println();
    }
  }
}