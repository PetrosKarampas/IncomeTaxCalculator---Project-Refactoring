package incometaxcalculator.data.management;

public class MarriedFilingJointlyTaxpayer extends Taxpayer
{


  public MarriedFilingJointlyTaxpayer(String fullName, int taxRegistrationNumber, float income)
  {
    super(fullName, taxRegistrationNumber, income);
  }
  @Override
  public double calculateBasicTax()
  {
    double basicTax = 0f;
    for (int i = 0; i < limitIncomesForMFJTaxpayer.length; i++) {
      if (income < limitIncomesForMFJTaxpayer[i]) {
        basicTax = constantsForMFJTaxpayer[i] + taxPercentagesForMFJTaxpayer[i] * (income - incomeDecrease[i]);
        break;
      }
    }
    return basicTax;
  }
  @Override
  public double getBasicTax(int index)
  {
    return constantsForMFJTaxpayer[index] + (taxPercentagesForMFJTaxpayer[index]*(income - limitIncomesForMFJTaxpayer[index -1]));
  }
}