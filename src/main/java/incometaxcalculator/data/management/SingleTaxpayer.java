package incometaxcalculator.data.management;

public class SingleTaxpayer extends Taxpayer {

  public SingleTaxpayer(String fullName, int taxRegistrationNumber, float income) {
    super(fullName, taxRegistrationNumber, income);
  }

  public double calculateBasicTax() {

//    int[] limitIncomes = {24680, 81080, 90000, 152540, Integer.MAX_VALUE};
//    float[] taxPercentages = {0.0535f, 0.0705f, 0.0785f, 0.0785f, 0.0985f};
//    float[] constants = {0f, 1320.38f, 5296.58f, 5996.8f, 10906.19f};
//    float basicTax = 0f;
//
//    for(int i=0; i < limitIncomes.length; i++)
//    {
//      if(income < limitIncomes[i] && i == 0)
//        basicTax = taxPercentages[0]*income;
//      else if(income < limitIncomes[i]) {
//        float reducedIncome = income - limitIncomes[i-1];
//        basicTax = constants[i] + taxPercentages[i]*reducedIncome;
//      }
//    }
//    return basicTax;

    if (income < TaxConsts.S_LIMIT_1) {
      return TaxConsts.S_MULTIPLIER1 * income;
    }
    else if (income < TaxConsts.S_LIMIT_2) {
      return TaxConsts.S_AGGREGATOR1 + TaxConsts.S_MULTIPLIER2 * (income - TaxConsts.S_LIMIT_1);
    }
    else if (income < TaxConsts.MFJ_LIMIT_3) {
      return TaxConsts.S_AGGREGATOR2 + TaxConsts.S_MULTIPLIER3 * (income - TaxConsts.S_LIMIT_3);
    }
    else if (income < TaxConsts.MFJ_LIMIT_4) {
      return TaxConsts.S_AGGREGATOR3 + TaxConsts.S_MULTIPLIER4 * (income - TaxConsts.S_LIMIT_4);
    }
    else {
      return TaxConsts.S_AGGREGATOR4 + TaxConsts.S_MULTIPLIER5 * (income - TaxConsts.S_LIMIT_4);
    }
  }
}