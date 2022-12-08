package incometaxcalculator.data.management;

import incometaxcalculator.exceptions.WrongTaxpayerStatusException;

public class TaxpayerFactory {

    public TaxpayerFactory() {}

    public Taxpayer createTaxpayer(String status, String fullName, int taxRegistrationNumber, float income) throws WrongTaxpayerStatusException{
         switch (status) {
            case "Married Filing Jointly":
                    return new MarriedFilingJointlyTaxpayer(fullName, taxRegistrationNumber, income);
            case "Married Filing Separately":
                    return new MarriedFilingSeparatelyTaxpayer(fullName, taxRegistrationNumber, income);
            case "Single":
                    return new SingleTaxpayer(fullName, taxRegistrationNumber, income);
            case "Head of Household":
                    return new HeadOfHouseholdTaxpayer(fullName, taxRegistrationNumber, income);
            default:
                    throw new WrongTaxpayerStatusException();
        }
    }
}
