package Controller;

import DAO.*;
import Model.Country;
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
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;

public class AddCustomerController implements Initializable {
    private Stage stage;
    private Parent scene;
    private static String country;
    private static ObservableList<Country> countries = FXCollections.observableArrayList();
    private static ObservableList<FLDivision> divisions = FXCollections.observableArrayList();

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
    void selectCountry(ActionEvent event) throws SQLException {
        String country = custCountryCB.getSelectionModel().getSelectedItem().toString();
        custDivisionCB.setItems(FLDivisionDAO.getFilteredDivisions(country));

    }

    @FXML
    void selectDivision(ActionEvent event) throws SQLException {

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
                CustomerDAO.createCustomer(Integer.parseInt(custIdLbl.getText()), custNameTf.getText(), custAddressTf.getText(),
                        custPostalCodeTf.getText(), custPhoneTf.getText(),
                        Integer.parseInt(String.valueOf(custDivisionCB.getSelectionModel().getSelectedItem().getId())));

                stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                scene = FXMLLoader.load(getClass().getResource("/View/MainForm.fxml"));
                stage.setScene(new Scene(scene));
                stage.show();
            }

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            custIdLbl.setText(String.valueOf(CustomerDAO.getAllCustomers().size()+1));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            custCountryCB.setItems(CountryDAO.getAllCountries());
        } catch (SQLException e) {
            e.printStackTrace();
        }
            custCountryCB.setPromptText("Please select a country");
            custDivisionCB.setPromptText("Please select a state/province");

    }
}
