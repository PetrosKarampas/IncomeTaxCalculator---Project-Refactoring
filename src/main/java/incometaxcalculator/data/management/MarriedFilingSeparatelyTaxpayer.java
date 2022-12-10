package incometaxcalculator.data.management;

public class MarriedFilingSeparatelyTaxpayer extends Taxpayer
{
  public MarriedFilingSeparatelyTaxpayer(String fullName, int taxRegistrationNumber, float income)
  {
    super(fullName, taxRegistrationNumber, income);
  }
  @Override
  public double calculateBasicTax()
  {
    double basicTax = 0f;
    for (int i = 0; i < limitIncomesForMFSTaxpayer.length; i++) {
      if (income < limitIncomesForMFSTaxpayer[i]) {
        basicTax = constantsForMFSTaxpayer[i] + taxPercentagesForMFSTaxpayer[i] * (income - incomeDecrease[i]);
        break;
      }
    }
    return basicTax;

  }

  @Override
  public double getBasicTax(int index)
  {
    return constantsForMFSTaxpayer[index] + (taxPercentagesForMFSTaxpayer[index]*(income - limitIncomesForMFSTaxpayer[index -1]));
  }
}