package incometaxcalculator.data.management;

public class MarriedFilingSeparatelyTaxpayer extends Taxpayer {

  public MarriedFilingSeparatelyTaxpayer(String fullName, int taxRegistrationNumber, float income) {
    super(fullName, taxRegistrationNumber, income);
  }

  public double calculateBasicTax() {

    int[]   limitIncomesForMarriedFilingSeparately = {18040 , 71680, 90000, 127120};
    float[] taxPercentagesForMarriedFilingSeparately = {0.0535f, 0.0705f, 0.0785f, 0.0785f, 0.0985f};
    float[] constantsForMarriedFilingSeparately = {1.0f, 965.14f, 4746.76f, 6184.88f, 9098.8f};
    float marriedFilingSeparatelyBasicTax = 0f;

    for(int i=0; i<limitIncomesForMarriedFilingSeparately.length; i++) {
      if(income < limitIncomesForMarriedFilingSeparately[i] && i== 0)
        marriedFilingSeparatelyBasicTax = taxPercentagesForMarriedFilingSeparately[0]*income;
      else {
        float reducedIncome = income-limitIncomesForMarriedFilingSeparately[i-1];
        marriedFilingSeparatelyBasicTax = constantsForMarriedFilingSeparately[i] + taxPercentagesForMarriedFilingSeparately[i]*reducedIncome;
      }
      /*
       * if (income < 18040) { return 0.0535 * income; } else if (income < 71680) { return 965.14 +
       * 0.0705 * (income - 18040); } else if (income < 90000) { return 4746.76 + 0.0785 * (income -
       * 71680); } else if (income < 127120) { return 6184.88 + 0.0785 * (income - 90000); } else {
       * return 9098.80 + 0.0985 * (income - 127120); }
       */
    }
    return marriedFilingSeparatelyBasicTax;
  }
}