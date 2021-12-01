package Controller;

import DAO.CustomerDAO;
import Model.Customer;
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
import java.sql.SQLException;
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
    void deleteCustomer(ActionEvent event) {

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
    void updateCustomer(ActionEvent event) {

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
