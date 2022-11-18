package incometaxcalculator.newGui;

import incometaxcalculator.data.management.TaxpayerManager;
import incometaxcalculator.exceptions.*;
import incometaxcalculator.gui.TaxpayerData;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class MainController {

    @FXML
    private Button selectBtn;
    @FXML
    private ListView<Integer> taxpayerList;
    private final TaxpayerManager taxpayerManager = new TaxpayerManager();

    @FXML
    void deleteTaxpayer(MouseEvent event) throws WrongTaxRegistrationNumberException {
        int removedRegistrationNumber = taxpayerList.getSelectionModel().getSelectedItem();

        taxpayerList.getItems().remove(taxpayerList.getSelectionModel().getSelectedItem());
        taxpayerManager.removeTaxpayer(removedRegistrationNumber);
    }

    @FXML
    void loadTxtDocument(ActionEvent event) throws WrongReceiptKindException, WrongFileFormatException, IOException, WrongFileEndingException, WrongTaxpayerStatusException, WrongReceiptDateException {
        loadDocument("txt");
    }

    @FXML
    void loadXMLDocument(ActionEvent event) throws WrongReceiptKindException, WrongFileFormatException, IOException, WrongFileEndingException, WrongTaxpayerStatusException, WrongReceiptDateException {
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
                System.out.println("Taxpayer already exists");
            }
            else {
                taxpayerManager.loadTaxpayer(fileName);
                taxpayerList.getItems().add(registrationNumber);
            }
        }else{
            System.out.println("file was not selected");
        }
    }

    @FXML
    void selectTaxpayer(MouseEvent event) throws IOException {

        int trn = taxpayerList.getSelectionModel().getSelectedItem();
        String fullName = taxpayerManager.getTaxpayerFullName(trn);
        String status = taxpayerManager.getTaxpayerStatus(trn);
        String income = taxpayerManager.getTaxpayerIncome(trn);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("TaxpayerDataScene.fxml"));
        TaxpayerDataController taxPayer = new TaxpayerDataController(fullName, income, status, trn);
        loader.setController(taxPayer);

        Stage stage = (Stage) selectBtn.getScene().getWindow();
        Parent root = loader.load();
        stage.setScene(new Scene(root));
    }

}
