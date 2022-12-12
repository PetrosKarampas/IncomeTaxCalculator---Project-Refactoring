package incometaxcalculator.view;

import incometaxcalculator.data.management.Receipt;
import incometaxcalculator.data.management.TaxpayerManager;
import incometaxcalculator.exceptions.ReceiptIdDoesNotExistException;
import incometaxcalculator.exceptions.WrongFileFormatException;
import incometaxcalculator.exceptions.WrongReceiptDateException;
import incometaxcalculator.exceptions.WrongTaxpayerStatusException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

public class TaxpayerDataController implements Initializable {
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
        ReceiptController newReceipt = new ReceiptController(this);
        newReceipt.setTaxRegistrationNumber(this.trnText);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ReceiptWindow.fxml"));
        loader.setController(newReceipt);

        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage primaryStage = new Stage();

        primaryStage.setTitle("Receipt data");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.initModality(Modality.APPLICATION_MODAL);
        primaryStage.showAndWait();
    }

    void addReceiptIdToList(int id) {
        receiptList.getItems().add(id);
    }

    @FXML
    private void saveAsTXT() throws WrongFileFormatException, IOException, WrongTaxpayerStatusException {
        taxpayerManager.saveLogFile(trnText, "_LOG.txt");
    }

    @FXML
    private void saveAsXML() throws WrongFileFormatException, IOException, WrongTaxpayerStatusException {
        taxpayerManager.saveLogFile(trnText, "_LOG.xml");
    }

    @FXML
    void removeReceipt(ActionEvent event) throws WrongFileFormatException, IOException, ReceiptIdDoesNotExistException, WrongTaxpayerStatusException {
        int selectedReceipt = receiptList.getSelectionModel().getSelectedItem();
        int selectedReceiptIndex = receiptList.getSelectionModel().getSelectedIndex();

        receiptList.getItems().remove(selectedReceiptIndex);
        taxpayerManager.removeReceipt(selectedReceipt);
    }

    @FXML
    void viewChartReport(ActionEvent event) throws IOException {
        HashMap<Integer, Receipt> receipts = taxpayerManager.getReceiptHashMap(trnText);

        /*
         * Setup PieChart
         */
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
        pieStage.setX(300);
        pieStage.setY(400);
        pieStage.setScene(scene);
        pieStage.setResizable(false);
        pieStage.show();

        /*
         * Setup BarChart
         */
        Stage barChartStage = new Stage();
        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
        final BarChart<String,Number> barChart = new BarChart<String, Number>(xAxis, yAxis);
        barChart.setTitle("Tax Analysis");
        yAxis.setLabel("Dollars $");

        XYChart.Series set = new XYChart.Series<>();
        set.setName("Tax");
        double basicTax = taxpayerManager.getTaxpayerBasicTax(trnText);
        double taxVariation = taxpayerManager.getTaxpayerVariationTaxOnReceipts(trnText);
        double totalTax = taxpayerManager.getTaxpayerTotalTax(trnText);

        set.getData().add(new XYChart.Data<>("Basic", basicTax));
        if(taxVariation < 0)
            set.getData().add(new XYChart.Data<>("Decrease", -taxVariation));
        else
            set.getData().add(new XYChart.Data<>("Increase", taxVariation));
        set.getData().add(new XYChart.Data<>("Total", totalTax));

        Scene barChartScene = new Scene(barChart, 500, 500);
        barChart.getData().addAll(set);
        barChartStage.setScene(barChartScene);
        barChartStage.setX(900);
        barChartStage.setY(400);
        barChartStage.setResizable(false);
        barChartStage.show();
    }

    @FXML
    public void updateTaxpayerData() throws WrongFileFormatException, IOException, WrongTaxpayerStatusException {
        System.out.println(this.fullName.getText() + " \n" + this.income.getText());
        taxpayerManager.getTaxpayer(trnText).setIncome(Float.parseFloat(this.income.getText()));
        taxpayerManager.getTaxpayer(trnText).setFullName(this.fullName.getText());
        taxpayerManager.updateFiles(trnText);
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
