package Controller;

import DAO.CountryDAO;
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
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

public class AddCustomerController implements Initializable {
    private Stage stage;
    private Parent scene;
    private static ObservableList<Country> countries = FXCollections.observableArrayList();
    private static ObservableList<FLDivision> divisions = FXCollections.observableArrayList();

    @FXML
    private Label custIdLbl;

    @FXML
    private TextField custNameTf;

    @FXML
    private TextField custPostalCodeCB;

    @FXML
    private TextField custPhoneCB;

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
    void saveCustomer(ActionEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            custCountryCB.setItems(CountryDAO.getAllCountries());
        } catch (SQLException e) {
            e.printStackTrace();
        }
            custCountryCB.setPromptText("Please select a country");
            custDivisionCB.setPromptText("Please select a state/province");
            custDivisionCB.setItems(divisions);
    }
}
