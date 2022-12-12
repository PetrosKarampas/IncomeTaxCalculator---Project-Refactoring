package incometaxcalculator.data.management;

public class MarriedFilingJointlyTaxpayer extends Taxpayer {
  protected final double[] limitIncomesForMFJTaxpayer = {36080.0, 90000.0, 143350.0, 254240.0, Double.MAX_VALUE};
  protected final double[] incomeDecreaseForMFJTaxpayer = {0, 36080.0, 90000.0, 143350.0, 254240.0};
  protected final double[] taxPercentagesForMFJTaxpayer = {0.0535, 0.0705, 0.0705, 0.0785, 0.0985};
  protected final double[] constantsForMFJTaxpayer = {1.0, 1930.28, 5731.64, 9492.82, 18197.69};

  public MarriedFilingJointlyTaxpayer(String fullName, int taxRegistrationNumber, float income) {
    super(fullName, taxRegistrationNumber, income);
  }

  public double calculateBasicTax() {
    return calculateBasicTax(limitIncomesForMFJTaxpayer, incomeDecreaseForMFJTaxpayer, taxPercentagesForMFJTaxpayer, constantsForMFJTaxpayer);
  }

}