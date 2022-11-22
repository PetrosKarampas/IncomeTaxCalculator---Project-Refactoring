package incometaxcalculator.newGui;

import incometaxcalculator.data.management.*;
import incometaxcalculator.exceptions.ReceiptAlreadyExistsException;
import incometaxcalculator.exceptions.WrongFileFormatException;
import incometaxcalculator.exceptions.WrongReceiptDateException;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.util.StringConverter;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.ResourceBundle;

public class ReceiptController{

    @FXML private Button submitReceiptBtn;
    @FXML private TextField receiptId;
    @FXML private DatePicker date;
    @FXML private TextField kind;
    @FXML private TextField amount;
    @FXML private TextField company;
    @FXML private TextField country;
    @FXML private TextField city;
    @FXML private TextField street;
    @FXML private TextField number;

    private int taxRegistrationNumber;
    private final TaxpayerManager manager = new TaxpayerManager();

    @FXML public void submitReceipt(){
        LocalDate localDate = date.getValue();
        String correctDate = localDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));

        try {
            manager.addReceipt(Integer.parseInt(receiptId.getText()),
                    correctDate,
                    Float.parseFloat(amount.getText()),
                    kind.getText(),
                    company.getText(),
                    country.getText(),
                    city.getText(),
                    street.getText(),
                    Integer.parseInt(number.getText()),
                    taxRegistrationNumber);
        } catch (IOException | WrongReceiptDateException | ReceiptAlreadyExistsException | WrongFileFormatException e1) {
            System.out.println(Arrays.toString(e1.getStackTrace()));
        }

        Stage stage = (Stage) submitReceiptBtn.getScene().getWindow();
        stage.close();
    }

    public void setTaxRegistrationNumber(int taxRegistrationNumber) {
        this.taxRegistrationNumber = taxRegistrationNumber;
    }

    public int getReceiptId() {
        return Integer.parseInt(receiptId.getText());
    }
}
