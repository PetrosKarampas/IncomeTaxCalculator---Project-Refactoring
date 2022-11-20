package incometaxcalculator.newGui;

import incometaxcalculator.data.management.Date;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class ReceiptController implements Initializable {

    @FXML private Button submitReceiptBtn;
    @FXML private TextField receiptId;
    @FXML private TextField date;
    @FXML private TextField kind;
    @FXML private TextField amount;
    @FXML private TextField company;
    @FXML private TextField country;
    @FXML private TextField city;
    @FXML private TextField street;
    @FXML private TextField number;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
