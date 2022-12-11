package incometaxcalculator.test;

import incometaxcalculator.data.management.TaxpayerManager;
import incometaxcalculator.exceptions.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import org.junit.Test;
import static org.junit.Assert.*;

public class TaxpayerManagerTest
{
    TaxpayerManager taxpayerManager = new TaxpayerManager();
    Path txtFilepath = Paths.get(System.getProperty("user.dir")+"/123456789_INFO.txt");
    Path xmlFilePath = Paths.get(System.getProperty("user.dir")+"/123456789_INFO.txt");
    Path txtFileName = txtFilepath.getFileName();
    Path xmlFileName = xmlFilePath.getFileName();

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
     public void removeReceiptTest1() throws WrongReceiptKindException, WrongFileFormatException, IOException, WrongTaxpayerStatusException, WrongReceiptDateException, WrongReceiptIDException
     {
        taxpayerManager.loadTaxpayer(String.valueOf(txtFileName));
        taxpayerManager.removeReceipt(12);
        assertFalse(taxpayerManager.containsReceipt(12));
    }

    @Test
    public void removeReceiptTest2() throws WrongReceiptKindException, WrongFileFormatException, IOException, WrongTaxpayerStatusException, WrongReceiptDateException
    {
        taxpayerManager.loadTaxpayer(String.valueOf(txtFileName));
        assertThrows(
                WrongReceiptIDException.class,
                () -> taxpayerManager.removeReceipt(9999)
        );
    }

    @Test
    public void saveLogFileTest1() throws WrongFileFormatException, IOException, WrongTaxpayerStatusException, WrongReceiptKindException, WrongReceiptDateException
    {
        taxpayerManager.loadTaxpayer(String.valueOf(txtFileName));
        taxpayerManager.saveLogFile(123456789, "_LOG.txt");
        String expected = " Apostolos Zarras,  123456789,  260000.0,  18765.05,  1501.204,  20266.254,  6,  0.0,  8784.0,  100.0,  0.0,  1000.0";
        Path txtLogFilePath = Paths.get(System.getProperty("user.dir")+"/123456789_LOG.txt");
        BufferedReader br = new BufferedReader(new FileReader(txtLogFilePath.toFile()));
        String line;
        ArrayList<String> contents = new ArrayList<>();
        while((line = br.readLine()) != null)
        {
            String[] value = line.split(":");
            contents.add(value[1]);
        }
        String actual = String.join(", ", contents);
        assertEquals(expected, actual);
    }
}