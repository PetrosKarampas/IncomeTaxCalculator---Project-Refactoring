package incometaxcalculator.view;

import incometaxcalculator.data.management.*;
import incometaxcalculator.exceptions.ReceiptAlreadyExistsException;
import incometaxcalculator.exceptions.WrongFileFormatException;
import incometaxcalculator.exceptions.WrongReceiptDateException;
import incometaxcalculator.exceptions.WrongTaxpayerStatusException;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class ReceiptController implements Initializable
{

    @FXML private Button submitReceiptBtn;
    @FXML private TextField receiptId;
    @FXML private DatePicker date;
    @FXML private ChoiceBox<String> kind;
    private String receiptKind;
    @FXML private TextField amount;
    @FXML private TextField company;
    @FXML private TextField country;
    @FXML private TextField city;
    @FXML private TextField street;
    @FXML private TextField number;

    private int taxRegistrationNumber;
    private final TaxpayerDataController taxpayerDataController;
    private final TaxpayerManager taxpayerManager = new TaxpayerManager();

    public ReceiptController(TaxpayerDataController controller) {
        taxpayerDataController = controller;
    }

    @FXML public void submitReceipt() throws WrongFileFormatException, IOException, WrongReceiptDateException, ReceiptAlreadyExistsException, WrongTaxpayerStatusException
    {
        LocalDate localDate = date.getValue();
        String correctDate = localDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));

        int id = Integer.parseInt(receiptId.getText());

        if (!taxpayerManager.containsReceipt(id))
        {
            taxpayerManager.addReceipt(Integer.parseInt(receiptId.getText()),
                    correctDate,
                    Float.parseFloat(amount.getText()),
                    receiptKind,
                    company.getText(),
                    country.getText(),
                    city.getText(),
                    street.getText(),
                    Integer.parseInt(number.getText()),
                    taxRegistrationNumber);

            taxpayerDataController.addReceiptIdToList(Integer.parseInt(receiptId.getText()));
        }
        else
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Receipt id error");
            alert.setHeaderText("Receipt id already exists!");
            alert.showAndWait();
        }
        Stage stage = (Stage) submitReceiptBtn.getScene().getWindow();
        stage.close();
    }

    private void getKind(ActionEvent event) {
        receiptKind = kind.getValue();
    }

    public void setTaxRegistrationNumber(int taxRegistrationNumber)
    {
        this.taxRegistrationNumber = taxRegistrationNumber;
    }

    public int getReceiptId() {
        return Integer.parseInt(receiptId.getText());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        final String[] receiptKinds = {"Basic", "Entertainment", "Health", "Travel", "Other"};
        kind.getItems().addAll(receiptKinds);
        kind.setOnAction(this::getKind);

        submitReceiptBtn.disableProperty().bind(Bindings.isEmpty(receiptId.textProperty())
                .or(Bindings.isEmpty(amount.textProperty()))
                .or(Bindings.isEmpty(company.textProperty()))
                .or(Bindings.isEmpty(country.textProperty()))
                .or(Bindings.isEmpty(city.textProperty()))
                .or(Bindings.isEmpty(street.textProperty()))
                .or(Bindings.isEmpty(number.textProperty()))
                .or(date.valueProperty().isNull())
                .or(kind.valueProperty().isNull())
        );
    }
}
