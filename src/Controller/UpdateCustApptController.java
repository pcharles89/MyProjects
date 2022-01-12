package Controller;

import DAO.AppointmentDAO;
import DAO.ContactDAO;
import DAO.CustomerDAO;
import DAO.UserDAO;
import Model.Appointment;
import Model.Contact;
import Model.Customer;
import Model.User;
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
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.ResourceBundle;

/** This class deals with the update customer appointment screen. It allows a user to update an existing appointment for a customer.*/
public class UpdateCustApptController implements Initializable {
    private Stage stage;
    private Parent scene;
    private static final LocalTime firstAppt = LocalTime.of(8,0,0);
    private static final LocalTime lastAppt = LocalTime.of(21,45, 0);
    private static final LocalTime businessClose = LocalTime.of(22, 0, 0);
    private static final LocalTime earliestFirstApptEnd = LocalTime.of(8, 15, 0);
    private int flag = 0;

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

    /** Displays customer appointments in a tableview. Brings the user back to the customer appointments screen.
     * @param event occurs when a user clicks the cancel button
     */
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

    /** Saves a customer appointment. Brings a user back to the customer appointments screen after saving.
     * @param event occurs when a user clicks the save button
     */
    @FXML
    void saveApptAction(ActionEvent event) throws IOException, SQLException {
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
        else {
            LocalDate apptStartDate = startDp.getValue();
            LocalTime apptStartTime = startTimeCb.getSelectionModel().getSelectedItem();
            LocalDateTime appt = LocalDateTime.of(apptStartDate.getYear(), apptStartDate.getMonth(), apptStartDate.getDayOfMonth(),
                    apptStartTime.getHour(), apptStartTime.getMinute());
            ZoneId localZone = ZoneId.systemDefault();
            ZonedDateTime apptStart = ZonedDateTime.of(appt, localZone);
            ZonedDateTime apptStartCheck = apptStart.withZoneSameInstant(ZoneId.of("America/New_York"));
            LocalDateTime easternDateTimeStart = apptStartCheck.toLocalDateTime();
            ZonedDateTime utcDateTimeStart = apptStartCheck.withZoneSameInstant(ZoneId.of("UTC"));
            LocalDateTime utcDateTimeStartInsert = utcDateTimeStart.toLocalDateTime();

            LocalDate apptEndDate = startDp.getValue();
            LocalTime apptEndTime = endTimeCb.getSelectionModel().getSelectedItem();
            LocalDateTime apptEnd = LocalDateTime.of(apptEndDate.getYear(), apptEndDate.getMonth(), apptEndDate.getDayOfMonth(),
                    apptEndTime.getHour(), apptEndTime.getMinute());
            ZoneId localZoneEnd = ZoneId.systemDefault();
            ZonedDateTime apptEndZone = ZonedDateTime.of(apptEnd, localZoneEnd);
            ZonedDateTime apptEndCheck = apptEndZone.withZoneSameInstant(ZoneId.of("America/New_York"));
            LocalDateTime easternDateTimeEnd = apptEndCheck.toLocalDateTime();
            ZonedDateTime utcDateTimeEnd = apptEndCheck.withZoneSameInstant(ZoneId.of("UTC"));
            LocalDateTime utcDateTimeEndInsert = utcDateTimeEnd.toLocalDateTime();

            LocalTime easternTimeStart = easternDateTimeStart.toLocalTime();
            LocalTime easternTimeEnd = easternDateTimeEnd.toLocalTime();

            if (easternTimeStart.isAfter(easternTimeEnd)) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Dialogue");
                alert.setContentText("Please ensure start time is before end time");
                alert.showAndWait();
            }
            else if ((easternTimeStart.isBefore(firstAppt) || easternTimeStart.isAfter(lastAppt)) || (easternTimeEnd.isBefore(earliestFirstApptEnd)
                    || easternTimeEnd.isAfter(businessClose))) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Dialogue");
                alert.setContentText("Business hours are 8 a.m. - 10 p.m. EST");
                alert.showAndWait();
            }
            else {
                Timestamp timestampStart = Timestamp.valueOf(utcDateTimeStartInsert);
                Timestamp timestampEnd = Timestamp.valueOf(utcDateTimeEndInsert);

                mainLoop: {
                    for (Appointment appointment : AppointmentDAO.getAllAppointments()) {
                        if (Integer.parseInt(String.valueOf(custIdCb.getSelectionModel().getSelectedItem())) == appointment.getCustomerId()) {
                            //System.out.println("test");
                            Timestamp dbTimeStart = Timestamp.valueOf(appointment.getStart());
                            Timestamp dbTimeEnd = Timestamp.valueOf(appointment.getEnd());
                            //System.out.println(dbTimeEnd);
                            LocalDateTime dbDateTimeStart = dbTimeStart.toLocalDateTime();
                            LocalDateTime dbDateTimeEnd = dbTimeEnd.toLocalDateTime();
                            System.out.println(easternDateTimeEnd);
                            if (((easternDateTimeStart.isAfter(dbDateTimeStart)) || (easternDateTimeStart.isEqual(dbDateTimeStart))) &&
                                    (easternDateTimeStart.isBefore(dbDateTimeEnd)) && (!(apptIdLbl.getText().equalsIgnoreCase(String.valueOf(appointment.getId()))))) {
                                Alert alert = new Alert(Alert.AlertType.ERROR);
                                alert.setTitle("Error Dialogue");
                                alert.setContentText("Appointment overlap error");
                                alert.showAndWait();
                                flag++;
                                break mainLoop;
                            }
                            if (((easternDateTimeEnd.isAfter(dbDateTimeStart)) && (easternDateTimeEnd.isBefore(dbDateTimeEnd))) ||
                                    (easternDateTimeEnd.isEqual(dbDateTimeEnd)) && (!(apptIdLbl.getText().equalsIgnoreCase(String.valueOf(appointment.getId()))))) {
                                Alert alert = new Alert(Alert.AlertType.ERROR);
                                alert.setTitle("Error Dialogue");
                                alert.setContentText("Appointment overlap error");
                                alert.showAndWait();
                                flag++;
                                break mainLoop;
                            }
                            if(((easternDateTimeStart.isBefore(dbDateTimeStart)) || (easternDateTimeStart.equals(dbDateTimeStart))) &&
                                    ((easternDateTimeEnd.isAfter(dbDateTimeEnd)) || (easternDateTimeEnd.isEqual(dbDateTimeEnd))) &&
                                    (!(apptIdLbl.getText().equalsIgnoreCase(String.valueOf(appointment.getId()))))) {
                                Alert alert = new Alert(Alert.AlertType.ERROR);
                                alert.setTitle("Error Dialogue");
                                alert.setContentText("Appointment overlap error");
                                alert.showAndWait();
                                flag++;
                                break mainLoop;
                            }
                        }
                    }
                }
                if(flag == 0) {
                    String stringTimestampStart = timestampStart.toString();
                    String stringTimestampEnd = timestampEnd.toString();
                    Appointment appointment = new Appointment(Integer.parseInt(apptIdLbl.getText()), titleTf.getText(), descriptionTf.getText(),
                            locationTf.getText(), typeTf.getText(), stringTimestampStart, stringTimestampEnd, Integer.parseInt(String.valueOf(custIdCb.getSelectionModel().getSelectedItem())),
                            Integer.parseInt(String.valueOf(userIdCb.getSelectionModel().getSelectedItem())),
                            Integer.parseInt(String.valueOf(contactCb.getSelectionModel().getSelectedItem().getId())));
                    AppointmentDAO.updateAppointment(Integer.parseInt(apptIdLbl.getText()), appointment);
                    stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                    scene = FXMLLoader.load(getClass().getResource("/View/CustAppts.fxml"));
                    stage.setScene(new Scene(scene));
                    stage.show();
                }
            }
        }
    }

    /** Receives appointment from the customer appointments screen. Populates all text fields and combo boxes with relevant
     * information.
     * @param appointment the specific appointment being updated is the one that is passed as an argument
     */
    public void receiveAppointment(Appointment appointment) throws SQLException {
        apptIdLbl.setText(String.valueOf(appointment.getId()));
        titleTf.setText(appointment.getTitle());
        descriptionTf.setText(appointment.getDescription());
        locationTf.setText(appointment.getLocation());
        typeTf.setText(appointment.getType());
        contactCb.setItems(ContactDAO.getAllContacts());
        appointment.setContactName(appointment.getContactId());
        contactCb.getSelectionModel().select(appointment.getContactIndex());
        custIdCb.setItems(CustomerDAO.getAllCustomers());
        custIdCb.getSelectionModel().select(appointment.getCustomerId()-1);
        userIdCb.setItems(UserDAO.getAllUsers());
        userIdCb.getSelectionModel().select(appointment.getUserId()-1);

        String apptStart = appointment.getStart();
        String[] splitStart = apptStart.split(" ", 2);
        String apptEnd = appointment.getEnd();
        String[] splitEnd = apptEnd.split(" ", 2);
        String splitStartDate = splitStart[0];
        String splitStartTime = splitStart[1];
        String splitEndDate = splitEnd[0];
        String splitEndTime = splitEnd[1];
        LocalDate startDate = LocalDate.parse(splitStartDate);
        startDp.setValue(startDate);

        LocalTime businessTimeStart = LocalTime.of(0, 0);
        LocalDate localDate = LocalDate.of(2021, 12, 27);
        LocalTime businessTimeEnd = LocalTime.of(23, 30);
        ZoneId businessLocale = ZoneId.of(String.valueOf(ZoneId.systemDefault()));
        ZonedDateTime localZdt = ZonedDateTime.of(localDate, businessTimeStart, businessLocale);

        while(businessTimeStart.isBefore(businessTimeEnd)) {
            startTimeCb.getItems().add(businessTimeStart);
            endTimeCb.getItems().add(businessTimeStart);
            businessTimeStart = businessTimeStart.plusMinutes(15);
            businessTimeEnd = businessTimeStart.plusMinutes(15);
        }
        startTimeCb.getItems().add(LocalTime.of(23, 30));
        endTimeCb.getItems().add(LocalTime.of(23, 30));

        LocalTime startTime = LocalTime.parse(splitStartTime);
        LocalTime endTime = LocalTime.parse(splitEndTime);
        startTimeCb.setValue(startTime);
        endTimeCb.setValue(endTime);
    }

    /** The first method called in the class. Sets the appointment Id to disabled.*/
    @Override
    public void initialize (URL url, ResourceBundle resourceBundle) {
        apptIdLbl.setDisable(true);
    }

}

