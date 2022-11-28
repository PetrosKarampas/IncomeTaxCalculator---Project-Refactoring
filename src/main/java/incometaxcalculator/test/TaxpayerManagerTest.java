package incometaxcalculator.test;

import incometaxcalculator.data.management.TaxpayerManager;
import incometaxcalculator.exceptions.*;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.Assert.*;

class TaxpayerManagerTest {
    TaxpayerManager taxpayerManager = new TaxpayerManager();
    Path txtFilepath = Paths.get(System.getProperty("user.dir")+"/123456789_INFO.txt");
    Path xmlFilePath = Paths.get(System.getProperty("user.dir")+"/123456789_INFO.txt");
    Path xlsxFilePath = Paths.get(System.getProperty("user.dir")+"123456789_INFO.xlsx");
    Path txtFileName = txtFilepath.getFileName();
    Path xmlFileName = xmlFilePath.getFileName();
    Path xlsxFileName = xlsxFilePath.getFileName();

    @org.junit.jupiter.api.Test
    void loadTaxpayerTest1() throws WrongReceiptKindException, WrongFileFormatException, IOException, WrongTaxpayerStatusException, WrongReceiptDateException
    {
        // loadTaxpayerTest from txt file
        assertTrue(taxpayerManager.loadTaxpayer(String.valueOf(txtFileName)));

    }

    @org.junit.jupiter.api.Test
    void loadTaxpayerTest2() throws WrongReceiptKindException, WrongFileFormatException, IOException, WrongTaxpayerStatusException, WrongReceiptDateException
    {
        //loadTaxpayerTest from xml file
        assertTrue(taxpayerManager.loadTaxpayer(String.valueOf(xmlFileName)));

    }

    @org.junit.jupiter.api.Test
    void loadTaxpayerTest3()
    {
        //WrongFileExtensionException Test
        assertThrows
                (
                        WrongFileFormatException.class,
                        () -> taxpayerManager.loadTaxpayer(String.valueOf(xlsxFileName))
                );
    }

    @org.junit.jupiter.api.Test
    void removeTaxpayerTest1() throws WrongReceiptKindException, WrongFileFormatException, IOException, WrongTaxpayerStatusException, WrongReceiptDateException, WrongTaxRegistrationNumberException {
        //Remove existing taxpayer
        boolean loadTaxpayerFromTXTFile = taxpayerManager.loadTaxpayer(String.valueOf(txtFileName));
        taxpayerManager.removeTaxpayer(123456789);
        assertFalse(TaxpayerManager.getTaxpayerHashMap().containsKey(123456789));

    }

    @org.junit.jupiter.api.Test
    void removeTaxpayerTest2() throws WrongReceiptKindException, WrongFileFormatException, IOException, WrongTaxpayerStatusException, WrongReceiptDateException
    {
        //WrongTaxRegistrationNumberException test
        boolean loadTaxpayerFromTXTFile = taxpayerManager.loadTaxpayer(String.valueOf(txtFileName));
        assertThrows(
                WrongTaxRegistrationNumberException.class,
                () -> taxpayerManager.removeTaxpayer(999999999)
                    );
    }

     @org.junit.jupiter.api.Test
   void addReceiptTest1() throws WrongReceiptKindException, IOException, WrongReceiptDateException, ReceiptAlreadyExistsException, WrongFileFormatException, WrongTaxpayerStatusException
    {
        // Add non-existing receipt
        boolean loadTaxpayerFromTXTFile = taxpayerManager.loadTaxpayer(String.valueOf(txtFileName));
        assertTrue(taxpayerManager.addReceipt
                (
                        50, "8/11/2022", 1999, "Entertainment", "TEST",
                        "Greece", "Ioannina", "Smyrnhs", 15, 123456789
                ));
    }

    @org.junit.jupiter.api.Test
    void addReceiptTest2() throws WrongReceiptKindException, WrongFileFormatException, IOException, WrongTaxpayerStatusException, WrongReceiptDateException
    {
        // ReceiptAlreadyExistsException Test
        boolean loadTaxpayerFromTXTFile = taxpayerManager.loadTaxpayer(String.valueOf(txtFileName));
        assertThrows(
                ReceiptAlreadyExistsException.class,
                () -> taxpayerManager.addReceipt
                        (
                                20, "8/11/2022", 1999, "Basic", "TEST",
                                "Greece", "Ioannina", "Smyrnhs", 15, 123456789
                        ));
    }

     @org.junit.jupiter.api.Test
    void removeReceiptTest1() throws WrongReceiptKindException, WrongFileFormatException, IOException, WrongTaxpayerStatusException, WrongReceiptDateException, ReceiptIdDoesNotExistException
    {
        // Remove existing receipt
        boolean loadTaxpayerFromTXTFile = taxpayerManager.loadTaxpayer(String.valueOf(txtFileName));
        taxpayerManager.removeReceipt(20);
        assertFalse(taxpayerManager.isTaxpayerHashMapContainsReceiptID(20));
    }

    @org.junit.jupiter.api.Test
    void removeReceiptTest2() throws WrongReceiptKindException, WrongFileFormatException, IOException, WrongTaxpayerStatusException, WrongReceiptDateException
    {
        // Remove existing receipt
        boolean loadTaxpayerFromTXTFile = taxpayerManager.loadTaxpayer(String.valueOf(txtFileName));
        assertThrows(
                ReceiptIdDoesNotExistException.class,
                () -> taxpayerManager.removeReceipt(9999)
        );
    }

    @org.junit.jupiter.api.Test
    void saveLogFileTest1() throws WrongFileFormatException, IOException
    {
        assertTrue(taxpayerManager.saveLogFile(123456789, "_LOG.txt"));
    }

    @org.junit.jupiter.api.Test
    void saveLogFileTest2() throws  WrongFileFormatException, IOException
    {
        assertTrue(taxpayerManager.saveLogFile(123456789, "_LOG.xml"));
    }

    @org.junit.jupiter.api.Test
    void saveLogFileTest3()
    {
        assertThrows(
                WrongFileFormatException.class,
                () -> taxpayerManager.saveLogFile(123456789, "_LOG.xlsx")
        );
    }

}