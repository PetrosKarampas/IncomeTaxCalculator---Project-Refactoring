package incometaxcalculator.data.management;
import java.util.HashMap;

public abstract class Taxpayer
{
  protected double[] limitIncomesForMFJTaxpayer = {36080.0, 90000.0, 143350.0, 254240.0, Double.MAX_VALUE};
  protected double[] taxPercentagesForMFJTaxpayer = {0.0535, 0.0705, 0.0705, 0.0785, 0.0985};
  protected double[] constantsForMFJTaxpayer = {1.0, 1930.28, 5731.64, 9492.82, 18197.69};

  protected double[] limitIncomesForMFSTaxpayer = {18040.0 , 71680.0, 90000.0, 127120.0, Double.MAX_VALUE};
  protected double[] taxPercentagesForMFSTaxpayer = {0.0535, 0.0705, 0.0785, 0.0785, 0.0985};
  protected double[] constantsForMFSTaxpayer =  {1.0, 965.14, 4746.76, 6184.88, 9098.8};

  protected double[] limitIncomesForSingleTaxpayer = {24680.0, 81080.0, 90000.0, 152540.0, Double.MAX_VALUE};
  protected double[] taxPercentagesForSingleTaxpayer = {0.0535, 0.0705, 0.0785, 0.0785, 0.0985};
  protected double[] constantsForSingleTaxpayer = {1.0, 1320.38, 5296.58, 5996.8, 10906.19};

  protected double[] limitIncomesForHOHTaxpayer = {30390.0, 90000.0, 122110.0, 203390.0, Double.MAX_VALUE};
  protected double[] taxPercentagesForHOHTaxpayer = {0.0535, 0.0705, 0.0705, 0.0785, 0.0985};
  protected double[] constantsForHOHTaxpayer = {1.0, 1625.87, 5828.38, 8092.13, 14472.61};

  private String fullName;
  private final int taxRegistrationNumber;
  private final double[] receiptsAmountPerKind = new double[5];
  private int totalReceipts = 0;

  protected double income;
  private final HashMap<Integer, Receipt> receiptHashMap = new HashMap<>(0);
  private final HashMap<String, Integer> receiptKindAndIndexHashMap = new HashMap<>();

  public abstract double calculateBasicTax();
  public abstract double getBasicTax(int index);

  public Taxpayer(String fullName, int taxRegistrationNumber, float income)
  {
    this.fullName = fullName;
    this.taxRegistrationNumber = taxRegistrationNumber;
    this.income = income;

  }

  public void initializeReceiptKindAndIndexIntoArrayHashMap()
  {
    receiptKindAndIndexHashMap.put("Entertainment", 0);
    receiptKindAndIndexHashMap.put("Basic", 1);
    receiptKindAndIndexHashMap.put("Travel", 2);
    receiptKindAndIndexHashMap.put("Health", 3);
    receiptKindAndIndexHashMap.put("Other", 4);
  }

  public void addReceiptToTaxpayerHashMap(Receipt receipt)
  {
    initializeReceiptKindAndIndexIntoArrayHashMap();
    for(String receiptKind: receiptKindAndIndexHashMap.keySet())
    {
      if(receipt.getKind().equals(receiptKind))
      {
        int index = receiptKindAndIndexHashMap.get(receiptKind);
        receiptsAmountPerKind[index] += receipt.getAmount();
        receiptHashMap.put(receipt.getReceiptId(), receipt);
        totalReceipts++;
      }
    }
  }

  public void removeReceiptFromTaxpayerHashMap(int receiptId)
  {
    Receipt receipt = receiptHashMap.get(receiptId);
    for(String receiptKind: receiptKindAndIndexHashMap.keySet())
    {
      if(receipt.getKind().equals(receiptKind))
      {
        int index = receiptKindAndIndexHashMap.get(receiptKind);
        receiptsAmountPerKind[index] -= receipt.getAmount();
        receiptHashMap.remove(receiptId);
        totalReceipts--;
      }
    }
  }

  public double getVariationTaxOnReceipts()
  {
    double[] limitIncomesArray = {0.2*income, 0.4*income, 0.6*income, income};
    double[] extraTaxFactorsArray = {0.08, 0.04, -0.15, -0.3};
    for(int i=0; i < limitIncomesArray.length; i++)
    {
      if (calculateReceiptsTotalAmount() < limitIncomesArray[i])
        return calculateBasicTax() * extraTaxFactorsArray[i];
    }
    return -1;
  }

  public double calculateReceiptsTotalAmount()
  {
    double receiptsTotalAmount = 0.0;
    for (double amount : receiptsAmountPerKind)
      receiptsTotalAmount += amount;
    return receiptsTotalAmount;
  }

  public String getFullName()
  {
    return fullName;
  }

  public int getTaxRegistrationNumber()
  {
    return taxRegistrationNumber;
  }

  public double getIncome()
  {
    return income;
  }
  public HashMap<Integer, Receipt> getReceiptHashMap() {
    return receiptHashMap;
  }

  public int getTotalReceipts() {
    return totalReceipts;
  }

  public double getReceiptsAmountPerKind(int kind) {
    return receiptsAmountPerKind[kind];
  }

  public double calculateTotalTax() {
    return calculateBasicTax() + getVariationTaxOnReceipts();
  }

  public void setIncome(double income) {
    this.income = income;
  }

  public void setFullName(String fullName) {
    this.fullName = fullName;
  }
}