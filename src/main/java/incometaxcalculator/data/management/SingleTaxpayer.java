package incometaxcalculator.data.management;

public class SingleTaxpayer extends Taxpayer {

  public SingleTaxpayer(String fullName, int taxRegistrationNumber, float income) {
    super(fullName, taxRegistrationNumber, income);
  }

  public double calculateBasicTax() {
    int[] limitIncomes = {24680, 81080, 90000, 152540, Integer.MAX_VALUE};
    float[] taxPercentages = {0.0535f, 0.0705f, 0.0785f, 0.0785f, 0.0985f};
    float[] constants = {0f, 1320.38f, 5296.58f, 5996.8f, 10906.19f};
    float basicTax = 0f;

    for(int i=0; i < limitIncomes.length; i++)
    {
      if(income < limitIncomes[i] && i == 0)
        basicTax = taxPercentages[0]*income;
      else if(income < limitIncomes[i]) {
        float reducedIncome = income - limitIncomes[i-1];
        basicTax = constants[i] + taxPercentages[i]*reducedIncome;
      }
    }
    return basicTax;
  }
}