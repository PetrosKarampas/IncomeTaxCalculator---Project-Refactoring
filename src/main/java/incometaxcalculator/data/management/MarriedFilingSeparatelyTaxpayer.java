package incometaxcalculator.data.management;

public class MarriedFilingSeparatelyTaxpayer extends Taxpayer {

  public MarriedFilingSeparatelyTaxpayer(String fullName, int taxRegistrationNumber, float income) {
    super(fullName, taxRegistrationNumber, income);
  }

  public double calculateBasicTax() {

    int[]   limitIncomes = {18040 , 71680, 90000, 127120, Integer.MAX_VALUE};
    float[] taxPercentages = {0.0535f, 0.0705f, 0.0785f, 0.0785f, 0.0985f};
    float[] constants = {0f, 965.14f, 4746.76f, 6184.88f, 9098.8f};
    float basicTax = 0f;

    for(int i=0; i<limitIncomes.length; i++) {
      if(income < limitIncomes[i] && i== 0)
        basicTax = taxPercentages[0]*income;
      else if(income < limitIncomes[i]) {
        float reducedIncome = income-limitIncomes[i-1];
        basicTax = constants[i] + taxPercentages[i]*reducedIncome;
      }
    }
    return basicTax;
  }
}