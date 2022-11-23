package incometaxcalculator.data.management;

public class MarriedFilingJointlyTaxpayer extends Taxpayer {
  public MarriedFilingJointlyTaxpayer(String fullName, int taxRegistrationNumber, float income) {

    super(fullName, taxRegistrationNumber, income);

  }

  public double calculateBasicTax() {

    int[]   limitIncomes = {36080, 90000, 143350, 254240, Integer.MAX_VALUE};
    float[] taxPercentages = {0.0535f, 0.0705f, 0.0705f, 0.0785f, 0.0985f};
    float[] constants = {0f, 1930.28f, 5731.64f, 9492.82f, 18197.69f};
    float basicTax = 0f;

    for(int i=0; i<limitIncomes.length; i++) {
      if(income < limitIncomes[i] && i == 0)
        basicTax =  taxPercentages[0] * income;
      else if(income < limitIncomes[i]) {
        float reducedIncome = income - limitIncomes[i-1];
        basicTax =  constants[i] + taxPercentages[i]*reducedIncome;
      }
    }
    return basicTax;
  }
}