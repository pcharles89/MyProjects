package Controller;

import DAO.*;
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

/** This class handles adding customer appointments for a specific customer Id. Brings a user to the add customer appointment screen. */
public class AddCustApptController implements Initializable {
    private Stage stage;
    private Parent scene;

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

    private static final LocalTime firstAppt = LocalTime.of(8,0,0);
    private static final LocalTime lastAppt = LocalTime.of(21,45, 0);
    private static final LocalTime businessClose = LocalTime.of(22, 0, 0);
    private static final LocalTime earliestFirstApptEnd = LocalTime.of(8, 15, 0);
    private int flag = 0;


    /** Displays all customer appointments. Brings a user back to the Customer Appointments screen.
     * @param event occurs when a user clicks the back button
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

    /** Saves an appointment. Brings a user back to the Customer Appointments screen after saving it
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
            LocalDateTime apptStartIf = apptStart.toLocalDateTime();
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
            LocalDateTime apptEndIf = apptEndZone.toLocalDateTime();
            ZonedDateTime apptEndCheck = apptEndZone.withZoneSameInstant(ZoneId.of("America/New_York"));
            LocalDateTime easternDateTimeEnd = apptEndCheck.toLocalDateTime();
            ZonedDateTime utcDateTimeEnd = apptEndCheck.withZoneSameInstant(ZoneId.of("UTC"));
            LocalDateTime utcDateTimeEndInsert = utcDateTimeEnd.toLocalDateTime();

            LocalTime easternTimeStart = easternDateTimeStart.toLocalTime();
            LocalTime easternTimeEnd = easternDateTimeEnd.toLocalTime();

            LocalDate date = startDp.getValue();
            ZoneId localZoneEastern = ZoneId.of("America/New_York");
            ZonedDateTime firstApptZdt = ZonedDateTime.of(date, firstAppt, localZoneEastern);
            ZonedDateTime lastApptZdt = ZonedDateTime.of(date, lastAppt, localZoneEastern);
            ZonedDateTime businessCloseZdt = ZonedDateTime.of(date, businessClose, localZoneEastern);
            ZonedDateTime earliestFirstApptEndZdt = ZonedDateTime.of(date, earliestFirstApptEnd, localZoneEastern);
            LocalDateTime firstApptLdt = firstApptZdt.toLocalDateTime();
            LocalDateTime lastApptLdt = lastApptZdt.toLocalDateTime();
            LocalDateTime businessCloseLdt = businessCloseZdt.toLocalDateTime();
            LocalDateTime earliestFirstApptLdt = earliestFirstApptEndZdt.toLocalDateTime();
            LocalTime firstApptTime = firstApptLdt.toLocalTime();
            LocalTime lastApptTime = lastApptLdt.toLocalTime();
            LocalTime businessCloseTime = businessCloseLdt.toLocalTime();
            LocalTime earliestFirstApptEndTime = earliestFirstApptLdt.toLocalTime();

            if ((apptStartTime.isAfter(apptEndTime)) || (apptStartTime.equals(apptEndTime)))
            {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Dialogue");
                alert.setContentText("Invalid appointment");
                alert.showAndWait();
            }

            else if ((easternDateTimeStart.isBefore(firstApptLdt) || easternDateTimeStart.isAfter(lastApptLdt)) || (easternDateTimeEnd.isBefore(earliestFirstApptLdt)
                    || easternDateTimeEnd.isAfter(businessCloseLdt))) {
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
                        System.out.println("test");
                        Timestamp dbTimeStart = Timestamp.valueOf(appointment.getStart());
                        Timestamp dbTimeEnd = Timestamp.valueOf(appointment.getEnd());
                        System.out.println(dbTimeEnd);
                        LocalDateTime apptStartCompare = dbTimeStart.toLocalDateTime();
                        LocalDateTime apptEndCompare = dbTimeEnd.toLocalDateTime();
                        /*ZonedDateTime apptStart2 = ZonedDateTime.of(dbDateTimeStart, ZoneId.of("UTC"));
                        ZonedDateTime apptEnd2 = ZonedDateTime.of(dbDateTimeEnd, ZoneId.of("UTC"));
                        ZonedDateTime apptStartLocal = apptStart2.withZoneSameInstant(ZoneId.systemDefault());
                        ZonedDateTime apptEndLocal = apptEnd2.withZoneSameInstant(ZoneId.systemDefault());
                        LocalDateTime apptStartCompare = apptStartLocal.toLocalDateTime();
                        LocalDateTime apptEndCompare = apptEndLocal.toLocalDateTime(); */
                        System.out.println(easternDateTimeEnd);
                        if (((apptStartIf.isAfter(apptStartCompare)) || (apptStartIf.isEqual(apptStartCompare))) &&
                                (apptStartIf.isBefore(apptEndCompare))) {
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setTitle("Error Dialogue");
                            alert.setContentText("Appointment overlap error");
                            alert.showAndWait();
                            flag++;
                            break mainLoop;
                        }
                        if (((apptEndIf.isAfter(apptStartCompare)) && (apptEndIf.isBefore(apptEndCompare))) ||
                                (apptEndIf.isEqual(apptEndCompare))) {
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setTitle("Error Dialogue");
                            alert.setContentText("Appointment overlap error");
                            alert.showAndWait();
                            flag++;
                            break mainLoop;
                        }
                        if(((apptStartIf.isBefore(apptStartCompare)) || (apptStartIf.equals(apptStartCompare))) &&
                                ((apptEndIf.isAfter(apptEndCompare)) || (apptEndIf.isEqual(apptEndCompare)))) {
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
                    AppointmentDAO.createAppointment(Integer.parseInt(apptIdLbl.getText()), titleTf.getText(), descriptionTf.getText(),
                            locationTf.getText(), typeTf.getText(), timestampStart, timestampEnd, Integer.parseInt(String.valueOf(custIdCb.getSelectionModel().getSelectedItem())),
                            Integer.parseInt(String.valueOf(userIdCb.getSelectionModel().getSelectedItem())),
                            Integer.parseInt(String.valueOf(contactCb.getSelectionModel().getSelectedItem().getId())));
                    System.out.println("inner loop");
                    stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                    scene = FXMLLoader.load(getClass().getResource("/View/CustAppts.fxml"));
                    stage.setScene(new Scene(scene));
                    stage.show();
                }
            }
        }
    }

    /** First method called in class. Disables id text field and populates combo boxes.*/
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            apptIdLbl.setText(String.valueOf(AppointmentDAO.getAllAppointments().size() + 1));
            apptIdLbl.setDisable(true);
            contactCb.setItems(ContactDAO.getAllContacts());
            custIdCb.setItems(CustomerDAO.getAllCustomers());
            userIdCb.setItems(UserDAO.getAllUsers());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        LocalTime businessTimeStart = LocalTime.of(0, 0);
        LocalDate localDate = LocalDate.of(2021, 12, 27);
        LocalTime businessTimeEnd = LocalTime.of(23, 30);
        ZoneId businessLocale = ZoneId.of(String.valueOf(ZoneId.systemDefault()));
        ZonedDateTime localZdt = ZonedDateTime.of(localDate, businessTimeStart, businessLocale);
        DateTimeFormatter adjustTimes = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        System.out.println(adjustTimes.format(localZdt));


        while(businessTimeStart.isBefore(businessTimeEnd)) {
            startTimeCb.getItems().add(businessTimeStart);
            endTimeCb.getItems().add(businessTimeStart);
            businessTimeStart = businessTimeStart.plusMinutes(15);
            businessTimeEnd = businessTimeStart.plusMinutes(15);
        }
        startTimeCb.getItems().add(LocalTime.of(23, 45));
        endTimeCb.getItems().add(LocalTime.of(23, 45));
    }

}

