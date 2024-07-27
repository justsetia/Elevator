
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.*;
import javafx.fxml.FXML;

import java.util.Objects;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{

        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Admin.fxml")));
        primaryStage.setTitle("Admin");
        primaryStage.setScene(new Scene(root, 600,400));
        System.out.println("main");
        primaryStage.show();
    }
    public static void main(String[] args) {
        launch(args);

    }
}