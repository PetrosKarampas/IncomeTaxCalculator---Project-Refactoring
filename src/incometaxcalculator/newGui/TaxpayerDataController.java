package incometaxcalculator.newGui;

import incometaxcalculator.data.management.Company;
import incometaxcalculator.data.management.Receipt;
import incometaxcalculator.data.management.TaxpayerManager;
import javafx.beans.property.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class TaxpayerDataController implements Initializable{

    @FXML private Button goBack;
    @FXML private Button updateBtn;
    @FXML private TextField fullName;
    @FXML private TextField income;

    @FXML private ListView<Integer> receiptList;
    @FXML private TextField status;
    @FXML private TextField taxRegistrationNumber;

    private TaxpayerManager manager = new TaxpayerManager();
    private String statusText;
    private String incomeText;
    private String fullNameText;
    private int trnText;

    public TaxpayerDataController(String fullName, String income, String status, int trn) {
        fullNameText = fullName;
        incomeText = income;
        statusText = status;
        trnText = trn;
    }

    @FXML
    void addReceipt(ActionEvent event) {

    }

    @FXML
    void removeReceipt(ActionEvent event) {

    }

    @FXML
    void viewChartReport(ActionEvent event) {

    }
    @FXML
    public void updateTaxpayerData() {
        System.out.println(this.fullName.getText() + " \n" + this.income.getText());
        manager.getTaxpayer(trnText).setIncome(Float.parseFloat(this.income.getText()));
    }

    @FXML
    void goBackToMainScene(ActionEvent event) throws IOException {
        Stage stage = (Stage) goBack.getScene().getWindow();
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Main.fxml")));
        stage.setScene(new Scene(root));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.fullName.setText(fullNameText);
        this.status.setText(statusText);
        this.taxRegistrationNumber.setText(String.valueOf(trnText));
        this.income.setText(incomeText);
    }
}
