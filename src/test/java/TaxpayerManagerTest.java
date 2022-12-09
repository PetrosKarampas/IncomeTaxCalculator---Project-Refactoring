package incometaxcalculator.test;

import incometaxcalculator.data.management.TaxpayerManager;
import incometaxcalculator.exceptions.*;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Test;

import static org.junit.Assert.*;

public class TaxpayerManagerTest
{
    TaxpayerManager taxpayerManager = new TaxpayerManager();
    Path txtFilepath = Paths.get(System.getProperty("user.dir")+"/123456789_INFO.txt");
    Path xmlFilePath = Paths.get(System.getProperty("user.dir")+"/123456789_INFO.txt");
    Path xlsxFilePath = Paths.get(System.getProperty("user.dir")+"123456789_INFO.xlsx");
    Path txtFileName = txtFilepath.getFileName();
    Path xmlFileName = xmlFilePath.getFileName();
    Path xlsxFileName = xlsxFilePath.getFileName();

    @Test
    public void loadTaxpayerTest1() throws WrongReceiptKindException, WrongFileFormatException, IOException, WrongTaxpayerStatusException, WrongReceiptDateException
    {
        taxpayerManager.loadTaxpayer(String.valueOf(txtFileName));
        assertTrue(taxpayerManager.containsTaxpayer(123456789));

    }

    @Test
    public void loadTaxpayerTest2() throws WrongReceiptKindException, WrongFileFormatException, IOException, WrongTaxpayerStatusException, WrongReceiptDateException
    {
        taxpayerManager.loadTaxpayer(String.valueOf(xmlFileName));
        assertTrue(taxpayerManager.containsTaxpayer(123456789));
    }

    @Test
    public void loadTaxpayerTest3() throws WrongReceiptKindException, WrongFileFormatException, IOException, WrongTaxpayerStatusException, WrongReceiptDateException
    {
        assertThrows
                (
                        WrongFileFormatException.class,
                        () -> taxpayerManager.loadTaxpayer(String.valueOf(xlsxFileName))
                );
    }

   @Test
   public void removeTaxpayerTest1() throws WrongReceiptKindException, WrongFileFormatException, IOException, WrongTaxpayerStatusException, WrongReceiptDateException, WrongTaxRegistrationNumberException
    {
        taxpayerManager.loadTaxpayer(String.valueOf(txtFileName));
        taxpayerManager.removeTaxpayer(123456789);
        assertFalse(taxpayerManager.containsTaxpayer(123456789));
    }

    @Test
    public void removeTaxpayerTest2() throws WrongReceiptKindException, WrongFileFormatException, IOException, WrongTaxpayerStatusException, WrongReceiptDateException
    {
        taxpayerManager.loadTaxpayer(String.valueOf(txtFileName));
        assertThrows(
                WrongTaxRegistrationNumberException.class,
                () -> taxpayerManager.removeTaxpayer(999999999)
                    );
    }

    @Test
    public void addReceiptTest1() throws WrongReceiptKindException, IOException, WrongReceiptDateException, ReceiptAlreadyExistsException, WrongFileFormatException, WrongTaxpayerStatusException
    {
        taxpayerManager.loadTaxpayer(String.valueOf(txtFileName));
        taxpayerManager.addReceipt
                (
                        100, "8/11/2022", 1999, "Entertainment", "TEST",
                        "Greece", "Ioannina", "Smyrnhs", 15, 123456789
                );
        assertTrue(taxpayerManager.containsReceipt(100));
    }

    @Test
    public void addReceiptTest2() throws WrongReceiptKindException, WrongFileFormatException, IOException, WrongTaxpayerStatusException, WrongReceiptDateException
    {
       taxpayerManager.loadTaxpayer(String.valueOf(txtFileName));
        assertThrows(
                ReceiptAlreadyExistsException.class,
                () -> taxpayerManager.addReceipt
                        (
                                100, "8/11/2022", 1999, "Basic", "TEST",
                                "Greece", "Ioannina", "Smyrnhs", 15, 123456789
                        ));
    }

     @Test
     public void removeReceiptTest1() throws WrongReceiptKindException, WrongFileFormatException, IOException, WrongTaxpayerStatusException, WrongReceiptDateException {
        taxpayerManager.loadTaxpayer(String.valueOf(txtFileName));
        taxpayerManager.removeReceipt(100);
        assertFalse(taxpayerManager.containsReceipt(100));
    }

    @Test
    public void removeReceiptTest2() throws WrongReceiptKindException, WrongFileFormatException, IOException, WrongTaxpayerStatusException, WrongReceiptDateException
    {
        taxpayerManager.loadTaxpayer(String.valueOf(txtFileName));
        assertThrows
        (
                WrongReceiptKindException.class,
                () -> taxpayerManager.removeReceipt(9999)
        );
    }

   /* @org.junit.jupiter.api.Test
    void saveLogFileTest1() throws WrongFileFormatException, IOException, WrongTaxpayerStatusException
    {
        assertTrue(taxpayerManager.saveLogFile(123456789, "_LOG.txt"));
    }

    @org.junit.jupiter.api.Test
    void saveLogFileTest2() throws WrongFileFormatException, IOException, WrongTaxpayerStatusException
    {
        assertTrue(taxpayerManager.saveLogFile(123456789, "_LOG.xml"));
    }

    @org.junit.jupiter.api.Test
    void saveLogFileTest3()
    {
        assertThrows
        (
                WrongFileFormatException.class,
                () -> taxpayerManager.saveLogFile(123456789, "_LOG.xlsx")
        );
    }
    */
}