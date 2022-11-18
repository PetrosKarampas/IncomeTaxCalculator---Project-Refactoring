package incometaxcalculator.newGui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.util.Objects;

public class MainGui extends Application{


    public static void main(String[] args) {
        Application.launch();
    }

    @Override // Similar to main method for jfx
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Main.fxml")));
        Image logo = new Image("/incometaxcalculator/images/Logo.png");

        stage.setTitle("Income Tax Calculator");
        stage.getIcons().add(logo);
        stage.setScene(new Scene(root));
        stage.setResizable(false);
        stage.show();

    }
}
