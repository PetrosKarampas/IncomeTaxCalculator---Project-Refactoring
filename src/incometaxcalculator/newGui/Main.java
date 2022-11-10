package incometaxcalculator.newGui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;
import java.util.Objects;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Main.fxml")));
        stage.setTitle("Income Tax Calculator");
        //Image logo = new Image("/incometaxcalculator/images/Logo.png");

        //stage.getIcons().add(logo);
        stage.setScene(new Scene(root));
        stage.setResizable(false);
        stage.show();

    }
}
