package Controller;

import DAO.AppointmentDAO;
import DAO.ContactDAO;
import DAO.UserDAO;
import Model.Appointment;
import Model.Contact;
import Model.User;
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
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class ReportsController implements Initializable {

    private Stage stage;
    private Parent scene;

    @FXML
    private ComboBox<?> monthCb;

    @FXML
    private ComboBox<String> typeCb;

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

    @FXML
    void backToAppts(ActionEvent event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/View/CustAppts.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

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

    @FXML
    void selectMonth(ActionEvent event) {

    }

    @FXML
    void selectType(ActionEvent event) {

    }

    @FXML
    void selectUser(ActionEvent event) throws SQLException {
        int count = 0;
        for(Appointment appointment : AppointmentDAO.getAllAppointments()) {
            if(appointment.getUserId() == userCb.getSelectionModel().getSelectedItem().getId()) {
                ++count;
            }
        }
        report2Lbl.setText(String.valueOf(count));
    }

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
            ObservableList<String> types = FXCollections.observableArrayList();
            contactCb.setItems(ContactDAO.getAllContacts());
            userCb.setItems(UserDAO.getAllUsers());
            for(Appointment appointment: AppointmentDAO.getAllAppointments()) {
                types.add(appointment.getType());
            }
            typeCb.setItems(types);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}

