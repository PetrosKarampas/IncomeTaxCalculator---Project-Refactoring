package incometaxcalculator.data.management;
import java.util.HashMap;

public abstract class Taxpayer {
  private String fullName;
  private final int taxRegistrationNumber;
  private final double[] receiptsAmountPerKind = new double[5];
  private int totalReceipts = 0;
  protected double income;
  private final HashMap<Integer, Receipt> receiptHashMap = new HashMap<>(0);
  private final HashMap<String, Integer> receiptKindAndIndexHashMap = new HashMap<>();

  public double calculateBasicTax(double[] incomeLimits, double[] incomeDecrease, double[]taxPercentages, double[] constants) {
    double basicTax = 0f;
    for (int i = 0; i < incomeLimits.length; i++) {
      if (income < incomeLimits[i]) {
        basicTax = constants[i] + taxPercentages[i] * (income - incomeDecrease[i]);
        break;
      }
    }
    return basicTax;
  }

  public abstract double calculateBasicTax();

  public Taxpayer(String fullName, int taxRegistrationNumber, float income ) {
    this.fullName = fullName;
    this.taxRegistrationNumber = taxRegistrationNumber;
    this.income = income;
  }

  public void initializeReceiptKindAndIndexIntoArrayHashMap() {
    receiptKindAndIndexHashMap.put("Entertainment", 0);
    receiptKindAndIndexHashMap.put("Basic", 1);
    receiptKindAndIndexHashMap.put("Travel", 2);
    receiptKindAndIndexHashMap.put("Health", 3);
    receiptKindAndIndexHashMap.put("Other", 4);
  }

  public void addReceiptToTaxpayerHashMap(Receipt receipt) {
    initializeReceiptKindAndIndexIntoArrayHashMap();

    for(String receiptKind: receiptKindAndIndexHashMap.keySet()) {
      if(receipt.getKind().equals(receiptKind)) {
        int index = receiptKindAndIndexHashMap.get(receiptKind);
        receiptsAmountPerKind[index] += receipt.getAmount();
        receiptHashMap.put(receipt.getReceiptId(), receipt);
        totalReceipts++;
      }
    }
  }

  public void removeReceiptFromTaxpayerHashMap(int receiptId) {
    Receipt receipt = receiptHashMap.get(receiptId);

    for(String receiptKind: receiptKindAndIndexHashMap.keySet()) {
      if(receipt.getKind().equals(receiptKind)) {
        int index = receiptKindAndIndexHashMap.get(receiptKind);
        receiptsAmountPerKind[index] -= receipt.getAmount();
        receiptHashMap.remove(receiptId);
        totalReceipts--;
      }
    }
  }

  public double getVariationTaxOnReceipts() {
    double[] limitIncomesArray = {0.2*income, 0.4*income, 0.6*income, income};
    double[] extraTaxFactorsArray = {0.08, 0.04, -0.15, -0.3};

    for(int i=0; i < limitIncomesArray.length; i++) {
      if (calculateReceiptsTotalAmount() < limitIncomesArray[i])
        return calculateBasicTax() * extraTaxFactorsArray[i];
    }
    return -1;
  }

  public double calculateReceiptsTotalAmount() {
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