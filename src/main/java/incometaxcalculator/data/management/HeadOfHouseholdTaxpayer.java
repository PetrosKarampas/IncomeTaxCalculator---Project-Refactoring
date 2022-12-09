package incometaxcalculator.data.management;

public class HeadOfHouseholdTaxpayer extends Taxpayer
{
  public HeadOfHouseholdTaxpayer(String fullName, int taxRegistrationNumber, float income)
  {
    super(fullName, taxRegistrationNumber, income);
  }
  @Override
  public double calculateBasicTax()
  {
    for(int i=0; i<limitIncomesForHOHTaxpayer.length; i++)
    {
      if(income < limitIncomesForHOHTaxpayer[i] )
        return  taxPercentagesForHOHTaxpayer[0] * income;
      else if(income < limitIncomesForHOHTaxpayer[i])
        return getBasicTax(i);
    }
    return -1;
  }

  @Override
  public double getBasicTax(int index)
  {
    return constantsForHOHTaxpayer[index] + (taxPercentagesForHOHTaxpayer[index]*(income - limitIncomesForHOHTaxpayer[index -1]));
  }
}
