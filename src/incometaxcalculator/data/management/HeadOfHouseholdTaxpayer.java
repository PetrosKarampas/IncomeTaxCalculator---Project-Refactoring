package incometaxcalculator.data.management;

public class HeadOfHouseholdTaxpayer extends Taxpayer {

  public HeadOfHouseholdTaxpayer(String fullName, int taxRegistrationNumber, float income) {
    super(fullName, taxRegistrationNumber, income);
  }

  public double calculateBasicTax() {
    int[]   limitIncomesForHeadOfHousehold = {30390, 90000, 122110, 203390};
    float[] taxPercentagesForHeadOfHousehold = {0.0535f, 0.0705f, 0.0705f, 0.0785f, 0.0985f};
    float[] constantsForHeadOfHousehold = {1.0f, 1625.87f, 5828.38f, 8092.13f, 14472.61f};
    float headOfHouseholdBasicTax = 0f;

    for(int i=0; i<limitIncomesForHeadOfHousehold.length; i++) {
      if(income < limitIncomesForHeadOfHousehold[i] && i== 0)
        headOfHouseholdBasicTax = taxPercentagesForHeadOfHousehold[0]*income;
      else {
        float reducedIncome = income-limitIncomesForHeadOfHousehold[i-1];
        headOfHouseholdBasicTax = constantsForHeadOfHousehold[i] + taxPercentagesForHeadOfHousehold[i]*reducedIncome;
      }
    }
    /*if (income < 30390) {
      return 0.0535 * income;
    } else if (income < 90000) {
      return 1625.87 + 0.0705 * (income - 30390);
    } else if (income < 122110) {
      return 5828.38 + 0.0705 * (income - 90000);
    } else if (income < 203390) {
      return 8092.13 + 0.0785 * (income - 122110);
    } else {
      return 14472.61 + 0.0985 * (income - 203390);
    }*/
    return headOfHouseholdBasicTax;
  }
}
