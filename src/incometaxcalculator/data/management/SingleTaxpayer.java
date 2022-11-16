package incometaxcalculator.data.management;

public class SingleTaxpayer extends Taxpayer {

  public SingleTaxpayer(String fullName, int taxRegistrationNumber, float income) {
    super(fullName, taxRegistrationNumber, income);
  }

  public double calculateBasicTax() {
    int[]   limitIncomesForSingle = {24680, 81080, 90000, 152540};
    float[] taxPercentagesForSingle = {0.0535f, 0.0705f, 0.0785f, 0.0785f, 0.0985f};
    float[] constantsForSingle = {1.0f, 1320.38f, 5296.58f, 5996.8f, 10906.19f};
    float singleBasicTax = 0f;

    for(int i=0; i<limitIncomesForSingle.length; i++) {

      if(income < limitIncomesForSingle[i] && i== 0)
        singleBasicTax = taxPercentagesForSingle[0]*income;
      else {
        float reducedIncome = income-limitIncomesForSingle[i-1];
        singleBasicTax = constantsForSingle[i] + taxPercentagesForSingle[i]*reducedIncome;
      }
      /*
       * if (income < 24680) { return 0.0535 * income; } else if (income < 81080) { return 1320.38 +
       * 0.0705 * (income - 24680); } else if (income < 90000) { return 5296.58 + 0.0785 * (income -
       * 81080); } else if (income < 152540) { return 5996.80 + 0.0785 * (income - 90000); } else {
       * return 10906.19 + 0.0985 * (income - 152540); }
       */
    }
    return singleBasicTax;
  }
}