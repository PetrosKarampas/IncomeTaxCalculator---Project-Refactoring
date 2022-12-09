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
    for(int i=0; i<limitIncomesForMFSTaxpayer.length; i++)
    {
      if(income < limitIncomesForMFSTaxpayer[i] && i == 0)
        return  taxPercentagesForMFSTaxpayer[0] * income;
      else if(income < limitIncomesForMFSTaxpayer[i])
        return getBasicTax(i);
      }
    return -1;

  }

  @Override
  public double getBasicTax(int index)
  {
    return constantsForMFSTaxpayer[index] + (taxPercentagesForMFSTaxpayer[index]*(income - limitIncomesForMFSTaxpayer[index -1]));
  }
}