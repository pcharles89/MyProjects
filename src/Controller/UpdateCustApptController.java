package Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;

public class UpdateCustApptController {
    private Stage stage;
    private Parent scene;

    @FXML
    private Label apptIdLbl;

    @FXML
    private DatePicker startDp;

    @FXML
    private ComboBox<?> startTimeCb;

    @FXML
    private ComboBox<?> endTimeCb;

    @FXML
    private TextField titleTf;

    @FXML
    private TextField descriptionTf;

    @FXML
    private TextField locationTf;

    @FXML
    private ComboBox<?> contactCb;

    @FXML
    private TextField typeTf;

    @FXML
    private ComboBox<?> custIdCb;

    @FXML
    private ComboBox<?> userIdCb;

    @FXML
    private Button saveApptRbtn;

    @FXML
    private Button cancelApptRbtn;

    @FXML
    private Label addApptLbl;

    @FXML
    void backToViewAppts(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "This will clear any unsaved work, continue?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/View/CustAppts.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        }
    }

    @FXML
    void saveApptAction(ActionEvent event) {

    }

}

