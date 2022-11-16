package incometaxcalculator.data.io;

import java.io.BufferedReader;
import java.io.IOException;
import incometaxcalculator.data.management.TaxpayerManager;
import incometaxcalculator.exceptions.WrongReceiptDateException;
import incometaxcalculator.exceptions.WrongFileFormatException;
import incometaxcalculator.exceptions.WrongTaxpayerStatusException;

public abstract class FileReader /*implements FileReaderInterface*/{

  //protected abstract int getReceiptID(String[] values);
 // protected abstract String getLastValueFromLine(String fieldsLine) throws WrongFileFormatException;

  protected abstract int getReceiptID(BufferedReader inputStream) throws NumberFormatException, IOException;

  protected abstract String getValueOfField(String fieldsLine) throws WrongFileFormatException;

  /*public int checkForReceipt(@NotNull BufferedReader inputStream) throws NumberFormatException, IOException
  {
   String line;
    while(!isEmpty(line = inputStream.readLine())) {
      System.out.println("OK3");
      String[] values = line.split(" ", 3);
      return getReceiptID(values);
    }
    return -1;
  }

  public String getValueOfField(String fieldsLine) throws WrongFileFormatException
  {
    if (isEmpty(fieldsLine))
      throw new WrongFileFormatException();
    System.out.println("OK4");

    return getLastValueFromLine(fieldsLine);
  }*/

  public void readFile(String fileName) throws NumberFormatException, IOException, WrongTaxpayerStatusException,
      WrongFileFormatException, WrongReceiptDateException
  {
    TaxpayerManager taxpayerManager = new TaxpayerManager();
    BufferedReader inputStream      = new BufferedReader(new java.io.FileReader(fileName));
    String fullName = getValueOfField(inputStream.readLine());
    int taxRegistrationNumber = Integer.parseInt(getValueOfField(inputStream.readLine()));
    String status = getValueOfField(inputStream.readLine());
    float income = Float.parseFloat(getValueOfField(inputStream.readLine()));
    taxpayerManager.createTaxpayer(fullName, taxRegistrationNumber, status, income);
    while (readReceipt(inputStream, taxRegistrationNumber));
  }
  protected boolean readReceipt(BufferedReader inputStream, int taxRegistrationNumber) throws WrongFileFormatException,
          IOException, WrongReceiptDateException
  {
    TaxpayerManager taxpayerManager = new TaxpayerManager();
    int receiptID;
    if ((receiptID = getReceiptID(inputStream)) < 0)
      return false;

    String issueDate    = getValueOfField(inputStream.readLine());
    String kind         = getValueOfField(inputStream.readLine());
    float amount        = Float.parseFloat(getValueOfField(inputStream.readLine()));
    String companyName  = getValueOfField(inputStream.readLine());
    String country      = getValueOfField(inputStream.readLine());
    String city         = getValueOfField(inputStream.readLine());
    String street       = getValueOfField(inputStream.readLine());
    int number          = Integer.parseInt(getValueOfField(inputStream.readLine()));
    taxpayerManager.createReceipt(
            receiptID, issueDate, amount, kind, companyName,
            country, city, street, number, taxRegistrationNumber
    );
    return true;
  }
  protected boolean isEmpty(String line) {
    return line == null;
  }

}