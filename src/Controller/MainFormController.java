package Controller;

import DAO.CustomerDAO;
import DAO.JDBC;
import DAO.Query;
import Model.Customer;
import Model.FLDivision;
import javafx.collections.FXCollections;
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
import java.util.Optional;
import java.util.ResourceBundle;

public class MainFormController implements Initializable {
    private Stage stage;
    private Parent scene;

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

    @FXML
    void addCustomer(ActionEvent event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/View/AddCustomer.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();

    }

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

    @FXML
    void updateCustomer(ActionEvent event) throws IOException {
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

    @FXML
    void viewAppts(ActionEvent event) {

    }

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
