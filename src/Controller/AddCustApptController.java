package Controller;

import DAO.*;
import Model.Contact;
import Model.Customer;
import Model.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import java.sql.Timestamp;
import java.time.*;
import java.util.Optional;
import java.util.ResourceBundle;

public class AddCustApptController implements Initializable {
    private Stage stage;
    private Parent scene;
    //private static ObservableList<LocalTime> startTimes = FXCollections.observableArrayList();

    @FXML
    private Label apptIdLbl;

    @FXML
    private DatePicker startDp;

    @FXML
    private ComboBox<LocalTime> startTimeCb;

    @FXML
    private ComboBox<LocalTime> endTimeCb;

    @FXML
    private TextField titleTf;

    @FXML
    private TextField descriptionTf;

    @FXML
    private TextField locationTf;

    @FXML
    private ComboBox<Contact> contactCb;

    @FXML
    private TextField typeTf;

    @FXML
    private ComboBox<Customer> custIdCb;

    @FXML
    private ComboBox<User> userIdCb;

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
    void saveApptAction(ActionEvent event) throws IOException {
        /*if(((titleTf.getText().equals("")) || (descriptionTf.getText().equals("")) || (locationTf.getText().equals(""))
                || (typeTf.getText().equals("")) || (contactCb.getSelectionModel().getSelectedItem() == null) ||
        (startDp.getValue() == null) || (startTimeCb.getSelectionModel().getSelectedItem() == null) ||
                (endTimeCb.getSelectionModel().getSelectedItem() == null) || (custIdCb.getSelectionModel().getSelectedItem() == null)
                || (userIdCb.getSelectionModel().getSelectedItem() == null))) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialogue");
            alert.setContentText("Please enter a valid value for each field.");
            alert.showAndWait();
        }
        LocalDate apptStartDate = startDp.getValue();
        LocalTime apptStartTime = (LocalTime) startTimeCb.getSelectionModel().getSelectedItem();
        ZoneId localZone = ZoneId.systemDefault();
        ZonedDateTime apptStart = ZonedDateTime.of(apptStartDate, apptStartTime, localZone);
        ZonedDateTime apptStartCheck = apptStart.withZoneSameInstant(ZoneId.of("America/New York"));

        else {
            AppointmentDAO.createAppointment(Integer.parseInt(apptIdLbl.getText()), titleTf.getText(), descriptionTf.getText(),
                    locationTf.getText(), typeTf.getText(), ,endTimeCb ,Integer.parseInt(String.valueOf(custIdCb.getSelectionModel().getSelectedItem())),
                    Integer.parseInt(String.valueOf(userIdCb.getSelectionModel().getSelectedItem())),
                    Integer.parseInt(String.valueOf(contactCb.getSelectionModel().getSelectedItem())));
            // (int id, String title, String description, String location, String type, Timestamp start,
            //                                         Timestamp end, int customerId, int userId, int contactId)
            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/View/CustAppts.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        } */
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            apptIdLbl.setText(String.valueOf(AppointmentDAO.getAllAppointments().size() + 1));
            contactCb.setItems(ContactDAO.getAllContacts());
            custIdCb.setItems(CustomerDAO.getAllCustomers());
            userIdCb.setItems(UserDAO.getAllUsers());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        LocalTime businessTimeStart = LocalTime.of(0, 30);
        LocalTime businessTimeEnd = LocalTime.of(23, 30);
        ZoneId businessLocale = ZoneId.of("America/New_York");
        //ZonedDateTime easternZdt = ZonedDateTime.of()

        while(businessTimeStart.isBefore(businessTimeEnd)) {
            startTimeCb.getItems().add(businessTimeStart);
            endTimeCb.getItems().add(businessTimeStart);
            businessTimeStart = businessTimeStart.plusMinutes(30);
            businessTimeEnd = businessTimeStart.plusMinutes(30);
        }
        startTimeCb.getItems().add(LocalTime.of(23, 30));
        endTimeCb.getItems().add(LocalTime.of(23, 30));
    }

}

