package incometaxcalculator.data.management;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import incometaxcalculator.data.io.*;
import incometaxcalculator.exceptions.*;

public class TaxpayerManager
{
  private static final HashMap<Integer, Taxpayer> taxpayerHashMap = new HashMap<>(0);
  private static final HashMap<Integer, Integer> receiptOwnerTRNHashMap = new HashMap<>(0);

  public TaxpayerManager() {}

  public void createTaxpayer(String status, String fullName, int taxRegistrationNumber, float income) throws WrongTaxpayerStatusException
  {
    TaxpayerFactory taxpayerFactory = new TaxpayerFactory();
    Taxpayer taxpayer = taxpayerFactory.createTaxpayerItem(status, fullName, taxRegistrationNumber, income);
    taxpayerHashMap.put(taxRegistrationNumber, taxpayer);
  }
  public void createReceipt(int receiptId, String issueDate, float amount, String kind,
                            String companyName, String country, String city, String street, int number,
                            int taxRegistrationNumber) throws WrongReceiptDateException
  {
    Company company = new Company(companyName, country, city, street, number);
    Receipt receipt = new Receipt(receiptId, issueDate, amount, kind, company);
    taxpayerHashMap.get(taxRegistrationNumber).addReceiptToTaxpayerHashMap(receipt);
    receiptOwnerTRNHashMap.put(receiptId, taxRegistrationNumber);
  }
  public void removeTaxpayer(int taxRegistrationNumber) throws WrongTaxRegistrationNumberException
  {
    TaxpayerManager taxpayerManager = new TaxpayerManager();
    Taxpayer taxpayer = taxpayerHashMap.get(taxRegistrationNumber);

    if(taxpayerManager.containsTaxpayer(taxRegistrationNumber))
      taxpayerHashMap.remove(taxRegistrationNumber);
    else
      throw new WrongTaxRegistrationNumberException();

    HashMap<Integer, Receipt> receiptsHashMap = taxpayer.getReceiptHashMap();
    for (HashMap.Entry<Integer, Receipt> entry : receiptsHashMap.entrySet())
    {
      Receipt receipt = entry.getValue();
      receiptOwnerTRNHashMap.remove(receipt.getReceiptId());
    }
  }

  public void addReceipt
          (
          int receiptId, String issueDate, float amount, String kind, String companyName,
          String country, String city, String street, int number, int taxRegistrationNumber
          )
          throws IOException, WrongReceiptDateException, ReceiptAlreadyExistsException, WrongTaxpayerStatusException
  {
    if (containsReceipt(receiptId))
      throw new ReceiptAlreadyExistsException();
    createReceipt(receiptId, issueDate, amount, kind, companyName, country, city, street, number, taxRegistrationNumber);
    updateFiles(taxRegistrationNumber);
  }

  public void removeReceipt(int receiptId) throws IOException, WrongTaxpayerStatusException
  {
      taxpayerHashMap.get(receiptOwnerTRNHashMap.get(receiptId)).removeReceiptFromTaxpayerHashMap(receiptId);
      updateFiles(receiptOwnerTRNHashMap.get(receiptId));
      receiptOwnerTRNHashMap.remove(receiptId);
  }

  public void updateFiles(int taxRegistrationNumber) throws IOException, WrongTaxpayerStatusException
  {
    FileFactory fileFactory = new FileFactory();
    FileWriterFactory fileWriterFactory = new FileWriterFactory();

    File xmlFile = fileFactory.createInfoFile("_INFO.xml", taxRegistrationNumber);
    File txtFile = fileFactory.createInfoFile("_INFO.txt", taxRegistrationNumber);

    if (xmlFile.exists())
    {
      FileWriterInterface xmlInfoWriter = fileWriterFactory.createFileWriter("_INFO.xml");
      xmlInfoWriter.generateFile(taxRegistrationNumber);
    }
    else if(txtFile.exists())
    {
      FileWriterInterface txtInfoWriter = fileWriterFactory.createFileWriter("_INFO.txt");
      txtInfoWriter.generateFile(taxRegistrationNumber);
    }
    else
      throw new IOException();
  }

  public void saveLogFile(int taxRegistrationNumber, String fileFormat) throws IOException, WrongTaxpayerStatusException
  {
    FileWriterFactory fileWriterFactory = new FileWriterFactory();
    FileWriterInterface fileWriter = fileWriterFactory.createFileWriter(fileFormat);
    fileWriter.generateFile(taxRegistrationNumber);
  }

  public void loadTaxpayer(String fileName)
          throws NumberFormatException, IOException,
          WrongTaxpayerStatusException, WrongReceiptDateException, WrongFileFormatException
  {
    FileReaderFactory fileReaderFactory = new FileReaderFactory();
    String[] fileNameValues = fileName.split("\\.");
    String fileFormat = fileNameValues[1];
    FileReader fileReader = fileReaderFactory.createFileReader(fileFormat);
    fileReader.readFile(fileName);
  }

  public String getTaxpayerStatus(int taxRegistrationNumber) throws WrongTaxpayerStatusException
  {
    Taxpayer taxpayer = taxpayerHashMap.get(taxRegistrationNumber);

    if (taxpayer instanceof MarriedFilingJointlyTaxpayer)
      return "Married Filing Jointly";
    else if (taxpayer instanceof MarriedFilingSeparatelyTaxpayer)
      return "Married Filing Separately";
    else if(taxpayer instanceof SingleTaxpayer)
      return "Single";
    else if(taxpayer instanceof HeadOfHouseholdTaxpayer)
      return "Head of Household";
    else
      throw new WrongTaxpayerStatusException();
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

  public boolean containsReceipt(int id)
  {
    return receiptOwnerTRNHashMap.containsKey(id);
  }

  public Taxpayer getTaxpayer(int taxRegistrationNumber)
  {
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

  public double getTaxpayerReceiptAmountPerKind(int taxRegistrationNumber, int kind)
  {
    return taxpayerHashMap.get(taxRegistrationNumber).getReceiptsAmountPerKind(kind);
  }

  public double getTaxpayerTotalTax(int taxRegistrationNumber)
  {
    return taxpayerHashMap.get(taxRegistrationNumber).calculateTotalTax();
  }

  public double getTaxpayerBasicTax(int taxRegistrationNumber)
  {
    return taxpayerHashMap.get(taxRegistrationNumber).calculateBasicTax();
  }

  public HashMap<Integer, Receipt> getReceiptHashMap(int taxRegistrationNumber)
  {
    return taxpayerHashMap.get(taxRegistrationNumber).getReceiptHashMap();
  }

}