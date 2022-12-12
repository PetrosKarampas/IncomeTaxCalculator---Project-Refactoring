package incometaxcalculator.data.io;

import java.io.BufferedReader;
import java.io.IOException;
import incometaxcalculator.data.management.TaxpayerManager;
import incometaxcalculator.exceptions.WrongReceiptDateException;
import incometaxcalculator.exceptions.WrongFileFormatException;
import incometaxcalculator.exceptions.WrongTaxpayerStatusException;

public abstract class FileReader implements FileReaderInterface {
  private final TaxpayerManager taxpayerManager = new TaxpayerManager();
  protected abstract int getReceiptID(String[] values);
  protected abstract String getLastValueFromLine(String fieldsLine) throws WrongFileFormatException;

  @Override
  public int checkForReceipt(BufferedReader inputStream) throws NumberFormatException, IOException {
   String line;
   int receiptID;
   while(!isEmpty(line = inputStream.readLine())) {
     String[] lineValues = line.split(" ", 3);
     receiptID = getReceiptID(lineValues);
     if (receiptID == -1)
      continue;
     else
       return receiptID;
    }
    return -1;
  }

  @Override
  public String getValueOfField(String fieldsLine) throws WrongFileFormatException {
    if (isEmpty(fieldsLine))
      throw new WrongFileFormatException();
    return getLastValueFromLine(fieldsLine);
  }

  public void readFile(String fileName) throws NumberFormatException, IOException, WrongTaxpayerStatusException,
      WrongFileFormatException, WrongReceiptDateException {
    BufferedReader inputStream      = new BufferedReader(new java.io.FileReader(fileName));
    String fullName = getValueOfField(inputStream.readLine());
    int taxRegistrationNumber = Integer.parseInt(getValueOfField(inputStream.readLine()));
    String status = getValueOfField(inputStream.readLine());
    float income = Float.parseFloat(getValueOfField(inputStream.readLine()));
    taxpayerManager.createTaxpayer(status, fullName, taxRegistrationNumber, income);
    while (readReceipt(inputStream, taxRegistrationNumber));
  }

  protected boolean readReceipt(BufferedReader inputStream, int taxRegistrationNumber) throws WrongFileFormatException, IOException, WrongReceiptDateException {
    int receiptID;
    if ((receiptID = checkForReceipt(inputStream)) == -1)
      return false;
    readReceiptFromFile(inputStream, taxRegistrationNumber, receiptID);
    return true;
  }

  private void readReceiptFromFile(BufferedReader inputStream, int taxRegistrationNumber, int receiptID) throws IOException, WrongFileFormatException, WrongReceiptDateException {
    String issueDate    = getValueOfField(inputStream.readLine());
    String kind         = getValueOfField(inputStream.readLine());
    float amount        = Float.parseFloat(getValueOfField(inputStream.readLine()));
    String companyName  = getValueOfField(inputStream.readLine());
    String country      = getValueOfField(inputStream.readLine());
    String city         = getValueOfField(inputStream.readLine());
    String street       = getValueOfField(inputStream.readLine());
    int number          = Integer.parseInt(getValueOfField(inputStream.readLine()));
    taxpayerManager.createReceipt(receiptID, issueDate, amount, kind, companyName,
            country, city, street, number, taxRegistrationNumber);
  }

  protected boolean isEmpty(String line) {
    return line == null;
  }

}