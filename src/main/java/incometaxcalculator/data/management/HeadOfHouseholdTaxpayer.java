package incometaxcalculator.data.management;

public class HeadOfHouseholdTaxpayer extends Taxpayer {

  public HeadOfHouseholdTaxpayer(String fullName, int taxRegistrationNumber, float income) {
    super(fullName, taxRegistrationNumber, income);
  }

  public double calculateBasicTax() {
//    int[]   limitIncomes = {30390, 90000, 122110, 203390, Integer.MAX_VALUE};
//    float[] taxPercentages = {0.0535f, 0.0705f, 0.0705f, 0.0785f, 0.0985f};
//    float[] constants = {0f, 1625.87f, 5828.38f, 8092.13f, 14472.61f};
//    float basicTax = 0f;
//
//    for(int i = 0; i < limitIncomes.length; i++) {
//      if(income < limitIncomes[i] && i== 0)
//        basicTax = taxPercentages[0]*income;
//      else if(income < limitIncomes[i]) {
//        float reducedIncome = income - limitIncomes[i-1];
//        basicTax = constants[i] + taxPercentages[i]*reducedIncome;
//      }
//    }
//    return basicTax;
    if (income < TaxConsts.HOH_LIMIT_1) {
      return TaxConsts.HOH_MULTIPLIER1 * income;
    }
    else if (income < TaxConsts.HOH_LIMIT_2) {
      return TaxConsts.HOH_AGGREGATOR1 + TaxConsts.HOH_MULTIPLIER2 * (income - TaxConsts.HOH_LIMIT_1);
    }
    else if (income < TaxConsts.HOH_LIMIT_3) {
      return TaxConsts.HOH_AGGREGATOR2 + TaxConsts.HOH_MULTIPLIER3 * (income - TaxConsts.HOH_LIMIT_3);
    }
    else if (income < TaxConsts.HOH_LIMIT_4) {
      return TaxConsts.HOH_AGGREGATOR3 + TaxConsts.HOH_MULTIPLIER4 * (income - TaxConsts.HOH_LIMIT_4);
    }
    else {
      return TaxConsts.HOH_AGGREGATOR4 + TaxConsts.HOH_MULTIPLIER5 * (income - TaxConsts.HOH_LIMIT_4);
    }
  }
}
