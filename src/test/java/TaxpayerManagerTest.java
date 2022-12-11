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
    public void loadTaxpayerTest1() throws WrongFileFormatException, IOException, WrongTaxpayerStatusException, WrongReceiptDateException
    {
        taxpayerManager.loadTaxpayer(String.valueOf(txtFileName));
        assertTrue(taxpayerManager.containsTaxpayer(123456789));

    }

    @Test
    public void loadTaxpayerTest2() throws WrongFileFormatException, IOException, WrongTaxpayerStatusException, WrongReceiptDateException
    {
        taxpayerManager.loadTaxpayer(String.valueOf(xmlFileName));
        assertTrue(taxpayerManager.containsTaxpayer(123456789));
    }

   @Test
   public void removeTaxpayerTest1() throws WrongFileFormatException, IOException, WrongTaxpayerStatusException, WrongReceiptDateException, WrongTaxRegistrationNumberException
    {
        taxpayerManager.loadTaxpayer(String.valueOf(txtFileName));
        taxpayerManager.removeTaxpayer(123456789);
        assertFalse(taxpayerManager.containsTaxpayer(123456789));
    }

    @Test
    public void removeTaxpayerTest2() throws WrongFileFormatException, IOException, WrongTaxpayerStatusException, WrongReceiptDateException
    {
        taxpayerManager.loadTaxpayer(String.valueOf(txtFileName));
        assertThrows(
                WrongTaxRegistrationNumberException.class,
                () -> taxpayerManager.removeTaxpayer(999999999)
                    );
    }

    @Test
    public void addReceiptTest1() throws IOException, WrongReceiptDateException, ReceiptAlreadyExistsException, WrongFileFormatException, WrongTaxpayerStatusException
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
    public void addReceiptTest2() throws WrongFileFormatException, IOException, WrongTaxpayerStatusException, WrongReceiptDateException
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
     public void removeReceiptTest1() throws WrongFileFormatException, IOException, WrongTaxpayerStatusException, WrongReceiptDateException
     {
        taxpayerManager.loadTaxpayer(String.valueOf(txtFileName));
        taxpayerManager.removeReceipt(9);
        assertFalse(taxpayerManager.containsReceipt(9));
    }

    @Test
    public void saveLogFileTest1() throws WrongFileFormatException, IOException, WrongTaxpayerStatusException, WrongReceiptDateException
    {
        taxpayerManager.loadTaxpayer(String.valueOf(txtFileName));
        taxpayerManager.saveLogFile(123456789, "_LOG.txt");
        String expected = " Mpampis Mpampouras,  123456789,  254658.0,  18238.862999999998,  1459.1090399999998,  19697.972039999997,  10,  234.89999389648438,  4086.0,  532.0,  4801.0,  1000.0";
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