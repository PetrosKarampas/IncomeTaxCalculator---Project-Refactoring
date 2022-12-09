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
    for(int i = 0; i < limitIncomesForMFJTaxpayer.length; i++)
    {
      if(income < limitIncomesForMFJTaxpayer[i] && i == 0)
        return taxPercentagesForMFJTaxpayer[0] * income;
      else if(income < limitIncomesForMFJTaxpayer[i])
        return getBasicTax(i);
    }
    return -1;
  }
  @Override
  public double getBasicTax(int index)
  {
    return constantsForMFJTaxpayer[index] + (taxPercentagesForMFJTaxpayer[index]*(income - limitIncomesForMFJTaxpayer[index -1]));
  }
}