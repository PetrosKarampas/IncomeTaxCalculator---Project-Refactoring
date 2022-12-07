package incometaxcalculator.view;

import incometaxcalculator.data.management.*;
import incometaxcalculator.exceptions.ReceiptAlreadyExistsException;
import incometaxcalculator.exceptions.WrongFileFormatException;
import incometaxcalculator.exceptions.WrongReceiptDateException;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.ResourceBundle;

public class ReceiptController implements Initializable{

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
    private final TaxpayerDataController taxpayerDataController;
    private final TaxpayerManager manager = new TaxpayerManager();

    public ReceiptController(TaxpayerDataController controller) {
        taxpayerDataController = controller;
    }

    @FXML public void submitReceipt() throws WrongFileFormatException, IOException, WrongReceiptDateException, ReceiptAlreadyExistsException {
        LocalDate localDate = date.getValue();
        String correctDate = localDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));

        int id = Integer.parseInt(receiptId.getText());

        if (!manager.containsReceipt(id)) {
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

            taxpayerDataController.addReceiptIdToList(Integer.parseInt(receiptId.getText()));
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Receipt id error");
            alert.setHeaderText("Receipt id already exists!");
            alert.showAndWait();
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        submitReceiptBtn.disableProperty().bind(Bindings.isEmpty(receiptId.textProperty())
                .or(Bindings.isEmpty(kind.textProperty()))
                .or(Bindings.isEmpty(amount.textProperty()))
                .or(Bindings.isEmpty(company.textProperty()))
                .or(Bindings.isEmpty(country.textProperty()))
                .or(Bindings.isEmpty(city.textProperty()))
                .or(Bindings.isEmpty(street.textProperty()))
                .or(Bindings.isEmpty(number.textProperty()))
                .or(date.valueProperty().isNull()));
    }
}
