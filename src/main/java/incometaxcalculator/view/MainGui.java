package incometaxcalculator.view;

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

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = null;

        try {
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/Main.fxml")));
        }
        catch( NullPointerException e) {
            System.out.println("Main window fxml is null!" + e.getMessage());
        }

        Image logo = new Image("/Logo.png");

        stage.setTitle("Income Tax Calculator");
        stage.getIcons().add(logo);
        stage.setScene(new Scene(root));
        stage.setResizable(false);
        stage.show();
    }
}
