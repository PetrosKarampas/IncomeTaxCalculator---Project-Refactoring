package incometaxcalculator.view;

import incometaxcalculator.data.management.TaxpayerManager;
import incometaxcalculator.exceptions.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    @FXML private Button selectBtn;
    @FXML private Button deleteBtn;
    @FXML private ListView<Integer> taxpayerList;
    private final TaxpayerManager taxpayerManager = new TaxpayerManager();

    @FXML
    void deleteTaxpayer(MouseEvent event) throws WrongTaxRegistrationNumberException {
        int removedRegistrationNumber = taxpayerList.getSelectionModel().getSelectedItem();

        taxpayerList.getItems().remove(taxpayerList.getSelectionModel().getSelectedItem());
        taxpayerManager.removeTaxpayer(removedRegistrationNumber);
    }

    @FXML
    void loadTxtDocument(ActionEvent event) throws WrongReceiptKindException, WrongFileFormatException, IOException, WrongTaxpayerStatusException, WrongReceiptDateException {
        loadDocument("txt");
    }

    @FXML
    void loadXMLDocument(ActionEvent event) throws WrongReceiptKindException, WrongFileFormatException, IOException, WrongTaxpayerStatusException, WrongReceiptDateException {
        loadDocument("xml");
    }

    private void loadDocument(String fileType) throws WrongReceiptKindException, WrongFileFormatException, IOException, WrongTaxpayerStatusException, WrongReceiptDateException {
        FileChooser fc = new FileChooser();
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter(fileType + " files", "*." + fileType));
        File selected = fc.showOpenDialog(null);

        if(selected != null && selected.getName().contains("INFO")) {
            String fileName = selected.getName();
            int registrationNumber = Integer.parseInt(selected.getName().substring(0,9));

            if (taxpayerManager.containsTaxpayer(registrationNumber)) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Loading Error!");
                alert.setHeaderText("Taxpayer already exists");
                alert.showAndWait();
            }
            else {
                taxpayerManager.loadTaxpayer(fileName);
                taxpayerList.getItems().add(registrationNumber);
            }
        }
        else if (selected != null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("File selection error");
            alert.setHeaderText("Please select a compatible file!");
            alert.setContentText("Compatible file format:   (ΑΦΜ)_INFO." + fileType);
            alert.showAndWait();
        }
        else {
            System.out.println("file selection got canceled!");
        }
    }

    @FXML
    void selectTaxpayer(MouseEvent event) throws IOException, WrongTaxpayerStatusException {
        int trn = taxpayerList.getSelectionModel().getSelectedItem();
        String fullName = taxpayerManager.getTaxpayerFullName(trn);
        String status = taxpayerManager.getTaxpayerStatus(trn);
        String income = taxpayerManager.getTaxpayerIncome(trn);

        TaxpayerDataController taxPayer = new TaxpayerDataController(fullName, income, status, trn);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/TaxpayerDataScene.fxml"));
        loader.setController(taxPayer);
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage primaryStage = new Stage();

        primaryStage.setTitle("Taxpayer Data");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.initModality(Modality.APPLICATION_MODAL);
        primaryStage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        selectBtn.disableProperty().bind(taxpayerList.getSelectionModel().selectedItemProperty().isNull());
        deleteBtn.disableProperty().bind(taxpayerList.getSelectionModel().selectedItemProperty().isNull());
    }
}
