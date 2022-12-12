package incometaxcalculator.data.management;

public class MarriedFilingSeparatelyTaxpayer extends Taxpayer {
  private final double[] limitIncomesForMFSTaxpayer = {18040.0 , 71680.0, 90000.0, 127120.0, Double.MAX_VALUE};
  private final double[] incomeDecreaseForMFSTaxpayer = {0, 18040.0 , 71680.0, 90000.0, 127120.0};
  private final double[] taxPercentagesForMFSTaxpayer = {0.0535, 0.0705, 0.0785, 0.0785, 0.0985};
  private final double[] constantsForMFSTaxpayer =  {1.0, 965.14, 4746.76, 6184.88, 9098.8};

  public MarriedFilingSeparatelyTaxpayer(String fullName, int taxRegistrationNumber, float income) {
    super(fullName, taxRegistrationNumber, income);
  }

  @Override
  public double calculateBasicTax() {
    return calculateBasicTax(limitIncomesForMFSTaxpayer, incomeDecreaseForMFSTaxpayer, taxPercentagesForMFSTaxpayer, constantsForMFSTaxpayer);
  }

}