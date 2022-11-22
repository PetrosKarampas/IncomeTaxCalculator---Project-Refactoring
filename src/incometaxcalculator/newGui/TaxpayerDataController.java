package incometaxcalculator.newGui;

import incometaxcalculator.data.management.Receipt;
import incometaxcalculator.data.management.TaxpayerManager;
import incometaxcalculator.exceptions.ReceiptIdDoesNotExistException;
import incometaxcalculator.exceptions.WrongFileFormatException;
import incometaxcalculator.exceptions.WrongReceiptDateException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

public class TaxpayerDataController implements Initializable{

    @FXML private Button updateBtn;
    @FXML private Button removeReceiptBtn;
    @FXML private TextField fullName;
    @FXML private TextField income;
    @FXML private ListView<Integer> receiptList;
    @FXML private TextField status;
    @FXML private TextField taxRegistrationNumber;

    private final TaxpayerManager taxpayerManager = new TaxpayerManager();
    private final String statusText;
    private final String incomeText;
    private final String fullNameText;
    private final int trnText;

    public TaxpayerDataController(String fullName, String income, String status, int trn) {
        fullNameText = fullName;
        incomeText = income;
        statusText = status;
        trnText = trn;
    }

    @FXML
    void addReceipt(ActionEvent event) throws WrongReceiptDateException, IOException {
        ReceiptController newReceipt = new ReceiptController();
        newReceipt.setTaxRegistrationNumber(this.trnText);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("ReceiptWindow.fxml"));
        loader.setController(newReceipt);

        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage primaryStage = new Stage();

        primaryStage.setTitle("Receipt data");
        primaryStage.setScene(scene);
        primaryStage.initModality(Modality.APPLICATION_MODAL);
        primaryStage.showAndWait();

        receiptList.getItems().add(newReceipt.getReceiptId());
    }


    @FXML
    void removeReceipt(ActionEvent event) throws WrongFileFormatException, IOException, ReceiptIdDoesNotExistException {
        int selectedReceipt = receiptList.getSelectionModel().getSelectedItem();
        int selectedReceiptIndex = receiptList.getSelectionModel().getSelectedIndex();

        receiptList.getItems().remove(selectedReceiptIndex);
        taxpayerManager.removeReceipt(selectedReceipt);
    }

    @FXML
    void viewChartReport(ActionEvent event) throws IOException {
        HashMap<Integer, Receipt> receipts = taxpayerManager.getReceiptHashMap(trnText);

        ObservableList<PieChart.Data> pieData = FXCollections.observableArrayList(
                new PieChart.Data("Entertainment", taxpayerManager.getTaxpayerReceiptAmountPerKind(trnText, 0)),
                new PieChart.Data("Basic", taxpayerManager.getTaxpayerReceiptAmountPerKind(trnText, 1)),
                new PieChart.Data("Travel", taxpayerManager.getTaxpayerReceiptAmountPerKind(trnText, 2)),
                new PieChart.Data("Health", taxpayerManager.getTaxpayerReceiptAmountPerKind(trnText, 3)),
                new PieChart.Data("Other", taxpayerManager.getTaxpayerReceiptAmountPerKind(trnText, 4))
        );

        PieChart pChart = new PieChart(pieData);

        Group root = new Group(pChart);
        Scene scene = new Scene(root, 500, 500);
        Stage pieStage = new Stage();
        pieStage.setTitle("Pie Chart");
        pieStage.setScene(scene);
        pieStage.show();
    }

    @FXML
    public void updateTaxpayerData() {
        System.out.println(this.fullName.getText() + " \n" + this.income.getText());
        taxpayerManager.getTaxpayer(trnText).setIncome(Float.parseFloat(this.income.getText()));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.fullName.setText(fullNameText);
        this.status.setText(statusText);
        this.taxRegistrationNumber.setText(String.valueOf(trnText));
        this.income.setText(incomeText);

        removeReceiptBtn.disableProperty().bind(receiptList.getSelectionModel().selectedItemProperty().isNull());

        taxpayerManager.getReceiptHashMap(trnText).forEach((Integer ID, Receipt receipt) ->{
            receiptList.getItems().add(ID);
                });
    }

}
