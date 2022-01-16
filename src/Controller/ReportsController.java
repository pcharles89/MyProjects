package Controller;

import DAO.AppointmentDAO;
import DAO.ContactDAO;
import DAO.UserDAO;
import Model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.stream.Stream;

/** This class deals with the reports screen. It allows a user to run reports based on various metrics and view the results.*/
public class ReportsController implements Initializable {

    private Stage stage;
    private Parent scene;

    @FXML
    private ComboBox<MonthCb> monthCb;

    @FXML
    private ComboBox<TypeCb> typeCb;

    @FXML
    private Label report1Lbl;

    @FXML
    private ComboBox<User> userCb;

    @FXML
    private Label report2Lbl;

    @FXML
    private ComboBox<Contact> contactCb;

    @FXML
    private TableView<Appointment> apptTableview;

    @FXML
    private TableColumn<Appointment, Integer> apptIdCol;

    @FXML
    private TableColumn<Appointment, String> titleCol;

    @FXML
    private TableColumn<Appointment, String> descriptionCol;

    @FXML
    private TableColumn<Appointment, String> locationCol;

    @FXML
    private TableColumn<Appointment, Integer> contactCol;

    @FXML
    private TableColumn<Appointment, String> typeCol;

    @FXML
    private TableColumn<Appointment, LocalDateTime> startCol;

    @FXML
    private TableColumn<Appointment, LocalDateTime> endCol;

    @FXML
    private TableColumn<Appointment, Integer> custIdCol;

    @FXML
    private TableColumn<Appointment, Integer> userCol;

    /** Used to view customer appointments. Brings a user back to the customer appointments screen
     * @param event occurs when a user hits the back button
     */
    @FXML
    void backToAppts(ActionEvent event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/View/CustAppts.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /** Displays appointments of contact selected. Populates tableview with appointment objects relating to the contact
     * @param event occurs when the user selects a contact from the combo box
     */
    @FXML
    void selectContact(ActionEvent event) throws SQLException {
        ObservableList<Appointment> filteredAppointments = FXCollections.observableArrayList();
        for(Appointment appointment : AppointmentDAO.getAllAppointments()) {
            if(Objects.equals(appointment.getContactName(), contactCb.getSelectionModel().getSelectedItem().toString())) {
                filteredAppointments.add(appointment);
            }
        }
        apptTableview.setItems(filteredAppointments);
    }

    /** Counts number of appointments by month and type. Will only generate the count if both combo boxes are selected.
     * @param event occurs when the user selects a month from the month combo box.
     */
    @FXML
    void selectMonth(ActionEvent event) throws SQLException {
        int counter = 0;
        if(typeCb.getSelectionModel().getSelectedItem() != null) {
            for(Appointment appointment : AppointmentDAO.getAllAppointments()) {
                Timestamp startTs = Timestamp.valueOf(appointment.getStart());
                LocalDateTime startLdt = startTs.toLocalDateTime();
                String startMonth = startLdt.getMonth().toString();
                if((appointment.getType().equalsIgnoreCase(typeCb.getSelectionModel().getSelectedItem().toString()) &&
                        (startMonth.equalsIgnoreCase(monthCb.getSelectionModel().getSelectedItem().toString())))) {
                    ++counter;
                }
            }
            report1Lbl.setText(String.valueOf(counter));
        }
    }

    /** Counts number of appointments by month and type. Will only generate the count if both combo boxes are selected.
     * @param event occurs when the user selects a type from the type combo box.
     */
    @FXML
    void selectType(ActionEvent event) throws SQLException {
        int counter = 0;
        if(monthCb.getSelectionModel().getSelectedItem() != null) {
            for(Appointment appointment : AppointmentDAO.getAllAppointments()) {
                Timestamp startTs = Timestamp.valueOf(appointment.getStart());
                LocalDateTime startLdt = startTs.toLocalDateTime();
                String startMonth = startLdt.getMonth().toString();
                if((appointment.getType().equalsIgnoreCase(typeCb.getSelectionModel().getSelectedItem().toString()) &&
                        (startMonth.equalsIgnoreCase(monthCb.getSelectionModel().getSelectedItem().toString())))) {
                    ++counter;
                }
            }
            report1Lbl.setText(String.valueOf(counter));
        }
    }

    /** -LAMBDA-Generates a count of appointments based on user id. A lambda expression makes this code more concise as it avoids
     * using a loop and a conditional statement
     * @param event occurs when a user selects a user id from the combo box
     */
    @FXML
    void selectUser(ActionEvent event) throws SQLException {
        /* int count = 0;
        for(Appointment appointment : AppointmentDAO.getAllAppointments()) {
            if(appointment.getUserId() == userCb.getSelectionModel().getSelectedItem().getId()) {
                ++count;
            }
        }
        report2Lbl.setText(String.valueOf(count)); */
        /*int counter = AppointmentDAO.getAllAppointments().stream().filter(w -> w.getUserId() ==
                userCb.getSelectionModel().getSelectedItem().getId())
                .mapToInt(Appointment::getUserId).sum();
        report2Lbl.setText(String.valueOf(counter)); */

        List<Appointment> filtered = new ArrayList<>(AppointmentDAO.getAllAppointments());
            Integer count = Math.toIntExact(filtered.stream()
                    .filter((appointment) -> appointment.getUserId() == userCb.getSelectionModel().getSelectedItem().getId())
                    .count());
            report2Lbl.setText(String.valueOf(count));
    }


    /** The first method called in the class. Populates the combo boxes and prepares the tableview.*/
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        apptIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        descriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        locationCol.setCellValueFactory(new PropertyValueFactory<>("location"));
        typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        startCol.setCellValueFactory(new PropertyValueFactory<>("start"));
        endCol.setCellValueFactory(new PropertyValueFactory<>("end"));
        custIdCol.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        userCol.setCellValueFactory(new PropertyValueFactory<>("userId"));
        contactCol.setCellValueFactory(new PropertyValueFactory<>("contactName"));
        try {
            ObservableList<TypeCb> types = FXCollections.observableArrayList();
            contactCb.setItems(ContactDAO.getAllContacts());
            userCb.setItems(UserDAO.getAllUsers());
            for(Appointment appointment: AppointmentDAO.getAllAppointments()) {
                types.add(new TypeCb(appointment.getType()));
            }
            typeCb.setItems(types);
            ObservableList<MonthCb> months = FXCollections.observableArrayList();
            months.addAll(new MonthCb("January"), new MonthCb("February"), new MonthCb("March"),
                    new MonthCb("April"), new MonthCb("May"), new MonthCb("June"), new MonthCb("July"),
                    new MonthCb("August"), new MonthCb("September"), new MonthCb("October"),
                    new MonthCb("November"), new MonthCb("December"));
            monthCb.setItems(months);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}

