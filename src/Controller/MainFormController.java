package Controller;

import DAO.AppointmentDAO;
import DAO.CustomerDAO;
import DAO.JDBC;
import DAO.Query;
import Model.Appointment;
import Model.Customer;
import Model.FLDivision;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;

/** This class deals with the main form screen. This is what the user sees when they first log in to the application.*/
public class MainFormController implements Initializable {
    private Stage stage;
    private Parent scene;
    int flag = 0;

    @FXML
    private TableView<Customer> custTableView;

    @FXML
    private TableColumn<Customer, Integer> custIdCol;

    @FXML
    private TableColumn<Customer, String> custNameCol;

    @FXML
    private TableColumn<Customer, String> custAddressCol;

    @FXML
    private TableColumn<Customer, String> custPostalCodeCol;

    @FXML
    private TableColumn<Customer, String> custPhoneCol;

    @FXML
    private TableColumn<Customer, Integer> custDivisionIdCol;

    @FXML
    private Button addBtn;

    @FXML
    private Button updateBtn;

    @FXML
    private Button deleteBtn;

    @FXML
    private Button viewApptBtn;

    @FXML
    private TextField searchBarTf;

    /** -LAMBDA-Used to exit the application. The lambda expression makes the code more concise as it avoids using a conditional
     * statement to exit the application
     * @param event occurs when a user clicks the exit button
     */
    @FXML
    void exitApplication(ActionEvent event) {
            /*Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "This will exit the application, are you sure?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                System.exit(0);
            }*/
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "This will exit the application, are you sure?");
        Optional<ButtonType> result = alert.showAndWait();
        result.ifPresent(x -> System.exit(0));
    }

    /** Used to add a new customer. Brings the user to the add customer form.
     * @param event occurs when a user clicks the add button
     */
    @FXML
    void addCustomer(ActionEvent event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/View/AddCustomer.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /** Used to delete a customer from the database. Removes the customer from the tableview.
     * @param event occurs when a user clicks the delete button
     */
    @FXML
    void deleteCustomer(ActionEvent event) throws SQLException, IOException {
        if(!(custTableView.getItems().isEmpty()) && (custTableView.getSelectionModel().getSelectedItem() != null)) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "This will delete the customer record, are you sure?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                String deleteCustomer = "DELETE FROM client_schedule.customers WHERE Customer_ID = ?";
                JDBC.openConnection();
                Query.setPreparedStatement(JDBC.getConnection(), deleteCustomer);
                PreparedStatement ps = Query.getPreparedStatement();
                ps.setInt(1, custTableView.getSelectionModel().getSelectedItem().getId());
                ps.executeUpdate();

                Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
                alert2.setTitle("Warning Dialogue");
                String customerId = String.valueOf(custTableView.getSelectionModel().getSelectedItem().getId());
                String customerName = custTableView.getSelectionModel().getSelectedItem().getName();
                alert2.setContentText("Customer[" + customerId + "] " + customerName + " was deleted.");
                alert2.showAndWait();

                stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                scene = FXMLLoader.load(getClass().getResource("/View/MainForm.fxml"));
                stage.setScene(new Scene(scene));
                stage.show();
                custTableView.getItems().remove(custTableView.getSelectionModel().getSelectedItem());

            }
        }
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialogue");
            alert.setContentText("There are no items selected to delete");
            alert.showAndWait();
        }

    }

    /** Used to search for a customer. The customer tableview is searched to see if a customer exists
     * @param event occurs when a user hits the enter button on their keyboard
     */
    @FXML
    void onEnterCustomer(KeyEvent event) {
        if (event.getCode().equals(KeyCode.ENTER)) {
            custTableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
            custTableView.getSelectionModel().clearSelection();
            String search = searchBarTf.getText();
            for(Customer customer: custTableView.getItems()) {
                if(searchBarTf.getText().equals("")) {
                    custTableView.getSelectionModel().select(null);
                    break;
                }
                else if(customer.getName().contains(search) || Integer.toString(customer.getId()).contains(search)) {
                    custTableView.getSelectionModel().select(customer);
                }
            }
            if (custTableView.getSelectionModel().isEmpty() && (!(searchBarTf.getText().equals("")))) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Dialogue");
                alert.setContentText("Search result not found.");
                alert.showAndWait();
            }

        }
    }

    /** Used to search for a customer. The customer tableview is searched to see if a customer exists
     * @param event occurs when a user clicks the search button
     */
    @FXML
    void searchForCustomer(ActionEvent event) {
        custTableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        custTableView.getSelectionModel().clearSelection();
        String search = searchBarTf.getText();
        for(Customer customer: custTableView.getItems()) {
            if(searchBarTf.getText().equals("")) {
                custTableView.getSelectionModel().select(null);
                break;
            }
            else if(customer.getName().contains(search) || Integer.toString(customer.getId()).contains(search)) {
                custTableView.getSelectionModel().select(customer);
            }
        }
        if (custTableView.getSelectionModel().isEmpty() && (!(searchBarTf.getText().equals("")))) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialogue");
            alert.setContentText("Search result not found.");
            alert.showAndWait();
        }

    }

    /** Allows the user to update a customer record. Brings the user to the update customer screen
     * @param event occurs when a user clicks the update button
     */
    @FXML
    void updateCustomer(ActionEvent event) throws IOException, SQLException {
        if(!(custTableView.getSelectionModel().getSelectedItems().isEmpty())) {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/View/UpdateCustomer.fxml"));
            loader.load();
            UpdateCustomerController updateCustomer = loader.getController();
            updateCustomer.receiveCustomer(custTableView.getSelectionModel().getSelectedItem());
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

    /** Allows a user to view appointments for a particular customer. Brings the user to the customer appointments screen
     * @param event occurs when a user has a customer selected in the tableview and then clicks the view all appointments button
     */
    @FXML
    void viewAppts(ActionEvent event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/View/CustAppts.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /** The first method called in the class. Sets the customer tableview with customer objects.*/
    @Override
    public void initialize (URL url, ResourceBundle resourceBundle) {
        try {
            custTableView.setItems(CustomerDAO.getAllCustomers());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        custIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        custNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        custAddressCol.setCellValueFactory(new PropertyValueFactory<>("address"));
        custPostalCodeCol.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
        custPhoneCol.setCellValueFactory(new PropertyValueFactory<>("phone"));
        custDivisionIdCol.setCellValueFactory(new PropertyValueFactory<>("divisionId"));

    }

}
