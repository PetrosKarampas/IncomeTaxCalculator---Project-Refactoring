package incometaxcalculator.data.management;

public class SingleTaxpayer extends Taxpayer {

  private final double[] limitIncomesForSingleTaxpayer = {24680.0, 81080.0, 90000.0, 152540.0, Double.MAX_VALUE};
  private final double[] incomeDecreaseForSingleTaxpayer = {0, 24680.0, 81080.0, 90000.0, 152540.0};
  private final double[] taxPercentagesForSingleTaxpayer = {0.0535, 0.0705, 0.0785, 0.0785, 0.0985};
  private final double[] constantsForSingleTaxpayer = {1.0, 1320.38, 5296.58, 5996.8, 10906.19};

  public SingleTaxpayer(String fullName, int taxRegistrationNumber, float income) {
    super(fullName, taxRegistrationNumber, income);
  }

  @Override
  public double calculateBasicTax() {
    return calculateBasicTax(limitIncomesForSingleTaxpayer, incomeDecreaseForSingleTaxpayer, taxPercentagesForSingleTaxpayer, constantsForSingleTaxpayer);
  }

}