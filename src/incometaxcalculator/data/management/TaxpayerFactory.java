package incometaxcalculator.data.management;

import incometaxcalculator.exceptions.WrongTaxpayerStatusException;

public class TaxpayerFactory {

    public TaxpayerFactory() {}

    public Taxpayer createTaxpayer(String status, String fullName, int taxRegistrationNumber, float income) throws WrongTaxpayerStatusException{
        return switch (status) {
            case "Married Filing Jointly"
                    -> new MarriedFilingJointlyTaxpayer(fullName, taxRegistrationNumber, income);
            case "Married Filing Separately"
                    -> new MarriedFilingSeparatelyTaxpayer(fullName, taxRegistrationNumber, income);
            case "Single"
                    -> new SingleTaxpayer(fullName, taxRegistrationNumber, income);
            case "Head of Household"
                    -> new HeadOfHouseholdTaxpayer(fullName, taxRegistrationNumber, income);
            default
                    -> throw new WrongTaxpayerStatusException();
        };
    }
}
