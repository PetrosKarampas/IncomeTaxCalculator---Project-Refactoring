package incometaxcalculator.newGui;

import incometaxcalculator.data.management.TaxpayerManager;
import incometaxcalculator.exceptions.*;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class MainSceneController extends Application {

    private TaxpayerManager taxpayerManager = new TaxpayerManager();
    @FXML
    private TextField income = new TextField();
    @FXML
    private ListView<Integer> taxpayerList;
    int i = 0;


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Main.fxml")));
        Image logo = new Image("/incometaxcalculator/images/Logo.png");

        stage.setTitle("Income Tax Calculator");
        stage.getIcons().add(logo);
        stage.setScene(new Scene(root));
        stage.setResizable(false);
        stage.show();
    }


    @FXML
    public void loadTxtDocument() throws WrongReceiptKindException, WrongFileFormatException, IOException, WrongFileEndingException, WrongTaxpayerStatusException, WrongReceiptDateException {
        loadDocument("txt");
    }

    @FXML
    public void loadXMLDocument() throws WrongReceiptKindException, WrongFileFormatException, IOException, WrongFileEndingException, WrongTaxpayerStatusException, WrongReceiptDateException {
        loadDocument("xml");
    }

    private void loadDocument(String fileType) throws IOException, WrongFileFormatException, WrongFileEndingException, WrongTaxpayerStatusException, WrongReceiptKindException, WrongReceiptDateException {
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
    public void selectTaxpayer() {

    }

    @FXML
    public void switchToScene2(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("TaxpayerDataScene.fxml")));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void showInformationDialog() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Information Dialog");
        alert.setHeaderText("Look, an Information Dialog");
        alert.setContentText("I have a great message for you!");
        alert.showAndWait();
    }
}
