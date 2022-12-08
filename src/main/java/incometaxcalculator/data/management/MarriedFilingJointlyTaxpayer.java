package incometaxcalculator.data.management;

public class MarriedFilingJointlyTaxpayer extends Taxpayer {
  public MarriedFilingJointlyTaxpayer(String fullName, int taxRegistrationNumber, float income) {

    super(fullName, taxRegistrationNumber, income);

  }

  public double calculateBasicTax() {

//    int[] limitIncomes = {36080, 90000, 143350, 254240, Integer.MAX_VALUE};
//    float[] taxPercentages = {0.0535f, 0.0705f, 0.0705f, 0.0785f, 0.0985f};
//    float[] constants = {0f, 1930.28f, 5731.64f, 9492.82f, 18197.69f};
//    float basicTax = 0f;
//
//    for(int i=0; i<limitIncomes.length; i++) {
//      if(income < limitIncomes[i] && i == 0) {
//        basicTax = taxPercentages[0] * income;
//      }
//      else if(income < limitIncomes[i]) {
//        float reducedIncome = income - limitIncomes[i-1];
//        basicTax =  constants[i] + taxPercentages[i]*reducedIncome;
//      }
//    }
//    return basicTax;

    if (income < TaxConsts.MFJ_LIMIT_1) {
      return TaxConsts.HOH_MULTIPLIER1 * income;
    }
    else if (income < TaxConsts.MFJ_LIMIT_2) {
      return TaxConsts.MFJ_AGGREGATOR1 + TaxConsts.MFJ_MULTIPLIER2 * (income - TaxConsts.MFJ_LIMIT_1);
    }
    else if (income < TaxConsts.MFJ_LIMIT_3) {
      return TaxConsts.MFJ_AGGREGATOR2 + TaxConsts.MFJ_MULTIPLIER3 * (income - TaxConsts.MFJ_LIMIT_3);
    }
    else if (income < TaxConsts.MFJ_LIMIT_4) {
      return TaxConsts.MFJ_AGGREGATOR3 + TaxConsts.MFJ_MULTIPLIER4 * (income - TaxConsts.MFJ_LIMIT_4);
    }
    else {
      return TaxConsts.MFJ_AGGREGATOR4 + TaxConsts.MFJ_MULTIPLIER5 * (income - TaxConsts.MFJ_LIMIT_4);
    }
  }
}