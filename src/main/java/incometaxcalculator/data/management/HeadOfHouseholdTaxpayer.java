package incometaxcalculator.data.management;

public class HeadOfHouseholdTaxpayer extends Taxpayer {

  public HeadOfHouseholdTaxpayer(String fullName, int taxRegistrationNumber, float income) {
    super(fullName, taxRegistrationNumber, income);
  }

  public double calculateBasicTax() {
    int[]   limitIncomes = {30390, 90000, 122110, 203390, Integer.MAX_VALUE};
    float[] taxPercentages = {0.0535f, 0.0705f, 0.0705f, 0.0785f, 0.0985f};
    float[] constants = {0f, 1625.87f, 5828.38f, 8092.13f, 14472.61f};
    float basicTax = 0f;

    for(int i = 0; i < limitIncomes.length; i++) {
      if(income < limitIncomes[i] && i== 0)
        basicTax = taxPercentages[0]*income;
      else if(income < limitIncomes[i]) {
        float reducedIncome = income - limitIncomes[i-1];
        basicTax = constants[i] + taxPercentages[i]*reducedIncome;
      }
    }
    return basicTax;
  }
}
