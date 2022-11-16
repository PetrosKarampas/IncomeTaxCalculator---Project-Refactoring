package incometaxcalculator.data.management;

public class MarriedFilingJointlyTaxpayer extends Taxpayer {


  public MarriedFilingJointlyTaxpayer(String fullName, int taxRegistrationNumber, float income) {

    super(fullName, taxRegistrationNumber, income);

  }

  public double calculateBasicTax() {

    int[]   limitIncomesForMarriedFilingJointly = {36080, 90000, 143350, 254240};
    float[] taxPercentagesForMarriedFilingJointly = {0.0535f, 0.0705f, 0.0705f, 0.0785f, 0.0985f};
    float[] constantsForMarriedFilingJointly = {1.0f, 1930.28f, 5731.64f, 9492.82f, 18197.69f};
    float marriedFilingJointlyBasicTax = 0f;

    for(int i=0; i<limitIncomesForMarriedFilingJointly.length; i++) {
      if(income<limitIncomesForMarriedFilingJointly[i] && i==0)
        marriedFilingJointlyBasicTax =  taxPercentagesForMarriedFilingJointly[0]*income;
      else {
        float reducedIncome = income-limitIncomesForMarriedFilingJointly[i-1];
        marriedFilingJointlyBasicTax =  constantsForMarriedFilingJointly[i] + taxPercentagesForMarriedFilingJointly[i]*reducedIncome;
      }
      /*
       * if (income < 36080) { return 0.0535 * income; } else if (income < 90000) { return 1930.28 +
       * 0.0705 * (income - 36080); } else if (income < 143350) { return 5731.64 + 0.0705 * (income -
       * 90000); } else if (income < 254240) { return 9492.82 + 0.0785 * (income - 143350); } else {
       * return 18197.69 + 0.0985 * (income - 254240); }
       */
    }
    return marriedFilingJointlyBasicTax;
  }
}