package incometaxcalculator.data.io;

import incometaxcalculator.exceptions.WrongTaxpayerStatusException;

import java.io.IOException;

public interface FileWriterInterface
{
    void generateFile(int taxRegistrationNumber) throws IOException, WrongTaxpayerStatusException;
}
