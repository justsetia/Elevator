import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.*;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class Admin implements Initializable {
    Elevator elevator=new Elevator();

    @FXML
    private AnchorPane AdminPage;

    @FXML
    private Button ButtonTotalFloor;

    @FXML
    private Label EnterTotalFloors;

    @FXML
    private TextField InputTotalFloor;

    @FXML
    private Text TextWelcome;

    @FXML
    private ChoiceBox<String> AlgorithmChoiseBox;


    @FXML
    void ButtonSaveAction(ActionEvent event) {

    }

    @FXML
    void ButtonSaveClick(MouseEvent event) throws IOException {

            int floorNum= Integer.parseInt(InputTotalFloor.getText());
            String Algo=AlgorithmChoiseBox.getSelectionModel().getSelectedItem();

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Elevator.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root1));
            String AlgoChoice =AlgorithmChoiseBox.getSelectionModel().getSelectedItem();
            stage.show();

            Elevator elevatorController = fxmlLoader.getController();
            elevatorController.process(floorNum, Algo,AlgoChoice);

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        AlgorithmChoiseBox.getItems().addAll("FCFS", "SJF", "RR", "SRTF");
        AlgorithmChoiseBox.setValue("FCFS");

    }
}
