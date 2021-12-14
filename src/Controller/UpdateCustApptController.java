package Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class UpdateCustApptController {

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
    void backToViewAppts(ActionEvent event) {

    }

    @FXML
    void saveApptAction(ActionEvent event) {

    }

}

