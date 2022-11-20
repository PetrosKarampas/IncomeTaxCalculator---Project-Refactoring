package incometaxcalculator.data.management;

import java.util.HashMap;

//import incometaxcalculator.exceptions.WrongReceiptKindException;

public abstract class Taxpayer {

  protected float income;
  private final String fullName;
  private final int taxRegistrationNumber;
  private final float[] receiptsAmountPerKind = new float[5];
  private int totalReceipts = 0;
  private final HashMap<Integer, Receipt> receiptHashMap = new HashMap<>(0);
  private final HashMap<String, Integer> receiptKindAndIndexIntoArrayHashMap = new HashMap<>();

  public abstract double calculateBasicTax();

  protected Taxpayer(String fullName, int taxRegistrationNumber, float income) {
    this.fullName = fullName;
    this.taxRegistrationNumber = taxRegistrationNumber;
    this.income = income;
  }



  public void addReceipt(Receipt receipt) {
    initializeReceiptKindAndIndexIntoArrayHashMap();
    for(String receiptKind: receiptKindAndIndexIntoArrayHashMap.keySet()) {
      if(receipt.getKind().equals(receiptKind)) {
        int indexIntoArray = receiptKindAndIndexIntoArrayHashMap.get(receiptKind);
        receiptsAmountPerKind[indexIntoArray] += receipt.getAmount();
        receiptHashMap.put(receipt.getReceiptId(), receipt);
        totalReceipts++;
      }
    }

    /*
     * if (receipt.getKind().equals("Entertainment")) { amountPerReceiptsKind[ENTERTAINMENT] +=
     * receipt.getAmount(); } else if (receipt.getKind().equals("Basic")) {
     * amountPerReceiptsKind[BASIC] += receipt.getAmount(); } else if
     * (receipt.getKind().equals("Travel")) { amountPerReceiptsKind[TRAVEL] += receipt.getAmount();
     * } else if (receipt.getKind().equals("Health")) { amountPerReceiptsKind[HEALTH] +=
     * receipt.getAmount(); } else if (receipt.getKind().equals("Other")) {
     * amountPerReceiptsKind[OTHER] += receipt.getAmount(); } else { throw new
     * WrongReceiptKindException(); } receiptHashMap.put(receipt.getId(), receipt);
     * totalReceiptsGathered++;
     */
  }

  public void removeReceipt(int receiptId) {
    Receipt receipt = receiptHashMap.get(receiptId);
    for(String receiptKind: receiptKindAndIndexIntoArrayHashMap.keySet()) {
      if(receipt.getKind().equals(receiptKind)) {
        int indexIntoArray = receiptKindAndIndexIntoArrayHashMap.get(receiptKind);
        receiptsAmountPerKind[indexIntoArray] -= receipt.getAmount();
        receiptHashMap.remove(receiptId);
        totalReceipts--;
      }
    }
    /*
     * if (receipt.getKind().equals("Entertainment")) { receiptsAmountPerKind[ENTERTAINMENT] -=
     * receipt.getAmount(); } else if (receipt.getKind().equals("Basic")) {
     * receiptsAmountPerKind[BASIC] -= receipt.getAmount(); } else if
     * (receipt.getKind().equals("Travel")) { receiptsAmountPerKind[TRAVEL] -= receipt.getAmount();
     * } else if (receipt.getKind().equals("Health")) { receiptsAmountPerKind[HEALTH] -=
     * receipt.getAmount(); } else if (receipt.getKind().equals("Other")) {
     * receiptsAmountPerKind[OTHER] -= receipt.getAmount(); } else { throw new
     * WrongReceiptKindException(); } totalReceiptsGathered--; receiptHashMap.remove(receiptId);
     */
  }

  public void initializeReceiptKindAndIndexIntoArrayHashMap() {
    receiptKindAndIndexIntoArrayHashMap.put("Entertainment", 0);
    receiptKindAndIndexIntoArrayHashMap.put("Basic", 1);
    receiptKindAndIndexIntoArrayHashMap.put("Travel", 2);
    receiptKindAndIndexIntoArrayHashMap.put("Health", 3);
    receiptKindAndIndexIntoArrayHashMap.put("Other", 4);

  }

  public double getVariationTaxOnReceipts() {
    float[] arrayWithPercentagesPerReceiptsTotalAmount = {0.2f*income, 0.4f*income, 0.6f*income, income};
    float[] arrayWithExtraTaxFactors = {0.08f , 0.04f , 0.15f , 0.3f};
    for(int i=0; i < arrayWithPercentagesPerReceiptsTotalAmount.length; i++) {
      if (calculateReceiptsTotalAmount() < arrayWithPercentagesPerReceiptsTotalAmount[i]) {
        return calculateBasicTax() * arrayWithExtraTaxFactors[i];
      }
    }
    return -1;

    /*
     * if (getReceiptsTotalAmount() < 0.2 * income) { return calculateBasicTax() * 0.08; } else if
     * (getReceiptsTotalAmount() < 0.4 * income) { return calculateBasicTax() * 0.04; } else if
     * (getReceiptsTotalAmount() < 0.6 * income) { return -calculateBasicTax() * 0.15; } else {
     * return -calculateBasicTax() * 0.3; }
     */
  }
  public float calculateReceiptsTotalAmount() {
    int receiptsTotalAmount = 0;
    for (float amount : receiptsAmountPerKind) {
      receiptsTotalAmount += amount;
    }
    return receiptsTotalAmount;
  }
  public String getFullName() {
    return fullName;
  }

  public int getTaxRegistrationNumber() {
    return taxRegistrationNumber;
  }

  public float getIncome() {
    return income;
  }

  public HashMap<Integer, Receipt> getReceiptHashMap() {
    return receiptHashMap;
  }

  public int getTotalReceipts() {
    return totalReceipts;
  }

  public float getReceiptsAmountPerKind(int kind) {
    return receiptsAmountPerKind[kind];
  }

  public double calculateTotalTax() {
    return calculateBasicTax() + getVariationTaxOnReceipts();
  }

  public double getBasicTax() {
    return calculateBasicTax();
  }

  public void setIncome(float income) { this.income = income; }
}