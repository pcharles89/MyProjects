package Controller;

import DAO.CustomerDAO;
import Model.Appointment;
import Model.Customer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

public class UpdateCustApptController implements Initializable {
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
        if (((titleTf.getText().equals("")) || (descriptionTf.getText().equals("")) || (locationTf.getText().equals(""))
                || (typeTf.getText().equals("")) || (contactCb.getSelectionModel().getSelectedItem() == null) ||
                (startDp.getValue() == null) || (startTimeCb.getSelectionModel().getSelectedItem() == null) ||
                (endTimeCb.getSelectionModel().getSelectedItem() == null) || (custIdCb.getSelectionModel().getSelectedItem() == null)
                || (userIdCb.getSelectionModel().getSelectedItem() == null))) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialogue");
            alert.setContentText("Please enter a valid value for each field.");
            alert.showAndWait();
        }

    }

    public void receiveAppointment(Appointment appointment) throws SQLException {

    }

    @Override
    public void initialize (URL url, ResourceBundle resourceBundle) {
        apptIdLbl.setDisable(true);
    }

}

