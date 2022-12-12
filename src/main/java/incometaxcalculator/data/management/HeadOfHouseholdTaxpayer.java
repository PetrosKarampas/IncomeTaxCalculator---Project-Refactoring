package incometaxcalculator.data.management;

public class HeadOfHouseholdTaxpayer extends Taxpayer {
  private final double[] limitIncomesForHOHTaxpayer = {30390.0, 90000.0, 122110.0, 203390.0, Double.MAX_VALUE};
  private final double[] incomeDecreaseForHOHTaxpayer = {0.0, 30390.0, 90000.0, 122110.0, 203390.0};
  private final double[] taxPercentagesForHOHTaxpayer = {0.0535, 0.0705, 0.0705, 0.0785, 0.0985};
  private final double[] constantsForHOHTaxpayer = {1.0, 1625.87, 5828.38, 8092.13, 14472.61};

  public HeadOfHouseholdTaxpayer(String fullName, int taxRegistrationNumber, float income) {
    super(fullName, taxRegistrationNumber, income);
  }

  @Override
  public double calculateBasicTax() {
    return calculateBasicTax(limitIncomesForHOHTaxpayer, incomeDecreaseForHOHTaxpayer, taxPercentagesForHOHTaxpayer, constantsForHOHTaxpayer);
  }
}
