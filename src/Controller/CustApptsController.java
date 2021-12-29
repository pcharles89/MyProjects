package Controller;

import DAO.AppointmentDAO;
import DAO.CustomerDAO;
import DAO.JDBC;
import DAO.Query;
import Model.Appointment;
import Model.Customer;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.ResourceBundle;

public class CustApptsController implements Initializable {

    private Stage stage;
    private Parent scene;

    @FXML
    private RadioButton allRbtn;

    @FXML
    private ToggleGroup FilterTGL;

    @FXML
    private RadioButton monthRbtn;

    @FXML
    private RadioButton weekRbtn;

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
    private TextField searchBarTf;

    @FXML
    private Button searchBtn;

    @FXML
    private Button addRbtn;

    @FXML
    private Button updateRbtn;

    @FXML
    private Button deleteRbtn;

    @FXML
    private Button backRbtn;

    @FXML
    private Button reportsBtn;

    @FXML
    void deleteAppt(ActionEvent event) throws SQLException, IOException {
        if(!(apptTableview.getItems().isEmpty()) && (apptTableview.getSelectionModel().getSelectedItem() != null)) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "This will delete the appointment, are you sure?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                String deleteAppointment = "DELETE FROM client_schedule.appointments WHERE Appointment_ID = ?";
                JDBC.openConnection();
                Query.setPreparedStatement(JDBC.getConnection(), deleteAppointment);
                PreparedStatement ps = Query.getPreparedStatement();
                ps.setInt(1, apptTableview.getSelectionModel().getSelectedItem().getId());
                ps.executeUpdate();

                Alert alert2 = new Alert(Alert.AlertType.WARNING);
                alert2.setTitle("Warning Dialogue");
                String appointmentId = String.valueOf(apptTableview.getSelectionModel().getSelectedItem().getId());
                String appointmentType = apptTableview.getSelectionModel().getSelectedItem().getType();
                alert2.setContentText("Appointment[" + appointmentId  + "] " + appointmentType + " was deleted.");
                alert2.showAndWait();

                stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                scene = FXMLLoader.load(getClass().getResource("/View/CustAppts.fxml"));
                stage.setScene(new Scene(scene));
                stage.show();
                apptTableview.getItems().remove(apptTableview.getSelectionModel().getSelectedItem());

            }
        }
    }

    @FXML
    void displayAddForm(ActionEvent event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/View/AddCustAppt.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    @FXML
    void displayAll(ActionEvent event) {
        try {
            apptTableview.setItems(AppointmentDAO.getAllAppointments());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void displayByMonth(ActionEvent event) {
        try {
            apptTableview.setItems(AppointmentDAO.getFilteredAppointmentsMonth());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void displayByWeek(ActionEvent event) {

    }

    @FXML
    void displayMainMenu(ActionEvent event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/View/MainForm.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    @FXML
    void displayUpdateForm(ActionEvent event) throws IOException, SQLException {
        if(!(apptTableview.getSelectionModel().getSelectedItems().isEmpty())) {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/View/UpdateCustAppt.fxml"));
            loader.load();
            UpdateCustApptController updateAppointment = loader.getController();
            updateAppointment.receiveAppointment(apptTableview.getSelectionModel().getSelectedItem());
            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            Parent scene = loader.getRoot();
            stage.setScene(new Scene(scene));
            stage.show();
        }
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialogue");
            alert.setContentText("No items are selected.");
            alert.showAndWait();
        }
    }

    @FXML
    void onEnterAppt(KeyEvent event) {
        if (event.getCode().equals(KeyCode.ENTER)) {
            apptTableview.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
            apptTableview.getSelectionModel().clearSelection();
            String search = searchBarTf.getText();
            for(Appointment appointment: apptTableview.getItems()) {
                if(searchBarTf.getText().equals("")) {
                    apptTableview.getSelectionModel().select(null);
                    break;
                }
                else if(Integer.toString(appointment.getCustomerId()).contains(search)) {
                    apptTableview.getSelectionModel().select(appointment);
                }
            }
            if (apptTableview.getSelectionModel().isEmpty() && (!(searchBarTf.getText().equals("")))) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Dialogue");
                alert.setContentText("Search result not found.");
                alert.showAndWait();
            }

        }
    }

    @FXML
    void searchForAppt(ActionEvent event) {
        apptTableview.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        apptTableview.getSelectionModel().clearSelection();
        String search = searchBarTf.getText();
        for(Appointment appointment: apptTableview.getItems()) {
            if(searchBarTf.getText().equals("")) {
                apptTableview.getSelectionModel().select(null);
                break;
            }
            else if(Integer.toString(appointment.getCustomerId()).contains(search)) {
                apptTableview.getSelectionModel().select(appointment);
            }
        }
        if (apptTableview.getSelectionModel().isEmpty() && (!(searchBarTf.getText().equals("")))) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialogue");
            alert.setContentText("Search result not found.");
            alert.showAndWait();
        }
    }

    @FXML
    void viewReports(ActionEvent event) {

    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            apptTableview.setItems(AppointmentDAO.getAllAppointments());
        } catch (SQLException e) {
            e.printStackTrace();
        }
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
    }
}
