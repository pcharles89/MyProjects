package Controller;

import DAO.CountryDAO;
import DAO.CustomerDAO;
import DAO.FLDivisionDAO;
import Model.Country;
import Model.Customer;
import Model.FLDivision;
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
import java.util.Optional;
import java.util.ResourceBundle;

public class UpdateCustomerController implements Initializable {
    private Stage stage;
    private Parent scene;

    @FXML
    private Label custIdLbl;

    @FXML
    private TextField custNameTf;

    @FXML
    private TextField custPostalCodeTf;

    @FXML
    private TextField custPhoneTf;

    @FXML
    private TextField custAddressTf;

    @FXML
    private ComboBox<Country> custCountryCB;

    @FXML
    private ComboBox<FLDivision> custDivisionCB;

    @FXML
    private Button saveBtn;

    @FXML
    private Button cancelBtn;

    @FXML
    void backToMainMenu(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "This will clear any unsaved work, continue?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/View/MainForm.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        }
    }

    @FXML
    void saveCustomer(ActionEvent event) throws SQLException, IOException {
        if(((custNameTf.getText().equals("")) || (custAddressTf.getText().equals("")) || (custPostalCodeTf.getText().equals(""))
                || (custPhoneTf.getText().equals("")) || (custDivisionCB.getSelectionModel().getSelectedItem() == null))) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialogue");
            alert.setContentText("Please enter a valid value for each field.");
            alert.showAndWait();
        }
        else {
            Customer customer = new Customer(Integer.parseInt(custIdLbl.getText()), custNameTf.getText(), custAddressTf.getText(),
                    custPostalCodeTf.getText(), custPhoneTf.getText(), Integer.parseInt(String.valueOf(custDivisionCB.getSelectionModel()
                    .getSelectedItem().getId())));
            CustomerDAO.updateCustomer(Integer.parseInt(custIdLbl.getText()), customer);
            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/View/MainForm.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        }

    }

    @FXML
    void selectCountry(ActionEvent event) throws SQLException {
        String country = custCountryCB.getSelectionModel().getSelectedItem().toString();
        custDivisionCB.setItems(FLDivisionDAO.getFilteredDivisions(country));
    }

    public void receiveCustomer(Customer customer) throws SQLException {
        int divisionId = customer.getDivisionId();
        String country;

        custIdLbl.setText(String.valueOf(customer.getId()));
        custNameTf.setText(customer.getName());
        custAddressTf.setText(customer.getAddress());
        custPhoneTf.setText(customer.getPhone());
        custPostalCodeTf.setText(customer.getPostalCode());
        custCountryCB.setItems(CountryDAO.getAllCountries());
        if (customer.getDivisionId() <= 54) {
            country = "U.S";
            custCountryCB.getSelectionModel().selectFirst();
        }
        else if(customer.getDivisionId() <= 72) {
            country = "Canada";
            custCountryCB.getSelectionModel().selectLast();
        }
        else {
            country = "UK";
            custCountryCB.getSelectionModel().select(1);
        }
        custDivisionCB.setItems(FLDivisionDAO.getFilteredDivisions(country));
        for(FLDivision division: custDivisionCB.getItems()) {
            if(divisionId == division.getId()) {
                custDivisionCB.getSelectionModel().select(division);
                break;
            }
        }
    }

    @Override
    public void initialize (URL url, ResourceBundle resourceBundle) {
        custIdLbl.setDisable(true);
    }

}

