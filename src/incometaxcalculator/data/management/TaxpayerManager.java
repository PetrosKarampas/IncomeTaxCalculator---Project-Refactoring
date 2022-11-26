package incometaxcalculator.data.management;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import incometaxcalculator.data.io.*;
import incometaxcalculator.exceptions.*;

public class TaxpayerManager {

  private static final HashMap<Integer, Taxpayer> taxpayerHashMap = new HashMap<>(0);
  private static final HashMap<Integer, Integer> receiptOwnerTRNHashMap = new HashMap<>(0);

  public TaxpayerManager() {}

  public void createTaxpayer(String fullName, int taxRegistrationNumber, String status, float income) throws WrongTaxpayerStatusException
  {
    TaxpayerFactory taxpayerFactory = new TaxpayerFactory();
    Taxpayer taxpayer = taxpayerFactory.createTaxpayer(status, fullName, taxRegistrationNumber, income);
    taxpayerHashMap.put(taxRegistrationNumber, taxpayer);
  }

  public void createReceipt(int receiptId, String issueDate, float amount, String kind,
                            String companyName, String country, String city, String street, int number,
                            int taxRegistrationNumber) throws WrongReceiptDateException
  {
    Company company = new Company(companyName, country, city, street, number);
    Receipt receipt = new Receipt(receiptId, issueDate, amount, kind, company);
    taxpayerHashMap.get(taxRegistrationNumber).addReceipt(receipt);
    receiptOwnerTRNHashMap.put(receiptId, taxRegistrationNumber);
  }

  public void removeTaxpayer(int taxRegistrationNumber) throws WrongTaxRegistrationNumberException
  {
    Taxpayer taxpayer = taxpayerHashMap.get(taxRegistrationNumber);

    if(taxpayerHashMap.containsKey(taxRegistrationNumber))
      taxpayerHashMap.remove(taxRegistrationNumber);
    else
      throw new WrongTaxRegistrationNumberException();

    HashMap<Integer, Receipt> receiptsHashMap = taxpayer.getReceiptHashMap();
    for (HashMap.Entry<Integer, Receipt> entry : receiptsHashMap.entrySet()) {
      Receipt receipt = entry.getValue();
      receiptOwnerTRNHashMap.remove(receipt.getReceiptId());
    }
  }

  public boolean addReceipt(
          int receiptId, String issueDate, float amount, String kind, String companyName,
          String country, String city, String street, int number, int taxRegistrationNumber)
          throws IOException, WrongReceiptDateException, ReceiptAlreadyExistsException, WrongFileFormatException {
    if (containsReceipt(receiptId)) {
      throw new ReceiptAlreadyExistsException();
    }
    createReceipt(receiptId, issueDate, amount, kind, companyName, country, city, street, number, taxRegistrationNumber);
    updateFiles(taxRegistrationNumber);
    return true;
  }

  public void removeReceipt(int receiptId) throws IOException, ReceiptIdDoesNotExistException, WrongFileFormatException {
    if(taxpayerHashMap.containsKey(receiptOwnerTRNHashMap.get(receiptId))){
      taxpayerHashMap.get(receiptOwnerTRNHashMap.get(receiptId)).removeReceipt(receiptId);
      updateFiles(receiptOwnerTRNHashMap.get(receiptId));
      receiptOwnerTRNHashMap.remove(receiptId);
    }else
      throw new ReceiptIdDoesNotExistException();
  }

  public void updateFiles(int taxRegistrationNumber) throws IOException, WrongFileFormatException {
    FileFactory fileFactory = new FileFactory();
    FileWriterFactory fileWriterFactory = new FileWriterFactory();

    File xmlFile = fileFactory.createInfoFile("_INFO.xml", taxRegistrationNumber);
    File txtFile = fileFactory.createInfoFile("_INFO.txt", taxRegistrationNumber);

    if (xmlFile.exists()) {
      FileWriterInterface xmlInfoWriter = fileWriterFactory.createFileWriter("_INFO.xml");
      xmlInfoWriter.generateFile(taxRegistrationNumber);
    }
    else {
      FileWriterInterface txtInfoWriter = fileWriterFactory.createFileWriter("_INFO.txt");
      txtInfoWriter.generateFile(taxRegistrationNumber);
      return;
    }

    if(txtFile.exists()){
      FileWriterInterface txtInfoWriter = fileWriterFactory.createFileWriter("_INFO.txt");
      txtInfoWriter.generateFile(taxRegistrationNumber);
    }
  }

