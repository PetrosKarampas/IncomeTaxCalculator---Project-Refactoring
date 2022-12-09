package incometaxcalculator.data.management;

public class SingleTaxpayer extends Taxpayer
{
  public SingleTaxpayer(String fullName, int taxRegistrationNumber, float income)
  {
    super(fullName, taxRegistrationNumber, income);
  }
  @Override
  public double calculateBasicTax()
  {
    for(int i=0; i<limitIncomesForSingleTaxpayer.length; i++)
    {
      if(income < limitIncomesForSingleTaxpayer[i] && i == 0)
        return  taxPercentagesForSingleTaxpayer[0] * income;
      else if(income < limitIncomesForSingleTaxpayer[i])
        return getBasicTax(i);
    }
    return -1;
  }
  @Override
  public double getBasicTax(int index)
  {
    return constantsForSingleTaxpayer[index] + (taxPercentagesForSingleTaxpayer[index]*(income - limitIncomesForSingleTaxpayer[index -1]));
  }
}