package Controller;

import DAO.*;
import Model.Country;
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

/** This class handles adding customers. Brings a user to the add customer screen to enter new customer info.*/
public class AddCustomerController implements Initializable {
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

    /** Displays all customers. Brings a user back to the main form screen.
     * @param event occurs when a user clicks the back button
     */
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

    /** Selects a country. Populates division combo box with the appropriate selection based on the country chosen.
     * @param event occurs when a user makes a selection from the country combo box
     */
    @FXML
    void selectCountry(ActionEvent event) throws SQLException {
        String country = custCountryCB.getSelectionModel().getSelectedItem().toString();
        custDivisionCB.setItems(FLDivisionDAO.getFilteredDivisions(country));

    }

    /** Saves a customer. Brings a user back to the main screen after saving it
     * @param event occurs when a user clicks the save button
     */
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

    /** First method called in class. Disables id text field and populates combo boxes.*/
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            custIdLbl.setText(String.valueOf(CustomerDAO.getAllCustomers().size()+1));
            custIdLbl.setDisable(true);
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