  public boolean saveLogFile(int taxRegistrationNumber, String fileFormat) throws IOException, WrongFileFormatException
  {
    FileWriterFactory fileWriterFactory = new FileWriterFactory();
    FileWriterInterface fileWriter = fileWriterFactory.createFileWriter(fileFormat);
    fileWriter.generateFile(taxRegistrationNumber);
    return true;
  }


  public boolean loadTaxpayer(String fileName) throws NumberFormatException, IOException, WrongFileFormatException,
          WrongTaxpayerStatusException, WrongReceiptKindException, WrongReceiptDateException
  {
    FileReaderFactory fileReaderFactory = new FileReaderFactory();

    String[] values = fileName.split("\\.");
    String fileFormat = values[1];
    FileReader fileReader = fileReaderFactory.createFileReader(fileFormat);
    fileReader.readFile(fileName);
    return true;
  }

  public String getTaxpayerStatus(int taxRegistrationNumber) {
    Taxpayer classTypeOfTaxpayer = taxpayerHashMap.get(taxRegistrationNumber);

    if (classTypeOfTaxpayer instanceof MarriedFilingJointlyTaxpayer)
      return "Married Filing Jointly";
    else if (classTypeOfTaxpayer instanceof MarriedFilingSeparatelyTaxpayer)
      return "Married Filing Separately";
    else if(classTypeOfTaxpayer instanceof SingleTaxpayer)
      return "Single";
    else
      return "Head of Household";

  }
  public String getTaxpayerFullName(int taxRegistrationNumber)
  {
    return taxpayerHashMap.get(taxRegistrationNumber).getFullName();
  }
  public boolean containsTaxpayer(int taxRegistrationNumber)
  {
    return taxpayerHashMap.containsKey(taxRegistrationNumber);
  }

  public boolean isTaxpayerHashMapEmpty() {
    return taxpayerHashMap.isEmpty();
  }

  public boolean containsReceipt(int id) {
    return receiptOwnerTRNHashMap.containsKey(id);

  }

  public Taxpayer getTaxpayer(int taxRegistrationNumber) {
    return taxpayerHashMap.get(taxRegistrationNumber);
  }

  public String getTaxpayerIncome(int taxRegistrationNumber)
  {
    return "" + taxpayerHashMap.get(taxRegistrationNumber).getIncome();
  }

  public double getTaxpayerVariationTaxOnReceipts(int taxRegistrationNumber)
  {
    return taxpayerHashMap.get(taxRegistrationNumber).getVariationTaxOnReceipts();
  }

  public int getTaxpayerTotalReceipts(int taxRegistrationNumber)
  {
    return taxpayerHashMap.get(taxRegistrationNumber).getTotalReceipts();
  }

  public float getTaxpayerReceiptAmountPerKind(int taxRegistrationNumber, int kind)
  {
    return taxpayerHashMap.get(taxRegistrationNumber).getReceiptsAmountPerKind(kind);
  }

  public double getTaxpayerTotalTax(int taxRegistrationNumber)
  {
    return taxpayerHashMap.get(taxRegistrationNumber).calculateTotalTax();
  }

  public double getTaxpayerBasicTax(int taxRegistrationNumber)
  {
    return taxpayerHashMap.get(taxRegistrationNumber).getBasicTax();
  }

  public HashMap<Integer, Receipt> getReceiptHashMap(int taxRegistrationNumber)
  {
    return taxpayerHashMap.get(taxRegistrationNumber).getReceiptHashMap();
  }

  public static HashMap<Integer, Taxpayer> getTaxpayerHashMap() {
    return taxpayerHashMap;
  }
  public boolean isTaxpayerHashMapContainsReceiptID(int receiptID)
  {
    return taxpayerHashMap.containsKey(receiptOwnerTRNHashMap.get(receiptID));
  }

}